from twisted.internet import reactor

SECS_IN_30DAYS=60*60*24*30

class PriceState(object):
	def __init__(self, reactor):
		self._stable = False
		self._inPromotion = False
		self._reactor = reactor
		self._stabilityTimer = None
		self._promotionTimer = None
		self._promotionValue = 0

		self._startStabilityTimer()

	def makeUnstable(self):
		self._stable = False
		if not self._inPromotion:
			self._startStabilityTimer()

	def isStable(self):
		return self._stable

	def isInPromotion(self):
		return self._inPromotion

	def endPromotion(self):
		if self._promotionTimer and self._promotionTimer.active():
			self._promotionTimer.cancel()
		days = self._reactor.seconds()/24/60/60
		self._promotionTimer = None
		self._stable = False
		self._inPromotion = False
		self._startStabilityTimer()

	def startPromotion(self, value):
		self._promotionValue = value
		self._inPromotion = True
		self._promotionTimer = self._reactor.callLater(SECS_IN_30DAYS, self.endPromotion)

	def promotionValue(self):
		return self._promotionValue

	def _startStabilityTimer(self):
		self._cancelStabilityTimer()
		self._stabilityTimer = self._reactor.callLater(\
			SECS_IN_30DAYS, self._becomeStable)

	def _cancelStabilityTimer(self):
		if self._stabilityTimer:
			self._stabilityTimer.cancel()

	def _becomeStable(self):
		self._stable = True
		self._stabilityTimer = None

class Price:
	def __init__(self, value, reactor=reactor):
		self._value = value
		self._state = PriceState(reactor)

	def stable(self):
		return self._state.isStable()

	def setValue(self, value):
		diff = self._calculateDiffInPercentage(value)

		if value > self._value:
			self._state.endPromotion()

		if diff >= 5 and diff <= 30 and self._state.isStable():
			self._state.startPromotion(self._value)

		if diff > 30 and self._state.isInPromotion():
			self._state.endPromotion()

		self._state.makeUnstable()
		self._value = value

	def _calculateDiffInPercentage(self, value):
		current = self._value
		if self._state.isInPromotion():
			current = self._state.promotionValue()
		diff = current - value	
		diff_percent = float(diff) / current * 100.0
		return diff_percent

	def value(self):
		return self._value
	
	def inPromotion(self):
		return self._state.isInPromotion()

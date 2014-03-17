from twisted.internet import reactor

SECS_IN_30DAYS=60*60*24*30

class PriceState(object):
	def __init__(self):
		pass

class Price:
	def __init__(self, value, reactor=reactor):
		self._stable = False
		self._inPromotion = False
		self._reactor = reactor
		self._value = value
		self._stabilityTimer = None
		self._promotionTimer = None
		self._promotionValue = 0

		self._startStabilityTimer()

	def stable(self):
		return self._stable

	def setValue(self, value):
		self._startStabilityTimer()

		diff = self._calculateDiffInPercentage(value)

		if value > self._value:
			self._unsetPromotion()

		if diff >= 5 and diff <= 30 and self._stable:
			self._setPromotion()

		if diff > 30 and self._inPromotion:
			self._unsetPromotion()

		self._value = value

		self._stable = False

	def _calculateDiffInPercentage(self, value):
		current = self._value
		if self._inPromotion:
			current = self._promotionValue
		diff = current - value	
		diff_percent = float(diff) / current * 100.0
		return diff_percent

	def _startStabilityTimer(self):
		self._cancelStabilityTimer()
		self._stabilityTimer = self._reactor.callLater(\
			SECS_IN_30DAYS, self._becomeStable)
	
	def _becomeStable(self):
		self._stable = True
		self._stabilityTimer = None

	def _cancelStabilityTimer(self):
		if self._stabilityTimer:
			self._stabilityTimer.cancel()

	def _setPromotion(self):
		self._promotionValue = self._value
		self._inPromotion = True
		self._promotionTimer = self._reactor.callLater(SECS_IN_30DAYS, self._unsetPromotion)

	def _unsetPromotion(self):
		if self._promotionTimer and self._promotionTimer.active():
			self._promotionTimer.cancel()
		self._promotionTimer = None
		self._stable = False
		self._inPromotion = False

	def value(self):
		return self._value
	
	def inPromotion(self):
		return self._inPromotion

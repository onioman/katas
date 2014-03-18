from twisted.internet import reactor

SECS_IN_30DAYS=60*60*24*30

class Promotion(object):
	def __init__(self, reactor):
		self._valid = None
		self._reactor = reactor
		self._originalPrice = 0

	def startFrom(self, price, notifyEnd):
		self._originalPrice = price
		self._valid = self._reactor.callLater(\
			SECS_IN_30DAYS, notifyEnd)

	def end(self):
		if self._valid and self._valid.active():
			self._valid.cancel()

	def startPrice(self):
		return self._originalPrice

	def ok(self):
		return (self._valid and self._valid.active())

class Stability(object):
	def __init__(self, reactor):
		self._pending = None
		self._reactor = reactor

	def reset(self):
		self._cancelPending()
		self._startPending()

	def _cancelPending(self):
		if self._pending and self._pending.active():
			self._pending.cancel()

	def _startPending(self):
		self._pending = self._reactor.callLater(\
			SECS_IN_30DAYS, self._becomeStable)

	def _becomeStable(self):
		pass

	def ok(self):
		return not self._pending.active()

class State(object):
	def __init__(self, reactor):
		self._stability = Stability(reactor)
		self._promotion = Promotion(reactor)

		self._stability.reset()

	def updateAfterPriceChange(self):
		self._stability.reset()

	def isStable(self):
		return self._stability.ok()

	def isInPromotion(self):
		return self._promotion.ok()

	def endPromotion(self):
		self._promotion.end()
		self._stability.reset()

	def startPromotion(self, price):
		self._promotion.startFrom(price, self._onPromotionEnd)

	def _onPromotionEnd(self):
		self._stability.reset()

	def promotionPrice(self):
		return self._promotion.startPrice()

class Price:
	def __init__(self, price, reactor=reactor):
		self._price = price
		self._state = State(reactor)

	def stable(self):
		return self._state.isStable()

	def changeTo(self, price):
		if self._price == price: return

		diff = self._calculateDiffInPercentage(price)

		if price > self._price:
			self._state.endPromotion()

		if diff >= 5 and diff <= 30 and self._state.isStable():
			self._state.startPromotion(self._price)

		if diff > 30 and self._state.isInPromotion():
			self._state.endPromotion()

		self._state.updateAfterPriceChange()
		self._price = price

	def _calculateDiffInPercentage(self, price):
		current = self._price
		if self._state.isInPromotion():
			current = self._state.promotionPrice()
		diff = current - price	
		diff_percent = float(diff) / current * 100.0
		return diff_percent

	def value(self):
		return self._price
	
	def inPromotion(self):
		return self._state.isInPromotion()

from twisted.trial import unittest
from twisted.internet.task import Clock

from libredpencil.Price import Price

class PriceTest(unittest.TestCase):
	def test_everythingInPlace(self):
		self.assertTrue(True)

	def test_noStableAfterCreation(self):
		self.assertFalse(self.instance.stable())

	def test_getValue(self):
		self.assertEqual(self.price, self.instance.value())

	def test_changeValue(self):
		self.instance.changeTo(95)
		self.assertEqual(95, self.instance.value())

	def test_sameValue(self):
		self.clock.advance(self.days30)
		self.assertTrue(self.instance.stable())
		self.instance.changeTo(100)
		self.assertTrue(self.instance.stable())
		

	def test_stableAfter30days(self):
		self.clock.advance(self.days30)

		self.assertTrue(self.instance.stable())

	def test_unstableWithChanges(self):
		self.clock.advance(self.days30-self.day1)
		self.instance.changeTo(99)
		self.clock.advance(self.day1)
		self.assertFalse(self.instance.stable())
	
	def test_becomeUnstableWhenChange(self):
		self.clock.advance(self.days30)

		self.instance.changeTo(45)
		self.assertFalse(self.instance.stable())

	def test_reductionDuringStability(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)

		self.assertTrue(self.instance.inPromotion())


	def test_lessThan5DiscountDuringStability(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(96)

		self.assertFalse(self.instance.inPromotion())

	def test_moreThan30DiscountDuringStability(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(69)

		self.assertFalse(self.instance.inPromotion())

	def test_promotionFinishAfter30days(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)

		self.clock.advance(self.days30-self.day1)
		self.assertTrue(self.instance.inPromotion())

		self.clock.advance(self.day1)
		self.assertFalse(self.instance.inPromotion())

	def test_furtherReduction(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.day1)
		self.instance.changeTo(94)
		self.clock.advance(self.days30-self.day1)

		self.assertFalse(self.instance.inPromotion())

	def test_bigReduction(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.day1)
		self.instance.changeTo(50)

		self.assertFalse(self.instance.inPromotion())

	def test_priceIncreaseDuringPromotion(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)

		self.clock.advance(self.day1)

		self.instance.changeTo(96)
		self.assertFalse(self.instance.inPromotion())

	def test_bigReductionFromTheStabilityPrice(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.day1)

		self.instance.changeTo(69)

		self.assertFalse(self.instance.inPromotion())

	def test_promotionPriceDoesChangeDuringPromotion(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.day1)
		
		self.instance.changeTo(90)

		self.instance.changeTo(69)
		self.assertFalse(self.instance.inPromotion())

	def test_stabilityPeriodBetweenPromotions(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.days30)
		self.instance.changeTo(90)
		self.assertFalse(self.instance.inPromotion())

	def test_stabilityCountFromFinishPromotion(self):
		self.clock.advance(self.days30)
		self.instance.changeTo(95)
		self.clock.advance(self.day1*15)
		self.instance.changeTo(69)
		self.clock.advance(self.day1*30)
		self.instance.changeTo(60)
		self.assertTrue(self.instance.inPromotion())


	def setUp(self):
		self.clock = Clock()
		self.days30 = 60*60*24*30
		self.day1 = 60*60*24
		self.price = 100
		self.instance = Price(self.price, reactor=self.clock)

	def tearDown(self):
		pass

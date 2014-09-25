from twisted.trial import unittest
from twisted.internet.task import Clock

from liballocate.Allocate import allocate

class AllocateTest(unittest.TestCase):
	def test_everythingInPlace(self):
		self.assertTrue(True)

	def test_allocatedEmpty(self):
		self.assertEqual([], allocate(20, []))

	def test_allocateZero(self):
		self.assertEqual([0,0,0], allocate(0, [10, 10, 10]))

	def test_everythingToOne(self):
		self.assertEqual([10, 0, 0], allocate(10, [1, 0, 0]))

	def test_nonDivisible(self):
		self.assertEqual([4, 3, 3], allocate(10, [1, 1, 1]))

	def test_firstPreference(self):
		self.assertEqual([4, 3], allocate(7, [50, 50]))

	def test_almostSame(self):
		self.assertEqual([3, 4], allocate(7, [50, 51]))

	def test_justOnTest(self):
		self.assertEqual([5,4,1], allocate(10, [4,4,1]))

	def setUp(self):
		pass

	def tearDown(self):
		pass

from twisted.trial import unittest
from twisted.internet.task import Clock
import math

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
		self.assertEqual([3, 3, 4], allocate(10, [1, 1, 1]))

	def test_lastGetsMore(self):
		self.assertEqual([3,4], allocate(7, [50, 50]))

	def test_almostSame(self):
		self.assertEqual([3, 4], allocate(7, [50, 51]))

	def test_justTest(self):
		self.assertEqual([4,5,1], allocate(10, [4,4,1]))

        def test_genAllocationAndWeightsSameLength(self):
            self.assertEqual(len(self.weights), len(self.allocation))

        def test_genExactQuantityAllocated(self):
            self.assertEqual(self.quantity, sum(self.allocation))

        def test_genProportionalAllocation(self):
            def _error(expected, got, index):
                msg=str( [(self.weights[i],self.allocation[i]) for i in range(len(self.allocation))])
                return "index %s -> (expected) %s != %s (got) -> %s"%(index, expected, got, msg)

            total_weight =  sum(self.weights)
            index = 0
            for weight in self.weights:
                p = weight / float(total_weight)
                expected = p * self.quantity
                got = self.allocation[index]
                self.assertLessEqual(abs(got - expected), 2, msg=_error(expected, got, index))
                index += 1

        def test_genFair(self):
            for i in range(len(self.weights)):
                for j in range(len(self.weights)):
                    if self.weights[i] > self.weights[j]:
                        self.assertGreaterEqual(self.allocation[i], self.allocation[j])

	def setUp(self):
            self.weights = self._random_list()
            self.quantity = self._random_quantity()

            self.allocation = allocate(self.quantity, self.weights)

        def _random_list(self):
            import random
            lenght = random.randint(0, 100)
            return random.sample(range(1000), lenght)

        def _random_quantity(self):
            import random
            return random.randint(0, 1000)


	def tearDown(self):
		pass

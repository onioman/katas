package com.onioman.katas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: onio
 * Date: 6/29/13
 * Time: 7:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class AreaRectanglesTest {
    @Test
    public void testNonIntersectingRectangles() {
        assertEquals(2, AreaRectangles.totalArea(0, 0, 1, 1, 2, 2, 3, 3));
    }

    @Test
    public void testCornerIntersection() {
        assertEquals(7, AreaRectangles.totalArea(0, 0, 2, 2, 1, 1, 3, 3));
    }

    @Test
    public void testContainedIntersection() {
        assertEquals(new Rectangle(0, 0, 3, 3).area(), AreaRectangles.totalArea(0, 0, 3, 3, 1, 1, 2, 2));
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
}

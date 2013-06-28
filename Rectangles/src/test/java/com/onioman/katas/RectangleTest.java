package com.onioman.katas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: onio
 * Date: 6/26/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class RectangleTest {
    @Test
    public void everythingInPlace() {
        assertTrue(true);
    }

    @Test
    public void testCalculateArea() {
        assertEquals(4, _instance.area());

        Rectangle _instance2 = new Rectangle(0, 0, 3, 2);
        assertEquals(6, _instance2.area());

        Rectangle zero = new Rectangle(0, 0, 0, 0);
        assertEquals(0, zero.area());
    }

    @Test
    public void testOutsidePoint() {
        assertFalse(_instance.containsPoint(3, 3));
    }

    @Test
    public void testInsidePoint() {
        assertTrue(_instance.containsPoint(1, 1));
    }

    @Test
    public void testBorderPoint() {
        assertTrue(_instance.containsPoint(0, 0));
        assertTrue(_instance.containsPoint(0, 2));
    }

    @Test
    public void testOutsideRectangle() {
        Rectangle rect = new Rectangle(3, 3, 4, 4);
        assertFalse(_instance.containsRect(rect));
    }

    @Test
    public void testInsideRectangle() {
        Rectangle rect = new Rectangle(1, 1, 2, 2);
        assertTrue(_instance.containsRect(rect));
    }

    @Test
    public void testInTheMiddleRectangle() {
        Rectangle rect = new Rectangle(1, 1, 3, 3);
        assertFalse(_instance.containsRect(rect));
    }

    @Test
    public void testLLIntersection() {
        Rectangle rect = new Rectangle(1, 1, 3, 3);
        assertEquals(1, _instance.intersectionWith(rect).area());
    }

    @Test
    public void testLRIntersection() {
        Rectangle rect = new Rectangle(-1, 1, 1, 3);
        assertEquals(1, _instance.intersectionWith(rect).area());
    }

    @Test
    public void testURIntersection() {
        Rectangle rect = new Rectangle(-1, -1, 1, 1);
        assertEquals(1, _instance.intersectionWith(rect).area());
    }

    @Test
    public void testULIntersection() {
        Rectangle rect = new Rectangle(1, -1, 3, 1);
        assertEquals(1, _instance.intersectionWith(rect).area());
    }

    @Test
    public void testNoIntersection() {
        Rectangle rect = new Rectangle(3, 3, 4, 4);
        assertEquals(0, _instance.intersectionWith(rect).area());
    }

    @Before
    public void setUp() throws Exception {
        _instance = new Rectangle(0, 0, 2, 2);
    }

    @After
    public void tearDown() throws Exception {

    }

    public Rectangle _instance;
}

package com.onioman.katas;

/**
 * Created with IntelliJ IDEA.
 * User: onio
 * Date: 6/29/13
 * Time: 7:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class AreaRectangles {
    public static int totalArea(int llx1, int lly1, int urx1, int ury1,
                                int llx2, int lly2, int urx2, int ury2) {
        Rectangle rect1 = new Rectangle(llx1, lly1, urx1, ury1);
        Rectangle rect2 = new Rectangle(llx2, lly2, urx2, ury2);
        return rect1.area() + rect2.area() - rect1.intersectionWith(rect2).area();
    }
}

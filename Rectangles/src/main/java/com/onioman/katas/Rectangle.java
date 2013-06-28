package com.onioman.katas;

import static java.lang.Math.hypot;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created with IntelliJ IDEA.
 * User: onio
 * Date: 6/26/13
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle {


    class Point {

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public final int x;
        public final int y;
    }

    public Rectangle(int llx, int lly, int urx, int ury) {
        this.ll = new Point(llx, lly);
        this.ur = new Point(urx, ury);
        this.lr = new Point(urx, lly);
        this.ul = new Point(llx, ury);
    }

    public int area() {
        int base = ur.x - ll.x;
        int height = ur.y - ll.y;
        return base*height;
    }

    private boolean containsPoint(Point p) {
        return containsPoint(p.x, p.y);
    }

    public boolean containsPoint(int x, int y) {
        return (x >= ll.x &&
                x <= ur.x &&
                y >= ll.y &&
                y <= ur.y);
    }

    public Rectangle intersectionWith(Rectangle rect) {
        int l = max(this.ll.x, rect.ll.x);
        int r = min(this.ur.x, rect.ur.x);
        int t = min(this.ur.y, rect.ur.y);
        int b = max(this.ll.y, rect.ll.y);

        if (l < r &&
            b < t) {
            return new Rectangle(l, b, r, t);
        } else {
            return new Rectangle(0, 0, 0, 0);
        }

    }

    public boolean containsRect(Rectangle rect) {
        return (this.containsPoint(rect.ll) &&
                this.containsPoint(rect.ur));
    }

    public final Point lr;
    public final Point ll;
    public final Point ur;
    public final Point ul;
}

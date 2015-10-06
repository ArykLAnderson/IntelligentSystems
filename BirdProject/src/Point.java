/**
 * Created by aryka on 10/5/2015.
 */

public class Point {

    private double _x;
    private double _y;
    private double _z;

    public Point(double x, double y, double z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public double X() {
        return _x;
    }

    public double Y() {
        return _y;
    }

    public double Z() {
        return _z;
    }

    public Point midPoint(Point that) {

        double x = (_x + that._x) / 2;
        double y = (_y + that._y) / 2;
        double z = (_z + that._z) / 2;

        return new Point(x, y, z);
    }
}

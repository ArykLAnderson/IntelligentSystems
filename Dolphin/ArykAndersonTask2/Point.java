/**
 * Created by aryka on 10/5/2015.
 */

public class Point {

    private double _x;
    private double _y;
    private double _z;
    private boolean _endpoint;

    public Point(double x, double y, double z, boolean endpoint) {
        _x = x;
        _y = y;
        _z = z;
        _endpoint = endpoint;
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

    public boolean isEndpoint() {
        return _endpoint;
    }

    public Point midPoint(Point that) {

        double x = (_x + that._x) / 2;
        double y = (_y + that._y) / 2;
        double z = (_z + that._z) / 2;

        return new Point(x, y, z, false);
    }

    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";
    }
}

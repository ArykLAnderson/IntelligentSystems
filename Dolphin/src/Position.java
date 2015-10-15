/**
 * Created by aryka on 10/5/2015.
 */

public class Position {

    private Point _location;
    private Point _orientation;

    public Position(Point location, Point orientation) {
        _location = location;
        _orientation = orientation;
    }

    public Point getLocation() {
        return _location;
    }

    public Point getOrientation() {
        return _orientation;
    }

    public double getRoll(Position nextPosition) {
        return 0;
    }

    public void setOrientation(Point orientation) {
        _orientation = orientation;
    }

    public String toString() {
        return "X: " + _location.X() + ", Y: " + _location.Y() + ", Z: " + _location.Z();
    }
}

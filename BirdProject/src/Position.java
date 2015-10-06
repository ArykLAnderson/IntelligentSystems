/**
 * Created by aryka on 10/5/2015.
 */

public class Position {

    private Point _location;
    private Line _orientation;

    public Position(Point location, Line orientation) {
        _location = location;
        _orientation = orientation;
    }

    public Point getLocation() {
        return _location;
    }

    public Line getOrientation() {
        return _orientation;
    }

    public double getRoll(Position nextPosition) {
        return 0;
    }

    public void setOrientation(Line orientation) {
        _orientation = orientation;
    }
}

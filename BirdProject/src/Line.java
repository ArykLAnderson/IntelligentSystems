/**
 * Created by aryka on 10/5/2015.
 */
public class Line {

    private Point _startPoint;
    private Point _endPoint;

    public Line(Point startPoint, Point endPoint) {
        _startPoint = startPoint;
        _endPoint = endPoint;
    }

    public Point midPoint() {
        return _startPoint.midPoint(_endPoint);
    }

    public Line unitVector() {
        return new Line(new Point(0,0,0), new Point((_endPoint.X() - _startPoint.X())/this.length(),
                                                    (_endPoint.Y() - _startPoint.Y())/this.length(),
                                                    (_endPoint.Z() - _startPoint.Z())/this.length()));
    }

    public Line vector() {
        return new Line(new Point(0,0,0), new Point(_endPoint.X() - _startPoint.X(),
                                                    _endPoint.Y() - _startPoint.Y(),
                                                    _endPoint.Z() - _startPoint.Z()));
    }

    public double length() {

        double sum = 0;

        sum += Math.pow(_startPoint.X() - _endPoint.X(), 2);
        sum += Math.pow(_startPoint.Y() - _endPoint.Y(), 2);
        sum += Math.pow(_startPoint.Z() - _endPoint.Z(), 2);

        return Math.sqrt(sum);
    }
}

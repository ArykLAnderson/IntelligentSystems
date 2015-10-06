import java.util.ArrayList;
import java.util.Random;

/**
 * Created by aryka on 10/5/2015.
 */

public class Bezier {

    private int _numLines;
    private double _stepSize;
    private boolean _noise;
    private double _degree;
    private Random _random;

    public Bezier(int numLines, double stepSize, boolean noise, double noiseDegree) {

        _numLines = numLines;
        _stepSize = stepSize;
        _noise = noise;
        _degree = noiseDegree;
        _random = new Random();
    }

    public ArrayList<Position> generateCurve() {

        /**
         * Generate _numLines + 1 Points
         * Between Each Point, generate more points according to @param stepSize
         * Add noise to points
         * Generate contiguous lines from points
         * For each line:
         *  Case 1: Line is an endpoint (first or last line)
         *      Generate points along the line to or from the midpoint
         *  Case 2: Line is middling in the path
         *      Generate Bezier curve using midpoints of line as ends and
         *      the endpoint where they join as the "control"
         *
         * Set the Orientations on the array of positions by pointing to the next position.
         * Last position maintains previous orientation
         */
        ArrayList<Point> targetPoints = new ArrayList<>();
        for (int i = 0; i <= _numLines; i++) {
            targetPoints.add(new Point(((_random.nextDouble() * 1000) - 500),
                                       ((_random.nextDouble() * 1000) - 500),
                                       ((_random.nextDouble() * 500))));
        }

        ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < targetPoints.size() - 1; i++) {

        }

        return null;
    }

    private void addNoise(ArrayList<Point> points, double degree) {

        for (int i = 0; i < points.size(); i++) {

            Point oldPoint = points.get(i);
            Point newPoint = new Point(oldPoint.X() + _random.nextGaussian() * _degree,
                                       oldPoint.Y() + _random.nextGaussian() * _degree,
                                       oldPoint.Z() + _random.nextGaussian() * _degree);

            points.set(i, newPoint);
        }
    }

    private void setOrientations(ArrayList<Position> positions) {

        /**
        * Set the Orientations on the array of positions by pointing to the next position.
        * Last position maintains previous orientation
        */
    }

    private Point generatePoint(Point startPoint, Point midPoint, Point endPoint, double t) {

        double sx = startPoint.X();
        double mx = midPoint.X();
        double ex = endPoint.X();

        double sy = startPoint.Y();
        double my = midPoint.Y();
        double ey = endPoint.Y();

        double sz = startPoint.Z();
        double mz = midPoint.Z();
        double ez = endPoint.Z();

        double x = getCoord(sx, mx, ex, t);
        double y = getCoord(sy, my, ey, t);
        double z = getCoord(sz, mz, ez, t);

        return new Point(x, y, z);
    }

    private double getCoord(double start, double mid, double end, double t) {

        //x = start * (1-t)^2 + mid * 2t(1 - t) + end * t^2
        return start * Math.pow((1 - t), 2) + mid*2*t*(1-t) + end*Math.pow(t, 2);
    }
}

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

        //Generate initial line points
        ArrayList<Point> targetPoints = new ArrayList<>();
        for (int i = 0; i <= _numLines; i++) {
            targetPoints.add(new Point(((_random.nextDouble() * 1000) - 500),
                                       ((_random.nextDouble() * 500)),
                                       ((_random.nextDouble() * 1000) - 500), true));
        }

        ArrayList<Point> positions = new ArrayList<>();

        //Generate new points along that line
        for (int i = 0; i < targetPoints.size() - 1; i++) {

            Point start = targetPoints.get(i);
            Point end = targetPoints.get(i + 1);

            Line wLine = new Line(start, end);
            positions.add(start);

            int numSteps = (int)(wLine.length() / _stepSize);
            double stepSizeActual = wLine.length()/numSteps;
            Line vector = wLine.unitVector();

            for (int j = 0; j < numSteps - 1; j++) {

                double x = wLine.startPoint().X() + vector.endPoint().X()*stepSizeActual*j;
                double y = wLine.startPoint().Y() + vector.endPoint().Y()*stepSizeActual*j;
                double z = wLine.startPoint().Z() + vector.endPoint().Z()*stepSizeActual*j;

                positions.add(new Point(x, y, z, false));
            }
            positions.add(end);
        }

        //Add noise to all the points generated so far
        this.addNoise(positions);

        //Generate Bezier curve points
        ArrayList<Point> bezier = new ArrayList<>();

        for (int i = 0; i < positions.size() - 2; i++) {

            Line line1 = new Line(positions.get(i), positions.get(i + 1));
            Line line2 = new Line(positions.get(i + 1), positions.get(i + 2));

            for (double t = 0; t < 1; t += 0.2) {
                if (i == 0) {
                    bezier.add(this.generatePoint(line1.startPoint(), line1.endPoint(), line2.midPoint(), t));
                }
                else if (i == positions.size() - 3) {
                    bezier.add(this.generatePoint(line1.midPoint(), line1.endPoint(), line2.endPoint(), t));
                }
                else {
                    bezier.add(this.generatePoint(line1.midPoint(), line1.endPoint(), line2.midPoint(), t));
                }
            }
        }

        //Set bird orientations
        ArrayList<Position> orientedPositions = this.setOrientations(bezier);

        return orientedPositions;
    }

    private void addNoise(ArrayList<Point> points) {

        for (int i = 0; i < points.size(); i++) {

            Point oldPoint = points.get(i);

            if (oldPoint.isEndpoint())
                continue;

            Point newPoint = new Point(oldPoint.X() + _random.nextGaussian() * _degree,
                                       oldPoint.Y() + _random.nextGaussian() * _degree,
                                       oldPoint.Z() + _random.nextGaussian() * _degree, false);

            points.set(i, newPoint);
        }
    }

    private ArrayList<Position> setOrientations(ArrayList<Point> points) {

        /**
        * Set the Orientations on the array of positions by pointing to the next position.
        * Last position maintains previous orientation
        */
        ArrayList<Position> wList = new ArrayList<>();
        for (Point wPoint : points) {
            wList.add(new Position(wPoint, null));
        }

        return wList; //WARNING THIS IS A STUB
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

        double x = getCoordinate(sx, mx, ex, t);
        double y = getCoordinate(sy, my, ey, t);
        double z = getCoordinate(sz, mz, ez, t);

        return new Point(x, y, z, false);
    }

    private double getCoordinate(double start, double mid, double end, double t) {

        //x = start * (1-t)^2 + mid * 2t(1 - t) + end * t^2
        return start * Math.pow((1 - t), 2) + mid*2*t*(1-t) + end*Math.pow(t, 2);
    }
}

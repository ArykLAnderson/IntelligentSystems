import java.util.ArrayList;
import java.util.Random;

/**
 * Created by aryka on 10/5/2015.
 */

public class Bezier {

    private int _numLines;
    private double _stepSize;
    private Random _random;

    public Bezier(int numLines, double stepSize) {

        _numLines = numLines;
        _stepSize = stepSize;
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
        ArrayList<Point> targetPoints = generatePoints();
        targetPoints = this.bezierPoints(targetPoints, 0.16);
        targetPoints = this.addSegments(targetPoints);
        targetPoints = this.bezierPoints(targetPoints, 0.16);
        ArrayList<Position> positions = this.setOrientations(targetPoints);

        return positions;
    }

    public ArrayList<Point> generatePoints() {

        ArrayList<Point> targetPoints = new ArrayList<>();

        for (int i = 0; i <= _numLines; i++) {
            targetPoints.add(new Point(((_random.nextDouble() * 1000) - 500),
                    ((_random.nextDouble() * 500)),
                    ((_random.nextDouble() * 1000) - 500), true));
        }

        return targetPoints;
    }

    public ArrayList<Point> generatePoints(Point point1, Point point2) {

        ArrayList<Point> targetPoints = new ArrayList<>();

        targetPoints.add(point1);
        targetPoints.add(point2);

        for (int i = 0; i < _numLines - 1; i++) {
            targetPoints.add(new Point(((_random.nextDouble() * 1000) - 500),
                    ((_random.nextDouble() * 500)),
                    ((_random.nextDouble() * 1000) - 500), true));
        }

        return targetPoints;
    }

    public ArrayList<Point> generatePoints(Point startPoint) {

        ArrayList<Point> targetPoints = new ArrayList<>();

        targetPoints.add(startPoint);
        for (int i = 0; i < _numLines; i++) {
            targetPoints.add(new Point(((_random.nextDouble() * 1000) - 500),
                    ((_random.nextDouble() * 500)),
                    ((_random.nextDouble() * 1000) - 500), true));
        }

        return targetPoints;
    }

    public ArrayList<Point> bezierPoints(ArrayList<Point> positions, double step) {
        ArrayList<Point> bezier = new ArrayList<>();

        for (int i = 0; i < positions.size() - 2; i++) {

            Line line1 = new Line(positions.get(i), positions.get(i + 1));
            Line line2 = new Line(positions.get(i + 1), positions.get(i + 2));

            for (double t = 0; t < 1; t += step) {

                Point temp = null;

                if (i == 0) {
                    temp = this.generatePoint(line1.startPoint(), line1.endPoint(), line2.midPoint(), t, true);
                }
                else if (i == positions.size() - 3) {
                    temp = this.generatePoint(line1.midPoint(), line1.endPoint(), line2.endPoint(), t, false);
                }
                else {
                    temp = this.generatePoint(line1.midPoint(), line1.endPoint(), line2.midPoint(), t, false);
                }

                //System.out.println(temp + ", " + t);

                if (temp != null)
                    bezier.add(temp);
            }
        }

        return bezier;
    }

    public ArrayList<Point> addSegments(ArrayList<Point> targetPoints) {
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
        }

        return positions;
    }

    public ArrayList<Position> setOrientations(ArrayList<Point> points) {

        /**
        * Set the Orientations on the array of positions by pointing to the next position.
        * Last position maintains previous orientation
        */
        ArrayList<Position> wList = new ArrayList<>();
        Point wPoint = null;
        int i = 0;

        for (; i < points.size() - 1; i ++) {
            Line wLine = new Line(points.get(i), points.get(i + 1));
            Line vec = wLine.unitVector();
            double distance = Math.sqrt(vec.endPoint().X()*vec.endPoint().X() + vec.endPoint().Z()*vec.endPoint().Z());
            double pitch = Math.atan2(vec.endPoint().Y(), distance);
            double yaw = Math.atan2(vec.endPoint().X(), vec.endPoint().Z());

            //convert to degrees
            pitch = pitch * 57.2958;
            yaw = yaw * 57.2958;

            //System.out.println(wLine);
            //System.out.println(vec);
            //System.out.println("X: " + vec.endPoint().X() + ", Y: " + vec.endPoint().Y() + ", Z: " + vec.endPoint().Z() + ", pitch: " + pitch + ", yaw: " + yaw);

            wPoint = new Point(yaw, pitch, 0, false);
            wList.add(new Position(points.get(i), wPoint));
        }

        wList.add(new Position(points.get(i), wPoint));

        return wList;
    }

    public Point generatePoint(Point startPoint, Point midPoint, Point endPoint, double t, boolean endpoint) {

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

        return new Point(x, y, z, endpoint);
    }

    public double getCoordinate(double start, double mid, double end, double t) {

        //x = start * (1-t)^2 + mid * 2t(1 - t) + end * t^2
        return start * Math.pow((1 - t), 2) + mid*2*t*(1-t) + end*Math.pow(t, 2);
    }
}

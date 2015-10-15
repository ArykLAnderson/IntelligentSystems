import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class FishSB implements SwimBehavior {

    public static final double STEP_SIZE = 35;
    private static Bezier bezier = new Bezier(2, STEP_SIZE);

    private ArrayList<Position> _curve;
    private int _curveIndex;
    private Fish _owner;
    private Point _targetEndPoint;
    private int _cooldown;

    public FishSB(Fish owner) {
        _owner = owner;
        _curveIndex = 0;
        _cooldown = 0;
    }

    public void swim() {

        if (_curve == null || _curve.size() == _curveIndex + 1) {

            ArrayList<Point> targetPoints;
            if (_curve == null)
                targetPoints = bezier.generatePoints();
            else
                targetPoints = bezier.generatePoints(_curve.get(_curveIndex).getLocation(), _targetEndPoint);

            for (int i = 0; i < targetPoints.size(); i++) {
                targetPoints.set(i, new Point(targetPoints.get(i).X(), 0, targetPoints.get(i).Z(), true));
            }

            _targetEndPoint = targetPoints.get(targetPoints.size()-1);

            targetPoints = bezier.bezierPoints(targetPoints, 0.15);
            targetPoints = bezier.addSegments(targetPoints);
            targetPoints = bezier.bezierPoints(targetPoints, 0.35);
            _curve = bezier.setOrientations(targetPoints);

            _curveIndex = 0;
        }

        _owner.setPosition(_curve.get(_curveIndex++));
    }

    public void handleCollision(ArrayList<Fish> collisions) {

        if (collisions == null)
            return;

        if (_cooldown != 0) {
            _cooldown--;
            return;
        }

        double  X = _owner.getPosition().getLocation().X(),
                Y = _owner.getPosition().getLocation().Y(),
                Z = _owner.getPosition().getLocation().Z(),
                x = 0,
                y = 0,
                z = 0;

        for (Fish fish : collisions) {

            x -= fish.getPosition().getLocation().X();
            y -= fish.getPosition().getLocation().Y();
            z -= fish.getPosition().getLocation().Z();
        }

        Point awayPoint = new Point((X + x)*STEP_SIZE, (Y + y) * STEP_SIZE, (Z+z) * STEP_SIZE, true);
        ArrayList<Point> targetPoints = bezier.generatePoints(_owner.getPosition().getLocation(), awayPoint);
        _targetEndPoint = targetPoints.get(targetPoints.size()-1);

        targetPoints = bezier.bezierPoints(targetPoints, 0.15);
        targetPoints = bezier.addSegments(targetPoints);
        targetPoints = bezier.bezierPoints(targetPoints, 0.35);
        _curve = bezier.setOrientations(targetPoints);

        _curveIndex = 0;
        _cooldown = 8;
    }
}

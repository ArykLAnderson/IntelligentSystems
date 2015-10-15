import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class FindSB implements SwimBehavior {

    private Dolphin _owner;
    private static Bezier bezier = new Bezier(2, FishSB.STEP_SIZE * 1.5);

    private ArrayList<Position> _curve;
    private int _curveIndex;
    private Point _targetEndPoint;

    public FindSB(Dolphin owner) {
        _owner = owner;
    }

    public void swim() {

        if (Math.random() < 0.01) {
            _owner.setJump();
            _curve = null;
            _curveIndex = 0;
            _owner.swim();
            return;
        }

        if (_curve == null || _curve.size() == _curveIndex + 1) {

            ArrayList<Point> targetPoints;
            if (_curve == null)
                targetPoints = bezier.generatePoints(_owner.getPosition().getLocation());
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

        Fish seen = FishController.instance().detectFish(_owner);

        if (seen != null) {
            _owner.setFollow();
            _curve = null;
            _curveIndex = 0;
        }
    }

    public void handleCollision(ArrayList<Fish> collisions) {

    }
}

import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class JumpSB implements SwimBehavior {

    private Dolphin _owner;
    private ArrayList<Position> _jumpPoints;
    private int _jumpIndex;
    private static Bezier bezier = new Bezier(2, FishSB.STEP_SIZE * 1.5);

    public JumpSB(Dolphin owner) {
        _owner = owner;
        _jumpIndex = 0;
    }

    public void swim() {

        if (_jumpPoints == null) {

            Line wLine = new Line(_owner.getPrevious().getLocation(), _owner.getPosition().getLocation());
            Line unit = wLine.unitVector();
            double length = 250 + 100*Math.random();
            Point startPoint = new Point(   _owner.getPrevious().getLocation().X(),
                                            _owner.getPrevious().getLocation().Y(),
                                            _owner.getPrevious().getLocation().Z(),
                                            true);

            Line jumpLine = new Line(startPoint, new Point( unit.endPoint().X()*length,
                                                            unit.endPoint().Y()*length,
                                                            unit.endPoint().Z()*length,
                                                            true));

            Point midPoint = new Point(jumpLine.midPoint().X(), 150, jumpLine.midPoint().Z(), true);

            ArrayList<Point> jumpCurve = new ArrayList<>();
            jumpCurve.add(jumpLine.startPoint());
            jumpCurve.add(midPoint);
            jumpCurve.add(jumpLine.endPoint());

            jumpCurve = bezier.bezierPoints(jumpCurve, 0.15);
            jumpCurve = bezier.addSegments(jumpCurve);
            jumpCurve = bezier.bezierPoints(jumpCurve, 0.35);
            _jumpPoints = bezier.setOrientations(jumpCurve);

            _jumpIndex = 0;
        }

        if (_jumpPoints.size() == _jumpIndex) {
            _owner.setFollow();
            _jumpPoints = null;
            _jumpIndex = 0;
            _owner.swim();
            return;
        }

        _owner.setPosition(_jumpPoints.get(_jumpIndex++));
    }

    public void handleCollision(ArrayList<Fish> collisions) {

    }
}

import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class FollowSB implements SwimBehavior {

    private Dolphin _owner; //Rigid!

    public FollowSB(Dolphin owner) {
        _owner = owner;
    }

    public void swim() {

        if (Math.random() < 0.01) {
            _owner.setJump();
            _owner.swim();
            return;
        }

        Fish seen = FishController.instance().detectFish(_owner);

        if (seen == null) {
            _owner.setFind();
            _owner.swim();
            return;
        }

        Line wLine = new Line(_owner.getPosition().getLocation(), seen.getPosition().getLocation());
        Line vec = wLine.unitVector();
        double distance = Math.sqrt(vec.endPoint().X()*vec.endPoint().X() + vec.endPoint().Z()*vec.endPoint().Z());
        double pitch = Math.atan2(vec.endPoint().Y(), distance);
        double yaw = Math.atan2(vec.endPoint().X(), vec.endPoint().Z());

        //convert to degrees
        pitch = pitch * 57.2958;
        yaw = yaw * 57.2958;

        Point newPoint = new Point( _owner.getPosition().getLocation().X() + vec.endPoint().X()*FishSB.STEP_SIZE*1.5/10,
                                    _owner.getPosition().getLocation().Y() + vec.endPoint().Y()*FishSB.STEP_SIZE*1.5/10,
                                    _owner.getPosition().getLocation().Z() + vec.endPoint().Z()*FishSB.STEP_SIZE*1.5/10,
                                    false); //Divide by 10 here because I generate a lot of extra points for the curves
                                            //Makes it look better, but not quite 'right'

        Position newPosition = new Position(newPoint, new Point(yaw, pitch, 0, false));
        _owner.setPosition(newPosition);
    }

    public void handleCollision(ArrayList<Fish> collisions) {

    }
}

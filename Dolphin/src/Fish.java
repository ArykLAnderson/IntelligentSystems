import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class Fish {

    private String _label;
    private SwimBehavior _swimBehavior;
    private Position _currentPosition;

    private Fish() {}

    public Fish(String label) {
        _label = label;
        _swimBehavior = new FishSB(this);
        _currentPosition = new Position(new Point(0, 0, 0, false), new Point(0, 0, 0, false));
    }

    public void display() {
        ViewerAccess.instance().doAddEvent(_label, _currentPosition.getLocation().X(), 0, _currentPosition.getLocation().Z(), _currentPosition.getOrientation().X(), _currentPosition.getOrientation().Y(), 0);
    }

    public void swim() {
        _swimBehavior.swim();
    }

    public void handleCollision() {

        ArrayList<Fish> collisions = FishController.instance().checkCollisions(this);
        _swimBehavior.handleCollision(collisions);
    }

    public Position getPosition() {
        return _currentPosition;
    }

    public void setPosition(Position newPosition) {
        _currentPosition = newPosition;
    }

    public String getLabel() {
        return _label;
    }
}

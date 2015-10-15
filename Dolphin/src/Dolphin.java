/**
 * Created by Aryk on 10/14/2015.
 */
public class Dolphin {

    private FindSB _find;
    private FollowSB _follow;
    private JumpSB _jump;
    private SwimBehavior _swimBehavior;
    private String _label;
    private Position _previousPosition;
    private Position _currentPosition;

    public Dolphin() {
        _label = "dolphin";
        _find = new FindSB(this);
        _follow = new FollowSB(this);
        _jump = new JumpSB(this);
        _swimBehavior = _find;
        _previousPosition = new Position(new Point(0, 0, 0, false), new Point(0, 0, 0, false));
        _currentPosition = _previousPosition;
    }

    public void display() {
        ViewerAccess.instance().doAddEvent(_label, _currentPosition.getLocation().X(), _currentPosition.getLocation().Y(), _currentPosition.getLocation().Z(), _currentPosition.getOrientation().X(), _currentPosition.getOrientation().Y(), 0);
    }

    public void swim() {
        _swimBehavior.swim();
    }

    public void setPosition(Position newPosition) {

        _previousPosition = _currentPosition;
        _currentPosition = newPosition;
    }

    public Position getPosition() {
        return _currentPosition;
    }

    public Position getPrevious() {
        return _previousPosition;
    }

    public void setFollow() {
        _swimBehavior = _follow;
    }

    public void setJump() {
        _swimBehavior = _jump;
    }

    public void setFind() {
        _swimBehavior = _find;
    }
}

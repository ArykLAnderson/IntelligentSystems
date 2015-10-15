import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public class FishController {

    private static FishController _instance;
    private final double _collisionDistance = 30;

    public static FishController instance() {

        if (_instance == null) {
            _instance = new FishController();
        }
        return _instance;
    }

    private ArrayList<Fish> _fish;

    private FishController() {

        _fish = new ArrayList<Fish>();
        _fish.add(new Fish("fish1"));
        _fish.add(new Fish("fish2"));
        _fish.add(new Fish("fish3"));
        _fish.add(new Fish("fish4"));
        _fish.add(new Fish("fish5"));
    }

    public ArrayList<Fish> checkCollisions(Fish fish) {

        ArrayList<Fish> collisions = new ArrayList();

        for (Fish wFish : _fish) {

            if (wFish.getLabel().equals(fish.getLabel()))
                continue;

            Line wLine = new Line(fish.getPosition().getLocation(), wFish.getPosition().getLocation());

            if (wLine.length() < _collisionDistance)
                collisions.add(wFish);
        }

        if (collisions.size() == 0)
            return null;
        else
            return collisions;
    }

    public Fish detectFish(Dolphin dolphin) {

        double lowestDistance = 100;
        Fish found = null;

        Line dolphinDirection = (new Line(dolphin.getPrevious().getLocation(), dolphin.getPosition().getLocation())).unitVector();

        for (Fish fish : _fish) {
            Line wLine = new Line(fish.getPosition().getLocation(), dolphin.getPosition().getLocation());
            Line unit = wLine.unitVector();

            if (wLine.length() < lowestDistance) {
                if (Math.abs(Math.atan2(dolphinDirection.endPoint().X() - unit.endPoint().X(), dolphinDirection.endPoint().Z() - unit.endPoint().Z())) < 0.610865) {
                    found = fish;
                    lowestDistance = wLine.length();
                }
            }
        }

        return found;
    }

    public void advanceFish() {

        for (Fish fish : _fish) {
            fish.handleCollision();
            fish.swim();
            fish.display();
        }
    }

    public void initFish() {

        for (Fish fish : _fish) {
            fish.swim();
        }
    }
}

import java.util.ArrayList;

/**
 * Created by Aryk on 10/14/2015.
 */
public interface SwimBehavior {

    public void swim();
    public void handleCollision(ArrayList<Fish> fish);
}

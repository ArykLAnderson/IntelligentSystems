import cs480viewer.task1.Viewer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Bezier generator = new Bezier(15, 75, true, 5);
        ArrayList<Position> positions = generator.generateCurve();

        Viewer viewer = new Viewer("track1_5.trk");
        for(Position position : positions) {
            viewer.doAddEvent("bird", position.getLocation().X(), position.getLocation().Y(), position.getLocation().Z(), position.getOrientation().X(), position.getOrientation().Y(), 0);
            viewer.doAdvanceEventClock(25);
        }
    }
}

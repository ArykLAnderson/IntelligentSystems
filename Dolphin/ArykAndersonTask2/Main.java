import cs480viewer.task2.Viewer;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Viewer viewer = ViewerAccess.getInstance("track2_4_2.trk");

        Dolphin dolphin = new Dolphin();
        FishController.instance().initFish();

        while (true) {
            FishController.instance().advanceFish();
            dolphin.swim();
            dolphin.display();
            viewer.doAdvanceEventClock();
        }
    }
}

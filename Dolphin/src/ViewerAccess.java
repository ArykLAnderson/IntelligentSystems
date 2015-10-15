import cs480viewer.task2.Viewer;

/**
 * Created by Aryk on 10/14/2015.
 */
public class ViewerAccess {

    private static Viewer _instance;

    public static Viewer instance() {
        if(_instance == null) {
            _instance = new Viewer("test_track.trk", 100);
        }
        return _instance;
    }

    public static Viewer getInstance(String filename) {
        if (_instance == null) {
            _instance = new Viewer(filename, 100);
        }
        return _instance;
    }
}

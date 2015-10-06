import cs480viewer.task1.Viewer;

import java.util.ArrayList;

//=============================================================================================================================================================
public class CS480Task1Template
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      Bezier generator = new Bezier(10, 10, true, 2);
      ArrayList<Position> positions = generator.generateCurve();
      for(Position position : positions) {
         System.out.println(position);
      }
      //new CS480Task1Template();
   }

   private final Viewer _viewer;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task1Template()
   {
      _viewer = new Viewer("C:\\Users\\aryka\\OneDrive\\Documents\\GitHub\\IntelligentSystems\\BirdProject\\track1_1.trk");

      doTest1();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
      for (double i = 0; i < 150; i += 5)
      {
         // call this one per movement; the arguments are id (use "bird"), x, y, z, yaw, pitch, and roll
         _viewer.doAddEvent("bird", i, (i * i * 0.01), (i * i * 0.025), (i * 10), i, (i * 3));

         // call this after each movement; the argument is the delay in milliseconds
         _viewer.doAdvanceEventClock(50);
      }
   }
}

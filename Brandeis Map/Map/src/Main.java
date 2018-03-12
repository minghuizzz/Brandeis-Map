import java.io.File;

public class Main {

   public static void main(String[] args) {
		
      String mapFilename = "file/BrandeisMapLabeledCropped.png";
      if(args.length>0) {
         if ( new String("jpg").equals(args[0]) ) mapFilename = "file/BrandeisMapLabeledCropped.jpg";
         else if ( new String("png").equals(args[0]) ) mapFilename = "file/BrandeisMapLabeledCropped.png";
         else mapFilename = "file/" + args[0];
         }

      String routeFilename = "file/RouteCropped.txt";
      if (
         ( new String("file/BrandeisMapLabeled.jpg").equals(mapFilename) ) ||
         ( new String("file/BrandeisMapLabeled.png").equals(mapFilename) )
         )
         routeFilename = "file/Route.txt";
		
      File inputFile = new File(routeFilename);
      File mapFile = new File(mapFilename);
		
      RouteDisplayFrame frame = new RouteDisplayFrame(inputFile,mapFile);
      frame.load();
      frame.display();

      }
   }

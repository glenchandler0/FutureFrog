package swoledcademy.com.futurefrogv21;

/**
 * Created by GlenChandler on 12/18/2015.
 */
public class Happenings
{
    //Maps for door
    /*
     * 0 Main office
     * 1 Garage
     * 2 Boss's Office
     * 3 Bathroom
     * 4 First space map
     */

    //Notice: THIS NEEDS TO ALL CHANGE TO ONE GAMESTATE


    /*
     * 0-Hasn't interacted with yet
     * 1-Has talked to
     * 2-Went to parking lot
     * 3-Found boss note
     */
    //public static int waterInteraction = 0;

    /*
     * 0 = Hasn't left first room yet.
     * 1 = Has left first room.
     */
    //public static int stage = 0; will refer to MapManipulator.mapBitmapNum instead
    //Replacing with game state
    public static int gameState = 0;
}

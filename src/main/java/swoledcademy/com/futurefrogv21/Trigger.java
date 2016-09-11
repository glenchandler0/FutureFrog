package swoledcademy.com.futurefrogv21;

/**
 * The purpose of Trigger is to create a one time use or specific
 * interactable object rather than creating an entirely new class.
 *
 * Trigger objects only have a dialogue trait, which is used to identify its effect.
 */
public class Trigger extends Entity
{
    public String dialogue = "";

    public Trigger(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name)
    {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);

        MapManipulator.map.get(mapY).set(mapX, 'X');
    }

    public int getBitmap()
    {
        int frameImage = R.drawable.painting_clock;

        return frameImage;
    }

    public int interact(String name)
    {
        if(this.name.toUpperCase().equals("PARKINGLOTTRIGGER"))
        {
            if(Happenings.gameState == 1)
                dialogue = "Looks like he's not here...";
        }
        else if(this.name.toUpperCase().equals("BOSSNOTE"))
        {
            if(MapManipulator.entities.get(0).direction == 2) {
                dialogue = "-The Note Reads-\nI saw the future. I saw what will be.\nI can't stand living this life.\nThe dissapointment of the present is\ntoo much.";
                Happenings.gameState = 3;
            }
            else
                dialogue = "There's a note...";
            return 0;
        }
        else if(this.name.toUpperCase().equals("MYDESK"))
        {
            if(MapManipulator.player.direction == 0)
            {
                if(Happenings.gameState == 0)
                    dialogue = "God, I hate my clients";
                else
                    dialogue = "Of all times, now is the worst\ntime to work.";
            }
            else
            {
                dialogue = MapManipulator.player.name + "'s Desk.";
            }
            return 0;
        }
        else if(this.name.toUpperCase().equals("PORTAL"))
        {
            //Disabled for now because I don't want to redo this map, but I don't want to transport to a messed up one.
            //if(Happenings.gameState >= 4) {
            //MapManipulator.loadSpecificMap(4); }
            MapManipulator.writeUserDataToFile(); //Warning
            //dialogue = "Game state saved...";
            return 0;
        }

        MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
        return 0;
    }

    @Override
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    @Override
    public String getDialogue() {
        return dialogue;
    }
}

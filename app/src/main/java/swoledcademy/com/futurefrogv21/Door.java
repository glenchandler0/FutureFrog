package swoledcademy.com.futurefrogv21;

import android.util.Log;

/**
 * Created by GlenChandler on 12/10/2015.
 */
public class Door extends Entity
{
    private int mapTransportNum;
    public String dialogue = "";

    public Door(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name, int mapTransportNum) {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);

        this.mapTransportNum = mapTransportNum;

        super.pixelWidth = 275;
        super.pixelHeight = 275;
        if (direction == 0) {
            super.xOffset = 0.5;
            super.yOffset = 0.7;
            super.pixelWidth = 200;
            super.pixelHeight = 200;
        } else if (direction == 1) {
            super.xOffset = 0;
        } else if (direction == 2) {
            super.yOffset = 0;
        } else if (direction == 3) {
            super.xOffset = 1;
        }

    }

    public int getBitmap()
    {
        int frameImage = R.drawable.door;
        switch(direction)
        {
            case 0:
                break;
            case 1:
                frameImage = R.drawable.door_right;
                break;
            case 2:
                frameImage = R.drawable.door_down;
                break;
            case 3:
                frameImage = R.drawable.door_left;
                break;
            default:
                frameImage = R.drawable.door;
                break;
        }

        return frameImage;
    }

    public int interact(String name)
    {
        if(mapTransportNum == 1) {
            if (Happenings.stage == 0) {
                if (Happenings.waterInteraction == 0) {
                    dialogue = "It's locked.";
                } else if (Happenings.waterInteraction == 1) {
                    MapManipulator.loadSpecificMap(mapTransportNum);
                }
            } else {
                MapManipulator.loadSpecificMap(mapTransportNum);
            }
        }
        else if(mapTransportNum == 2) {
            if(Happenings.stage == 0)
                dialogue = "Sound's like Boss has been sobbing\nin there for a couple hours.\nI probably shouldn't go in.\n    Yet.";
            if(Happenings.stage > 0)
            {
                MapManipulator.loadSpecificMap(mapTransportNum);
            }
        }
        else if(mapTransportNum == 3) {
            if(Happenings.stage == 0)
                dialogue = "I think I've committed enough\ntime theft in the bathroom\nfor today.";
            else if(Happenings.stage >= 2)
                MapManipulator.loadSpecificMap(mapTransportNum);
            else
                dialogue = "It's locked.\nThe janitor must be in there.";
        }
        else
        {
            MapManipulator.loadSpecificMap(mapTransportNum);
        }

        //End of function
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

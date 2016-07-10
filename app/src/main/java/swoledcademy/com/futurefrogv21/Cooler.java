package swoledcademy.com.futurefrogv21;

/**
 * Created by GlenChandler on 12/18/2015.
 */
public class Cooler extends Entity
{
    public String dialogue = "";

    public Cooler(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name)
    {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);
        super.xOffset = 0.5;
        super.yOffset = 0.8;
    }

    public int getBitmap()
    {
        int frameImage = R.drawable.water;

        return frameImage;
    }

    public int interact(String name)
    {
        if(Happenings.stage == 0) {
            if (Happenings.waterInteraction == 0) {
                dialogue = "???: I know you hate this job.\nTake this key and meet me outside.\nEverything will change soon.";
                Happenings.waterInteraction = 1;
            } else if (Happenings.waterInteraction == 1) {
                dialogue = "...";
            }
        }
        else if(Happenings.stage == 3)
        {
            dialogue = "???: Good, you're here.\nIf you want your life to change\nforever, come with me.\nWalk through this portal.";
            Happenings.waterInteraction = 2;
        }
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

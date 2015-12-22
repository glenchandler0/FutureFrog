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
                dialogue = "???: It's not safe here.\nTake this key and meet me outside.";
                Happenings.waterInteraction = 1;
            } else if (Happenings.waterInteraction == 1) {
                dialogue = "...";
            }
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

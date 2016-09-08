package swoledcademy.com.futurefrogv21;

/**
 * Created by GlenChandler on 12/20/2015.
 */
public class Painting extends Entity
{
    public String dialogue = "";

    public Painting(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name)
    {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);
        super.xOffset = 0.5;
        super.yOffset = 0.5;
        super.pixelWidth = 100;
        super.pixelHeight = 100;
    }

    public int getBitmap()
    {
        int frameImage = R.drawable.painting_trump;

        if(this.name.toUpperCase().equals("TRUMP"))
            frameImage = R.drawable.painting_trump;
        else if(this.name.toUpperCase().equals("CLOCK"))
            frameImage = R.drawable.painting_clock;
        else if(this.name.toUpperCase().equals("DIPLOMA"))
            frameImage = R.drawable.painting_diploma;

        return frameImage;
    }

    public int interact(String name)
    {
        if(this.name.toUpperCase().equals("TRUMP"))
            dialogue = "Man, what a president.";
        else if(this.name.toUpperCase().equals("CLOCK"))
            dialogue = "I swear this clock is stuck at four fifty nine...";
        else if(this.name.toUpperCase().equals("DIPLOMA"))
            dialogue = "I would jump out the window with this\ndegree too.";
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

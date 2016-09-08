package swoledcademy.com.futurefrogv21;

// Created by GlenChandler on 7/10/2016.
public class Computer extends Entity
{
    public String dialogue = "";
    private boolean powerOn = false;

    public Computer(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name)
    {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);
        super.xOffset = 0.5;
        super.yOffset = 0.8;
    }

    public int getBitmap()
    {
        int frameImage;
        if(!powerOn)
            frameImage = R.drawable.computer_off;
        else
            frameImage = R.drawable.computer_on;

        return frameImage;
    }

    public int interact(String name)
    {
       if(powerOn)
            powerOn = false;
       else
            powerOn = true;

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

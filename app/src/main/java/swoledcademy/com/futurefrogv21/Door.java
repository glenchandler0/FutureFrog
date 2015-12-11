package swoledcademy.com.futurefrogv21;

/**
 * Created by GlenChandler on 12/10/2015.
 */
public class Door extends Entity
{
    private int mapTransportNum;
    private String dialogue = "";

    public Door(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name, int mapTransportNum) {
        super(mapX, mapY, pixelWidth, pixelHeight, direction, name);

        this.mapTransportNum = mapTransportNum;
    }

    public int getBitmap()
    {
        int frameImage = R.drawable.door;

        return frameImage;
    }

    public int interact(String name)
    {
        dialogue = "Travelling to map " + mapTransportNum +".";
        MapManipulator.loadSpecificMap(mapTransportNum);
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

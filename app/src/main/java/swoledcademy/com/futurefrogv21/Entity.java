package swoledcademy.com.futurefrogv21;

import android.graphics.Point;

public class Entity
{
    //GamePanel will convert the mapX and Y to pixelX and Y later on
    public Point mapCoords = new Point();

    public int pixelWidth;
    public int pixelHeight;

    public int direction; // 0=up, 1=right, 2=down, 3=left
    public int frameCounter;

    public String name;

    //The x and y will be mapX and mapY
    public Entity(int mapX, int mapY, int pixelWidth, int pixelHeight, int direction, String name)
    {
        mapCoords.x = mapX;
        mapCoords.y = mapY;
        MapManipulator.map.get(mapY).set(mapX, '@');

        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;

        this.direction = direction;
        this.name = name;
    }

    //For the entity class, this will be a stub, and only player will return these certain drawables
    public int getBitmap()
    {
        int frameImage = R.drawable.zman00;

        if(frameCounter == 0)
            frameImage = R.drawable.zman00;
        else if(frameCounter >= 1)
        {
            frameCounter = 0;
            return R.drawable.zman01;
        }
        frameCounter++;
        return frameImage;
    }

    //INCOMPLETE MOVE FUNCTION
    public void move(int direction)
    {
        this.direction = direction;

        try
        {
            if (direction == 0) //up
            {
                if (!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y - 1).get(mapCoords.x))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.y -= 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }

            }
            else if (direction == 1) //right
            {
                if (!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y).get(mapCoords.x + 1))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.x += 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }
            }
            else if(direction == 2) //down
            {
                if(!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y + 1).get(mapCoords.x))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.y += 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }
            }
            else if(direction == 3) //left
            {
                if(!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y).get(mapCoords.x - 1))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.x -= 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

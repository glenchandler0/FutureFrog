package swoledcademy.com.futurefrogv21;

import android.graphics.Point;
import android.util.Log;

import java.security.SecureClassLoader;
import java.security.SecureRandom;

public class Entity
{
    //GamePanel will convert the mapX and Y to pixelX and Y later on
    public Point mapCoords = new Point();

    public int pixelWidth;
    public int pixelHeight;

    public double xOffset = 0.5;
    public double yOffset = 0.5;

    public int direction; // 0=up, 1=right, 2=down, 3=left
    public int frameCounter;

    public String name;
    public String dialogue = "";

    SecureRandom rand;

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

        rand = new SecureRandom();
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
            if (direction == 0) //up---
            {
                if(MapManipulator.map.get(mapCoords.y - 1).get(mapCoords.x) == '@' || MapManipulator.map.get(mapCoords.y - 1).get(mapCoords.x) == 'X') //If the spot moved into is an entity
                {
                    Log.i("WALKED INTO SOMETHING", "WALKED INTO SOMETHING");
                    for(int i = 1; i < MapManipulator.entities.size(); i++) //Finds which entity was interacted with
                    {
                        if(MapManipulator.entities.get(i).mapCoords.x == mapCoords.x && MapManipulator.entities.get(i).mapCoords.y == mapCoords.y - 1)
                        {
                            MapManipulator.entities.get(i).interact(name); //IMPLIMENT THIS
                            break;
                        }
                    }
                }
                else if (!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y - 1).get(mapCoords.x))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.y -= 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }

            }

            else if (direction == 1) //right---
            {
                if(MapManipulator.map.get(mapCoords.y).get(mapCoords.x + 1) == '@' || MapManipulator.map.get(mapCoords.y).get(mapCoords.x + 1) == 'X') //If the spot moved into is an entity
                {
                    Log.e("WALKED INTO SOMETHING", "WALKED INTO SOMETHING");
                    for(int i = 1; i < MapManipulator.entities.size(); i++) //finds which entity was interacted with
                    {
                        if(MapManipulator.entities.get(i).mapCoords.x == mapCoords.x + 1 && MapManipulator.entities.get(i).mapCoords.y == mapCoords.y)
                        {
                            MapManipulator.entities.get(i).interact(name); //IMPLIMENT THIS

                            break;
                        }
                    }
                }
                else if (!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y).get(mapCoords.x + 1))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.x += 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }
            }

            else if(direction == 2) //down---
            {
                if(MapManipulator.map.get(mapCoords.y + 1).get(mapCoords.x) == '@' || MapManipulator.map.get(mapCoords.y + 1).get(mapCoords.x) == 'X') //If the spot moved into is an entity
                {
                    Log.e("WALKED INTO SOMETHING", "WALKED INTO SOMETHING");
                    for(int i = 1; i < MapManipulator.entities.size(); i++) //finds which entity was interacted with
                    {
                        if(MapManipulator.entities.get(i).mapCoords.x == mapCoords.x && MapManipulator.entities.get(i).mapCoords.y == mapCoords.y + 1)
                        {
                            MapManipulator.entities.get(i).interact(name); //IMPLIMENT THIS
                            break;
                        }
                    }
                }
                else if(!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y + 1).get(mapCoords.x))))
                {
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '-');
                    mapCoords.y += 1;
                    MapManipulator.map.get(mapCoords.y).set(mapCoords.x, '@');
                }
            }

            else if(direction == 3) //left---
            {
                if(MapManipulator.map.get(mapCoords.y).get(mapCoords.x - 1) == '@' || MapManipulator.map.get(mapCoords.y).get(mapCoords.x - 1) == 'X') //If the spot moved into is an entity
                {
                    Log.e("WALKED INTO SOMETHING", "WALKED INTO SOMETHING");
                    for(int i = 1; i < MapManipulator.entities.size(); i++) //finds which entity was interacted with
                    {
                        if(MapManipulator.entities.get(i).mapCoords.x - 1== mapCoords.x && MapManipulator.entities.get(i).mapCoords.y == mapCoords.y)
                        {
                            MapManipulator.entities.get(i).interact(name); //IMPLIMENT THIS
                            break;
                        }
                    }
                }
                else if(!(MapManipulator.noPass.contains(MapManipulator.map.get(mapCoords.y).get(mapCoords.x - 1))))
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

    public int interact(String name)
    {
        //Player thoughts are 15 spaces out
        switch(rand.nextInt(10))
        {
            case 0:
            case 1:
            case 2:
            case 3:
                dialogue = String.format("%s: Listen, %s, you really\nneed to get the tax forms in\nby Friday.", this.name, name);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                dialogue = String.format("%s: I was thinking about\nbringing one of my Hawaiian shirts\nto casual friday.", this.name);
                break;
            case 8:
                dialogue = String.format("%s: I can't believe the\ncoffee machine is broken again.", this.name);
                break;
            default:
                dialogue = String.format("%s: Howdy, %s!", this.name, name);
        }
        return -1;
    }

    public void setDialogue(String d)
    {
        dialogue = d;
    }

    public String getDialogue()
    {
        return dialogue;
    }
}

package swoledcademy.com.futurefrogv21;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MapManipulator
{
    //ALL STATIC

    public static Entity player; //Initialized in MainThread when game starts.

    //Entities! can be referenced from everywhere. It is cleared every time a new map is set
    public static ArrayList<Entity> entities = new ArrayList<Entity>(); //Static so any class can add or manipulate entities

    public static ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();
    public static ArrayList<Character> noPass = new ArrayList<Character>(); //This is initiated in

    public static int mapDimX;
    public static int mapDimY;

    public static int mapBitmapNum = 0;

    private static Context c;

    public static void setContext(Context context)
    {
        c = context;
    }

    //Sets map to the text file, so it can be manipulated in game.
    public static void loadMapFromFile(int mapNum)
    {
        //c = context;
        map.clear();
        //ArrayList<Character> temp = new ArrayList<>();

        AssetManager am = c.getAssets();
        String s = "";
        String t = "";
        try
        {
            InputStream is = am.open("map" + mapNum + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            Scanner cLine;

            int columnCounter = 0;
            int rowCounter = 0;

            while((line = reader.readLine()) != null)
            {
                columnCounter = 0;
                cLine = new Scanner(line);
                map.add(new ArrayList<Character>());

                while(cLine.hasNext())
                {
                    t = cLine.next();
                    map.get(rowCounter).add(t.charAt(0));
                    s += map.get(rowCounter).get(columnCounter);
                    columnCounter++;
                }
                rowCounter++;
                s += "\n";
            }

            mapBitmapNum = mapNum;
            mapDimX = columnCounter;
            mapDimY = rowCounter;
        }
        catch(Exception e)
        {
            Log.e("EXCEPTION FOUND", "EXTION FOUND IN STOREMAP()");
            e.printStackTrace();

        }
        //printMap();
    }

    public static void printMap()
    {
        for(int i = 0; i < map.size(); i++)
        {
            String s = "";
            for(int j = 0; j < map.get(i).size(); j++)
            {
                s += map.get(i).get(j);
            }
            Log.i("", s);
        }
    }

    //Save current state of map into text document method

    //This is where to reference new bitmaps
    public static int getMapBitmap()
    {
        switch(mapBitmapNum)
        {
            case 0:
                return R.drawable.map0;
            case 1:
                return R.drawable.map1;
            case 2:
                return R.drawable.map2;
            case 3:
                return R.drawable.map3;
            case 4:
                return R.drawable.map4;
            default:
                return R.drawable.map0;
        }
    }

    //Different map load options
    public static void loadSpecificMap(int mapNum/*, Context context*/)
    {
        entities.clear();
        entities.add(player);
        //ALL LOADMAPS PASSED c
        switch(mapNum)
        {
            case 0:
                loadMap0();
                break;

            case 1:
                loadMap1();
                break;

            case 2:
                loadMap2();
                break;

            case 3:
                loadMap3();
                break;

            case 4:
                loadMap4();
                break;

            default:
                loadMap0();
                break;
        }
    }

    //First map layout is in MainThread.

    private static void loadMap0()
    {
        int temp = mapBitmapNum; //Holds previous map location
        loadMapFromFile(0);

        if(temp == 1) //From parking lot
        {
            player.mapCoords.x = 10;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(temp == 2) //from office
        {
            player.mapCoords.x = 3;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(temp == 3)
        {
            player.mapCoords.x = 5;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(temp == 0)
        {
            player.mapCoords.x = 10;
            player.mapCoords.y = 10;
            player.direction = 0;

            MapManipulator.entities.add(new Cooler(1, 0, 150, 150, 0, "Mysterious Cooler"));
        }

        MapManipulator.entities.add(new Entity(5,4,150,150,0, "Michael"));
        MapManipulator.entities.add(new Door(3, 0, 200, 200, 0, "Boss's office", 2));
        MapManipulator.entities.add(new Painting(4,0,50,50,0, "trump"));
        MapManipulator.entities.add(new Door(5,0, 200, 200, 0, "Bathroom", 3));
        MapManipulator.entities.add(new Painting(7,0,100,100, 0, "clock"));
        MapManipulator.entities.add(new Door(10, 0, 200, 200, 0, "Garage", 1));
        MapManipulator.entities.add(new Trigger(10,9,0,0,0,"mydesk"));
    }

    private static void loadMap1()
    {
        if(Happenings.stage == 0)
            entities.get(0).setDialogue("Looks like he's not here...");

        Happenings.stage = 1;
        loadMapFromFile(1);
        entities.get(0).mapCoords.x = 4;
        entities.get(0).mapCoords.y = 4;
        entities.get(0).direction = 0;
        entities.add(new Door(4,5,200,250,2,"Door1", 0));


    }

    private static void loadMap2()
    {
        if(Happenings.stage == 1)
            entities.get(0).setDialogue("What the heck...");

        loadMapFromFile(2);
        entities.get(0).mapCoords.x = 2;
        entities.get(0).mapCoords.y = 4;
        entities.get(0).direction = 0;

        entities.add(new Door(2,5,200,250,2,"Door1", 0));
        entities.add(new Trigger(2,2,0,0,0,"bossnote"));
        entities.add(new Painting(3,0,200,200,0,"diploma"));
    }

    private static void loadMap3()
    {
        Happenings.stage = 3;
        loadMapFromFile(3);
        entities.get(0).mapCoords.x = 1;
        entities.get(0).mapCoords.y = 4;
        entities.get(0).direction = 0;

        entities.add(new Door(1,5,200,250,2,"door",0));
        entities.add(new Trigger(7, 2, 0, 0, 0, "portal"));
        entities.add(new Cooler(7,3,150,150,2,"bathroomCooler"));
    }

    private static void loadMap4()
    {
        if(Happenings.stage == 3)
            entities.get(0).setDialogue("We're not in Kansas anymore");

        Happenings.stage = 4;
        loadMapFromFile(4);
        entities.get(0).mapCoords.x = 5;
        entities.get(0).mapCoords.y = 3;
        entities.get(0).direction = 0;
        entities.add(new Door(1,0,200,250, 0,"door", 0));
        entities.add(new Computer(6,0,150,150,3,"mainComputer"));
    }
}

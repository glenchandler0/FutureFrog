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

    public static Entity player;

    //Entities!
    public static ArrayList<Entity> entities = new ArrayList<Entity>(); //Static so any class can add or manipulate entities

    public static ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();
    public static ArrayList<Character> noPass = new ArrayList<Character>(); //This is initiated in

    //Hard coded map dimensions, will be initiated in GamePanel constructor, because it is much more simple than
    //Most detection algorithms
    public static ArrayList<Point> dimensions = new ArrayList<Point>();

    public static int mapDimX;
    public static int mapDimY;

    public static int mapBitmapNum = 0;

    private static Context c;

    //Look into being able to access context anywhere, or passing it to constructor
    public static void loadMapFromFile(int mapNum, Context context)
    {
        c = context;
        map.clear();
        ArrayList<Character> temp = new ArrayList<>();

        AssetManager am = context.getAssets();
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
            mapDimX = dimensions.get(mapNum).x;
            mapDimY = dimensions.get(mapNum).y;
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

    public static int getMapBitmap()
    {
        switch(mapBitmapNum)
        {
            case 0:
                return R.drawable.map0;
            case 1:
                break;
            default:
                return R.drawable.map0;
        }
        return R.drawable.map0;
    }

    //Different map load options
    public static void loadSpecificMap(int mapNum/*, Context context*/)
    {
        //IN ALL OF THE LOAD MAPS METHODS, PLAYER MUST BE ADDED FIRST!!!!!
        entities.clear();
        entities.add(player);
        switch(mapNum)
        {
            case 0:
                loadMap0(c);
                break;

            case 1:
                loadMap1(c);
                break;

            default:
                loadMap0(c);
                break;
        }
    }

    private static void loadMap0(Context context)
    {
        loadMapFromFile(0,context);
        entities.get(0).mapCoords.x = 10;
        entities.get(0).mapCoords.y = 10;
        entities.add(new Entity(1,2,100,100,0,"Bob"));
        entities.add(new Door(1,1, 250, 250, 0, "Door 0", 1));
    }

    private static void loadMap1(Context context)
    {
        loadMapFromFile(0, context);
        entities.get(0).mapCoords.x = 5;
        entities.get(0).mapCoords.y = 5;
        entities.add(new Door(5,4,250,250,0,"Door1", 0));
    }
}

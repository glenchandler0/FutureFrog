package swoledcademy.com.futurefrogv21;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import java.io.*;
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
    public static Bitmap mapBitmap; //Initialized on loadMapFromFile()

    private static Context c;

    public static void setContext(Context context)
    {
        c = context;
    }

    public static void readUserDataFromFile()
    {
        AssetManager am = c.getAssets();

        try
        {
            //InputStream is = am.open("save.txt");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            //String line = reader.readLine();

            //Take 2
            FileInputStream fis = c.openFileInput("save.txt");
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = fis.read()) != -1){
                builder.append((char)ch);
            }

            String line = builder.toString();

            //These lines of code are ran everytime the save file is read to assure that everything is initiated

            MapManipulator.loadMapFromFile(0); //player can't be initialized until there's a map, read from file
            MapManipulator.entities.add(MapManipulator.player = new Player(10, 10, 150, 150, 0, "Steve")); //Simultaniously initialized MapManipulator.player and places in MapManipulator.entities.get(0)
            MapManipulator.loadSpecificMap(0); //map is loaded normally with standard streamlined method.

            if(line != null)
            {
                Log.i("Alert","Loading from file!");
                //THERE NEEDS TO BE 5 NUMBERS IN "save.txt"

                //Setting scanner to get each number in the file
                Scanner dataScanner = new Scanner(line);

                //1 - First number in line will be map number
                MapManipulator.loadSpecificMap(Integer.parseInt(dataScanner.next()));

                //2 - Second number in line is event number
                Happenings.gameState = Integer.parseInt(dataScanner.next());

                //3:5 -These get the players' location in that map
                player.mapCoords.x = Integer.parseInt(dataScanner.next());
                player.mapCoords.y = Integer.parseInt(dataScanner.next());
                player.direction = Integer.parseInt(dataScanner.next());
            }
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeUserDataToFile()
    {
        AssetManager am = c.getAssets();

        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(c.openFileOutput("save.txt", Context.MODE_PRIVATE));

            //Creating string with necessary data. Since this is a small amount of data, I'm okay doing it this way.
            //If there was more data to keep we might want to hold an ArrayList with relevent integers, and loop through it to create this string.
            String saveText = MapManipulator.mapBitmapNum + " " + Happenings.gameState + " " + player.mapCoords.x + " " + player.mapCoords.y + " " + player.direction + "\n";
            MapManipulator.entities.get(0).dialogue = saveText;

            outputStreamWriter.write(saveText);
            outputStreamWriter.close();
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
            Thread.dumpStack();
            try{Thread.sleep(5000);}catch(Exception f) {e.printStackTrace();}
        }
    }

    //Sets map to the text file, so it can be manipulated in game.
    //Called when map changes
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
            mapBitmap = BitmapFactory.decodeResource(c.getResources(), MapManipulator.getMapBitmap());
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
            //Log.i("", s);
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
        try {
            switch (mapNum) {
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
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //First map layout is in MainThread.

    private static void loadMap0()
    {
        int tempBMNum = mapBitmapNum; //Holds previous map location
        loadMapFromFile(0);

        if(tempBMNum == 1) //From parking lot
        {
            player.mapCoords.x = 10;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(tempBMNum == 2) //from office
        {
            player.mapCoords.x = 3;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(tempBMNum == 3)
        {
            player.mapCoords.x = 5;
            player.mapCoords.y = 1;
            player.direction = 2;
        }
        else if(tempBMNum == 0)
        {
            player.mapCoords.x = 10;
            player.mapCoords.y = 10;
            player.direction = 0;

            MapManipulator.entities.add(new Cooler(1, 0, 150, 150, 0, "Mysterious Cooler"));
        }

        MapManipulator.entities.add(new Entity(5,4,150,150,0, "Michael"));
        MapManipulator.entities.add(new Door(3, 0, 200, 200, 0, "Boss's office", 2));
        MapManipulator.entities.add(new Painting(4, 0, 50, 50, 0, "trump"));
        MapManipulator.entities.add(new Door(5,0, 200, 200, 0, "Bathroom", 3));
        MapManipulator.entities.add(new Painting(7,0,100,100, 0, "clock"));
        MapManipulator.entities.add(new Door(10, 0, 200, 200, 0, "Garage", 1));
        MapManipulator.entities.add(new Trigger(10,9,0,0,0,"mydesk"));
    }

    private static void loadMap1()
    {
        if(Happenings.gameState == 1) {
            entities.get(0).setDialogue("Looks like he's not here...");
            Happenings.gameState = 2;
        }

        loadMapFromFile(1);
        entities.get(0).mapCoords.x = 4;
        entities.get(0).mapCoords.y = 4;
        entities.get(0).direction = 0;
        entities.add(new Door(4,5,200,250,2,"Door1", 0));


    }

    private static void loadMap2() //throws java.lang.InterruptedException
    {
        if(Happenings.gameState == 2) {
            entities.get(0).setDialogue("What the heck...");
            //c.wait(1000); //be very careful warning
        }

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
        if(mapBitmapNum == 3)
            entities.get(0).setDialogue("We're not in Kansas anymore");

        loadMapFromFile(4);
        entities.get(0).mapCoords.x = 5;
        entities.get(0).mapCoords.y = 3;
        entities.get(0).direction = 0;
        entities.add(new Door(1,0,200,250, 0,"door", 0));
        entities.add(new Computer(6,0,150,150,3,"mainComputer"));
    }
}

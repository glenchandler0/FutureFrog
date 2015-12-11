package swoledcademy.com.futurefrogv21;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;

    //public static ArrayList<Entity> entities = new ArrayList<Entity>(); //Static so any class can add or manipulate entities
    private int canvasWidth;
    private int canvasHeight;

    private Paint paint = new Paint(); //For drawing text to screen
    private String printText = "Swaaag";

    public GamePanel(Context context, Resources resources)
    {
        super(context);
        MapManipulator.dimensions.add(new Point(11, 11)); //Index 0, for map0

        paint.setColor(Color.WHITE);
        paint.setTextSize(75);

        MapManipulator.loadMapFromFile(0, context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this, resources);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.i("GAMEPANEL", "SURFACE CHANGED");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.i("GAMEPANEL", "SURFACE DESTROYED");
        //Removing this for now because game crashes when i restart it
        //I will make a save method so that you can either have the game continue running, or stop it completely
        //Also a pause that idles all methods in the loop

        boolean retry = true;
        while(retry)
        {
            try
            {
                thread.setRunning(false);
                thread.join();

            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.i("GAMEPANEL", "SURFACE CREATED");

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            for(int i = 0; i < MapManipulator.entities.size(); i++)
            {
                MapManipulator.entities.get(i).setDialogue("");
            }
            //printText = "";
            if((event.getRawX() > canvasWidth / 2 - MapManipulator.entities.get(0).pixelWidth) && (event.getRawX() < canvasWidth / 2 + MapManipulator.entities.get(0).pixelWidth))
            {
                //Up
                if(event.getRawY() < canvasHeight / 2 - MapManipulator.entities.get(0).pixelHeight)
                {
                    MapManipulator.entities.get(0).move(0);
                }
                //Down
                else if(event.getRawY() > canvasHeight / 2 + MapManipulator.entities.get(0).pixelHeight )
                {
                    MapManipulator.entities.get(0).move(2);
                }
            }
            else if((event.getRawY() > canvasHeight / 2 - MapManipulator.entities.get(0).pixelHeight) && (event.getRawY() < canvasHeight / 2 + MapManipulator.entities.get(0).pixelHeight))
            {
                //Left
                if(event.getRawX() < canvasWidth / 2 - MapManipulator.entities.get(0).pixelWidth)
                {
                    MapManipulator.entities.get(0).move(3);
                }
                //Right
                else if(event.getRawX() > canvasWidth / 2 + MapManipulator.entities.get(0).pixelWidth)
                {
                    MapManipulator.entities.get(0).move(1);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void update()
    {

    }

    //These are A-Okay
    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawColor(Color.BLACK);

        int blockPixelSize = 50;
        Rect tempBitmapRect = null;
        Rect phoneSizeRect = new Rect(0,0, canvas.getWidth(), canvas.getHeight());

        //Loading player and map bitmap
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), MapManipulator.entities.get(0).getBitmap());
        Bitmap mapBitmap = BitmapFactory.decodeResource(getResources(), MapManipulator.getMapBitmap());

        //Getting mapImg size for finding conversion ratio
        int imgX = mapBitmap.getWidth();
        int imgY = mapBitmap.getHeight();
        //Log.i("ImgXY_", String.format("X:%d, Y: %d", imgX, imgY));
        //Log.i("MapXY_", String.format("X:%d, Y: %d", MapManipulator.mapDimX, MapManipulator.mapDimY));

        //Ratio between size of map and size of map image
        double ratioX = (double)(MapManipulator.mapDimX * blockPixelSize) / (double)imgX;
        double ratioY = (double)(MapManipulator.mapDimY * blockPixelSize) / (double)imgY;
        //Log.i("Screen-MapRatio: ", String.format("RX: %.5f, RY: %.5f", ratioX, ratioY));

        //Finds the estimated player position on the map image using the ratio
        int playerImageX = (int)((MapManipulator.entities.get(0).mapCoords.x * blockPixelSize) / ratioX);
        int playerImageY = (int)((MapManipulator.entities.get(0).mapCoords.y * blockPixelSize) / ratioY);

        //Finds where the corners of the screen should be around the player
        int phoneULeftX = playerImageX - (canvas.getWidth() / 2);
        int phoneULeftY = playerImageY - (canvas.getHeight() / 2);
        int phoneBRightX = playerImageX + (canvas.getWidth() / 2);
        int phoneBRightY = playerImageY + (canvas.getHeight() / 2);


        //Drawing the map
        tempBitmapRect = new Rect(phoneULeftX, phoneULeftY, phoneBRightX, phoneBRightY);

        canvas.drawBitmap(mapBitmap, tempBitmapRect, phoneSizeRect, null);

        //Drawing all other entities
        Bitmap entityBitmap;
        int entityX = 0;
        int entityY = 0;

        ArrayList<Integer> ordered = orderEntities();
        int j = 0;
        //Starts at 1 because player is entity[0]
        for(int i = 0; i < ordered.size(); i++)
        {
            j = ordered.get(i);
            Log.i("J IN THE LOOP: ", "" +j);
            if(j == 0)
            {
                tempBitmapRect = new Rect(
                        (canvas.getWidth() / 2) - (MapManipulator.entities.get(0).pixelWidth / 2),
                        (canvas.getHeight() / 2) - (MapManipulator.entities.get(0).pixelHeight / 2),
                        (canvas.getWidth() / 2) + (MapManipulator.entities.get(0).pixelWidth / 2),
                        (canvas.getHeight() / 2) + (MapManipulator.entities.get(0).pixelHeight / 2)
                );

                canvas.drawBitmap(playerBitmap, null, tempBitmapRect, null);
                continue;
            }
            entityBitmap = BitmapFactory.decodeResource(getResources(), MapManipulator.entities.get(j).getBitmap());

            //Takes the distance of where the screen is on the map and where the entity is, to easily draw on screen
            entityX = (int)((MapManipulator.entities.get(j).mapCoords.x * blockPixelSize) / ratioX) - phoneULeftX;
            entityY = (int)((MapManipulator.entities.get(j).mapCoords.y * blockPixelSize) / ratioX) - phoneULeftY;
            Log.i("EntityImgXY_", String.format("X:%d, Y: %d", entityX, entityY));

            tempBitmapRect = new Rect(
                    (entityX - (MapManipulator.entities.get(j).pixelWidth / 2)),
                    (entityY - (MapManipulator.entities.get(j).pixelHeight / 2)),
                    (entityX + (MapManipulator.entities.get(j).pixelWidth / 2)),
                    (entityY + (MapManipulator.entities.get(j).pixelHeight / 2))
            );
            canvas.drawBitmap(entityBitmap, null, tempBitmapRect, null);
        }

        //Drawing player, keep in mind enetities[0] is always player

        Log.i("PLAYERIMGXY", String.format("X:%d, Y:%d", playerImageX, playerImageY));
        MapManipulator.printMap();

        for(int i = 0; i < MapManipulator.entities.size(); i++)
        {
            printText = MapManipulator.entities.get(i).getDialogue();
            if(MapManipulator.entities.get(i).dialogue != "")
            {
                break;
            }
        }

        canvas.drawText(printText, 0, canvasHeight / 2, paint);
    }

    private ArrayList<Integer> orderEntities()
    {
        ArrayList<Integer> organized = new ArrayList<Integer>();
        boolean event = false;
        int min = 1000;
        int prevNum = -1;
        int index = -1;

        int counter = 0;
        while(counter < MapManipulator.entities.size())
        {
            min = 1000;
            for(int i = 0; i < MapManipulator.entities.size(); i++)
            {
                if(!(organized.contains(i)))
                {
                    if (MapManipulator.entities.get(i).mapCoords.y <= min && MapManipulator.entities.get(i).mapCoords.y >= prevNum)
                    {
                        min = MapManipulator.entities.get(i).mapCoords.y;
                        index = i;
                        event = true;
                    }
                }
            }
            if(event == false)
            {
                return organized;
            }
            organized.add(index);
            prevNum = min;
            event = false;
            Log.i("", "" + index);
            counter++;
        }
        return organized;
    }
}
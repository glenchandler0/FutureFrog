package swoledcademy.com.futurefrogv21;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;



public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{

    //This thread will handle the game running.
    private MainThread thread;

    //public static ArrayList<Entity> entities = new ArrayList<Entity>(); //Static so any class can add or manipulate entities
    private int canvasWidth;
    private int canvasHeight;

    private Paint paint = new Paint(); //For drawing text to screen
    private Paint paintShadow = new Paint();
    private Paint thoughtPaint = new Paint();
    private String printText = ""; //Will be changed statically to edit text

    public GamePanel(Context context, Resources resources)
    {
        super(context);

        //Setting Paints for drawing text
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        paintShadow.setColor(Color.BLACK);
        paintShadow.setTextSize(100);
        thoughtPaint.setColor(Color.WHITE);
        thoughtPaint.setTextSize(75);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/pokemon.ttf");
        paint.setTypeface(face);
        paintShadow.setTypeface(face);
        thoughtPaint.setTypeface(face);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //Sets MapManipular.c private variable through this method so it only needs to be done once
        MapManipulator.setContext(context);

        //Initialized to draw to screen
        thread = new MainThread(getHolder(), this, resources);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.i("GAMEPANEL", "SURFACE CHANGED");
        //NEW EDIT

    }

    //This is where the crash issue is, work on save state? so when game stops, just reload from saved data?
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //PRETTY MUCH GOING TO DELETE THIS BECAUSE NOT NEEDED, MAYBE SETRUNNING(FALSE)
        Log.i("GAMEPANEL", "SURFACE DESTROYED");
        //Removing this for now because game crashes when i restart it
        //I will make a save method so that you can either have the game continue running, or stop it completely
        //Also a pause that idles all methods in the loop

        boolean retry = true;
        while(retry)
        {
            try
            {
                //Removing these lines of code and checking if the thread is already running seems to fix the problem,
                //however I worry about memory leaks because the thread is never joined.

                //thread.setRunning(false);
                //thread.join();
                Log.d("TEST SURFACE DESTROY","TEST SURFACE DESTROY");
            }
            catch(Exception/*InterruptedException*/ e)
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
        if(thread.getRunning() == false) {
            thread.setRunning(true);
            thread.start();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //If there was a touch to the screen.
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            //Tapping the screen will set sprite to center of tile before offsetting in move() function
            MapManipulator.entities.get(0).xOffset = 0.5;
            MapManipulator.entities.get(0).yOffset = 0.5;

            //Any time the screen is touched, all entity's dialogues are erased so they can be written in new locations later
            for(int i = 0; i < MapManipulator.entities.size(); i++)
            {
                MapManipulator.entities.get(i).setDialogue("");
            }

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
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //(1.0) = 96 x 96, (2.0/3.0) = 64 x 64, (1.0 / 2.0) = 48 x 48, (1.0 / 3.0) = 32 x 32

        double coef = 1.0; //Default set

        if(MapManipulator.mapBitmapNum == 0 || MapManipulator.mapBitmapNum == 4)
            coef = (1.0/2.0);


        canvasWidth = (canvas.getWidth());
        canvasHeight = (canvas.getHeight());

        canvas.drawColor(Color.BLACK);

        int blockPixelSize = 1000; //This probably does nothing
        Rect tempBitmapRect;
        Rect phoneSizeRect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Loading player and map bitmap
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), MapManipulator.entities.get(0).getBitmap());

        Log.d("TEXT MAP DIMENSIONS: ", MapManipulator.mapDimX + ", " + MapManipulator.mapDimY);
        //Getting mapImg size for finding conversion ratio
        int imgX = MapManipulator.mapBitmap.getWidth();
        int imgY = MapManipulator.mapBitmap.getHeight();
        //Log.i("ImgXY_", String.format("X:%d, Y: %d", imgX, imgY));
        //Log.i("MapXY_", String.format("X:%d, Y: %d", MapManipulator.mapDimX, MapManipulator.mapDimY));

        //Ratio between size of map and size of map image
        double ratioX = (double) (MapManipulator.mapDimX * blockPixelSize) / (double) imgX;
        double ratioY = (double) (MapManipulator.mapDimY * blockPixelSize) / (double) imgY;
        //Log.i("Screen-MapRatio: ", String.format("RX: %.5f, RY: %.5f", ratioX, ratioY));

        //Finds the estimated player position on the map image using the ratio
        int playerImageX = (int) (((MapManipulator.entities.get(0).mapCoords.x + MapManipulator.entities.get(0).xOffset) * blockPixelSize) / ratioX); //-----
        int playerImageY = (int) (((MapManipulator.entities.get(0).mapCoords.y + MapManipulator.entities.get(0).yOffset) * blockPixelSize) / ratioY);  //------

        //Finds where the corners of the screen should be around the player
        int phoneULeftX = (int)(playerImageX - (canvas.getWidth() / 2) * coef);
        int phoneULeftY = (int)(playerImageY - (canvas.getHeight() / 2) * coef);
        int phoneBRightX = (int)(playerImageX + (canvas.getWidth() / 2) * coef);
        int phoneBRightY = (int)(playerImageY + (canvas.getHeight() / 2) * coef);
        Log.e("PHONE BOX", String.format("PhoneULeftX: %d\nPhoneULeftY: %d\nPhoneBRightX: %d\nPhoneBRightY: %d", phoneULeftX, phoneULeftY, phoneBRightX, phoneBRightY));


        //Drawing the map
        tempBitmapRect = new Rect(phoneULeftX, phoneULeftY, phoneBRightX, phoneBRightY);
        canvas.drawBitmap(MapManipulator.mapBitmap, tempBitmapRect, phoneSizeRect, null);

        //Drawing all other entities
        Bitmap entityBitmap;
        int entityX;
        int entityY;

        ArrayList<Integer> ordered = orderEntities();
        int j;
        //Starts at 1 because player is entity[0]
        for (int i = 0; i < ordered.size(); i++) {
            j = ordered.get(i);
            Log.i("J IN THE LOOP: ", "" + j);
            if (j == 0) {
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
            entityX = (int) (((MapManipulator.entities.get(j).mapCoords.x + MapManipulator.entities.get(j).xOffset) * blockPixelSize) / ratioX - phoneULeftX); //-----
            entityY = (int) (((MapManipulator.entities.get(j).mapCoords.y + MapManipulator.entities.get(j).yOffset)* blockPixelSize) / ratioX - phoneULeftY); //------
            Log.i("EntityImgXY_", String.format("X:%d, Y: %d", entityX, entityY));

            tempBitmapRect = new Rect(
                    ((int)( entityX / coef) - (MapManipulator.entities.get(j).pixelWidth / 2)),
                    ((int)( entityY / coef) - (MapManipulator.entities.get(j).pixelHeight / 2)),
                    ((int)( entityX / coef) + (MapManipulator.entities.get(j).pixelWidth / 2)),
                    ((int)( entityY / coef) + (MapManipulator.entities.get(j).pixelHeight / 2))
            );
            canvas.drawBitmap(entityBitmap, null, tempBitmapRect, null);
        }

        Log.i("PLAYERIMGXY", String.format("X:%d, Y:%d", playerImageX, playerImageY));
        MapManipulator.printMap();

        boolean dialogueFound = false;
        for (int i = 0; i < MapManipulator.entities.size(); i++) {
            printText = MapManipulator.entities.get(i).getDialogue();
            if (!(printText.equals(""))) {
                dialogueFound = true;
                break;
            }
        }
        //Loop is picking up text from entity, but printText is not staying initialized to it.
        if(dialogueFound )
        {
            //MultiLine Text
            if (printText.contains("\n")) {
                if (printText.contains(":")) {
                    String[] lines = printText.split("\n");
                    for (int i = 0; i < lines.length; i++) {
                        canvas.drawText(lines[i], 15, canvasHeight - (canvasHeight / 3) + ((canvasHeight / 15) * i) + 5, paintShadow);
                        canvas.drawText(lines[i], 10, canvasHeight - (canvasHeight / 3) + ((canvasHeight / 15) * i), paint);
                    }
                } else {
                    String[] lines = printText.split("\n");
                    for (int i = 0; i < lines.length; i++) {
                        canvas.drawText(lines[i], 10, canvasHeight - (canvasHeight / 3) + ((canvasHeight / 15) * i), thoughtPaint);
                    }
                }
            }
            //Single Line text
            else {
                if (printText.contains(":")) {
                    canvas.drawText(printText, 15, canvasHeight - (canvasHeight / 3) + 5, paintShadow);
                    canvas.drawText(printText, 10, canvasHeight - (canvasHeight / 3), paint);
                } else {
                    canvas.drawText(printText, 10, canvasHeight - (canvasHeight / 3), thoughtPaint);
                }
            }
        }
    }

    //Look at optimizing this sort algorithm?
    private ArrayList<Integer> orderEntities()
    {
        ArrayList<Integer> organized = new ArrayList<>();
        boolean event = false;
        int min;
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
            if(!event)
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
package swoledcademy.com.futurefrogv21;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

public class MainThread extends Thread
{
    //FrameRate variables
    private int FPS = 30;
    private double averageFPS;

    private boolean running;

    //For game
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    public static Canvas canvas;
    public Resources resources;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel, Resources resources)
    {
        super();

        MapManipulator.loadMapFromFile(0); //player can't be initialized until there's a map, read from file
        MapManipulator.entities.add(MapManipulator.player = new Player(10, 10, 150, 150, 0, "Steve")); //Simultaniously initialized MapManipulator.player and places in MapManipulator.entities.get(0)
        MapManipulator.loadSpecificMap(0); //map is loaded normally with standard streamlined method.

        //Title dialogue
        MapManipulator.entities.get(0).dialogue = "                 FUTURE FROG\n            Tap near your character\n            in a direction to move.";

        //Sets the characters that can't be walked through
        MapManipulator.noPass.add('+');
        MapManipulator.noPass.add('@');
        MapManipulator.noPass.add(null);

        //Passes in necessary initialized classes so that the thread can use them.
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        this.resources  = resources;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    //Where the heavy lifting happens, the rest is reference code
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }
            catch (Exception e)
            {
            }
            finally
            {
                if(canvas!=null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }




            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try
            {
                //THIS WILL MAKE THE GAME RUN SMOOTHLY, it will optimize and wait the correct amount of frames to reach the correct
                ///frame rate
                this.sleep(waitTime);
            }
            catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                Log.i("Run Info:", averageFPS + " FPS");
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}

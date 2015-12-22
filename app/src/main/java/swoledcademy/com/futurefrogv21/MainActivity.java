package swoledcademy.com.futurefrogv21;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/* NOTES
   basically what you are going for is having gamepanel go through all the entity's
   dialgoue variables, and if it isnt set to [""] then print it, everytime the screen
   is tapped however, all entities will have their dialogue vairable set to [""]. Only events
   will set it to other things. It appears that there is a problem with setting the dialogue variable
   from other events and other files, because setting it dynamically inside of GamePanel (where setText()) is
   located works fine
 */
public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Creates new instance of GamePanel, passing in the resources from MainActivity. Game Panel will handle the game
        setContentView(new GamePanel(this, getResources()));
    }

    //OnCreateOptionsMenu--unused
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Unused for now
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

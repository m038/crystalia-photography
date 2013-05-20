package nl.gorinskat.crystalia;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.app.Activity;
import android.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: mischa
 * Date: 5/12/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIMenuManager {

    private Activity androidActivity;

    public UIMenuManager(Activity androidActivityParameter) {
        this.androidActivity = androidActivityParameter;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        androidActivity.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                Intent parentActivityIntent = new Intent(androidActivity,
                            MainActivity.class);
                NavUtils.navigateUpTo(androidActivity, parentActivityIntent);
                return true;
            case (R.id.action_home):
                intent = new Intent(androidActivity, MainActivity.class);
                androidActivity.startActivity(intent);
                return true;
            case (R.id.action_about):
                intent = new Intent(androidActivity, AboutActivity.class);
                androidActivity.startActivity(intent);
                return true;
            case (R.id.action_settings):
                intent = new Intent(androidActivity, SettingsActivity.class);
                androidActivity.startActivityForResult(intent, 1);
                return true;

//	        case R.id.help:
//	            showHelp();
//	            return true;
//		    case android.R.id.home:
//				// This ID represents the Home or Up button. In the case of this
//				// activity, the Up button is shown. Use NavUtils to allow users
//				// to navigate up one level in the application structure. For
//				// more details, see the Navigation pattern on Android Design:
//				//
//				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//				//
//				NavUtils.navigateUpFromSameTask(this);
//				return true;
//			}
        }
        return false;
    }

    public void setupActionBar(boolean homeAsUp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = androidActivity.getActionBar();
            actionBar.setTitle(R.string.app_name);

            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
        }
    }
}

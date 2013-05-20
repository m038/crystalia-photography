package nl.gorinskat.crystalia;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;

public class CrystaliaActivity extends Activity {

	// App-wide variables
    public final static String APP_NAMESPACE    = "nl.gorinskat.crystalia";
	public final static String EDITOR_TYPE 		= APP_NAMESPACE +
            ".EDITOR_TYPE";
	public final static Integer EDITOR_DYNAMIC 	= 1;
	public final static Integer EDITOR_SINGLE 	= 2;
    public final static String PREFERENCE_CALLEE = APP_NAMESPACE +
            ".PREFERENCE_CALLEE";
    private UIMenuManager menuManager = new UIMenuManager(this);
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        menuManager.onCreateOptionsMenu(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        menuManager.onOptionsItemSelected(item);
        return true;
        //TODO: Check if this works
        //return super.onOptionsItemSelected(item);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void setupActionBar(boolean homeAsUp) {
		menuManager.setupActionBar(homeAsUp);
	}
	
}

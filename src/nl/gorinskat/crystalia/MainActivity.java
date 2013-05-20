package nl.gorinskat.crystalia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends CrystaliaActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		super.setupActionBar(false);
	}
		
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		// Hide home button from start screen
		menu.removeItem(R.id.action_home);
		return true;
	}

	public void buttonAction(View view) {
		Intent intent;
		switch(view.getId()) {
		case (R.id.button_dynamic):
			intent = new Intent(this, WallpaperEditorActivity.class);
			intent.putExtra(EDITOR_TYPE, EDITOR_DYNAMIC);
			startActivity(intent);
			break;
		case (R.id.button_single):
			intent = new Intent(this, WallpaperEditorActivity.class);
			intent.putExtra(EDITOR_TYPE, EDITOR_SINGLE);
			startActivity(intent);
			break;
		case (R.id.button_gallery):
			// Do something in response to button
			intent = new Intent(this, GalleryActivity.class);
			startActivity(intent);
			break;
		case (R.id.button_settings):
			intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1);
			break;
		}
	}
}

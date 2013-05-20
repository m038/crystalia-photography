package nl.gorinskat.crystalia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WallpaperEditorActivity extends CrystaliaActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallpaper_editor);
		
		Intent intent = getIntent();
		Integer typeOfEditor = intent.getIntExtra(EDITOR_TYPE, EDITOR_SINGLE);

        TextView textView = (TextView) findViewById(R.id.textfield);
        switch(typeOfEditor) {
            case 1:
                textView.setText("Multi image wallpaper");
                break;
            case 2:
                textView.setText("Single image wallpaper");
                break;
        }

	    super.setupActionBar(true);
	}

    public void buttonAction(View view) {
        Intent intent;
        switch(view.getId()) {
            case (R.id.button_settings):
                intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 1);
                break;
            case (R.id.button_set_wallpaper):
                Log.d(APP_NAMESPACE, "Button: button_set_wallpaper");
                break;
        }
    }

}

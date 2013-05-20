package nl.gorinskat.crystalia;

import android.os.Bundle;

public class GalleryActivity extends CrystaliaActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		super.setupActionBar(true);
	}

}

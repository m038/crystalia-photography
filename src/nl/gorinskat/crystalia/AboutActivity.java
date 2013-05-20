package nl.gorinskat.crystalia;

import android.os.Bundle;

public class AboutActivity extends CrystaliaActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		super.setupActionBar(true);
	}
	
}

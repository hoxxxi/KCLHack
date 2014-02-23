package com.example.kclhack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class DetailedData extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_data);
		Intent i = getIntent();
		TextView homeTeam =(TextView) findViewById(R.id.detailedHomeTV);
		TextView guestTeam =(TextView) findViewById(R.id.detailedGuestTV);
		homeTeam.setText(i.getStringExtra("TeamOne"));
		guestTeam.setText(i.getStringExtra("TeamTwo"));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_data, menu);
		return true;
	}

}

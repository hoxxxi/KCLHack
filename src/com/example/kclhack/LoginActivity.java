package com.example.kclhack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity {

	protected Typeface aubrey;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//change
		aubrey = Typeface.createFromAsset(getAssets(), "font/aubrey.TTF");
		TextView labelUser = (TextView) findViewById(R.id.textView3);
		TextView labelPassword = (TextView) findViewById(R.id.textView2);
		TextView labelSignup = (TextView) findViewById(R.id.textView1);
		labelUser.setTypeface(aubrey);
		labelPassword.setTypeface(aubrey);
		labelSignup.setTypeface(aubrey);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	public void watchAsGuest(View view)
	{
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
	}

}

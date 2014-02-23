package com.example.kclhack;


import fragments.FutureFragment;
import fragments.LiveFragment;
import fragments.PastFragment;


<<<<<<< HEAD
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.FragmentActivity;

=======
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
>>>>>>> 535bdffb77e49b78f1102656cc93b3296b9c717f

import android.view.View;


<<<<<<< HEAD

public class MainActivity extends FragmentActivity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LiveFragment myList = new LiveFragment();
		FragmentTransaction transaction = 	getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.fragment, myList);
		transaction.commit();
	}
	
	public void onClick(View v){
		Fragment changeFragment = new LiveFragment();
		switch(v.getId()){
		case R.id.liveBtn:
			changeFragment = new LiveFragment();
			break;
		case R.id.futureBtn:
			changeFragment = new FutureFragment();
			break;
		case R.id.pastBtn:
			changeFragment = new PastFragment();
			break;
			
=======
@SuppressLint("CutPasteId")
public class MainActivity extends Activity {
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;
	private Typeface aubrey;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		aubrey = Typeface.createFromAsset(getAssets(), "fonts/aubrey.ttf");
		TextView labelUser = (TextView) findViewById(R.id.textView3);
		TextView labelPassword = (TextView) findViewById(R.id.textView2);
		TextView labelSignup = (TextView) findViewById(R.id.textView1);
		labelUser.setTypeface(aubrey);
		labelPassword.setTypeface(aubrey);
		labelSignup.setTypeface(aubrey);


		
		gameArray = new ArrayList<Game>();
		try {
			populate();
		} catch (ExecutionException e) {
			e.printStackTrace();
>>>>>>> 535bdffb77e49b78f1102656cc93b3296b9c717f
		}
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragment, changeFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		
	}
	
	
	
}

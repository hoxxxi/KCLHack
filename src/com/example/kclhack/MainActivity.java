package com.example.kclhack;


import fragments.FutureFragment;
import fragments.LiveFragment;
import fragments.PastFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.FragmentActivity;


import android.view.View;



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
			
		}
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragment, changeFragment);
		transaction.addToBackStack(null);
		transaction.commit();
		
		
	}
	
	
	
}

package com.example.kclhack;

import java.util.ArrayList;

import data_types.Football_List_Adapter;
import data_types.Game;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
private ArrayList<Game> gameArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameArray = new ArrayList<Game>();
		ListView gamesList = (ListView) findViewById(R.id.gamesList);
		populate();
		Football_List_Adapter adapter = new Football_List_Adapter(MainActivity.this, gameArray);
		gamesList.setAdapter(adapter);
		
	}

	private void populate() {
		gameArray.add(new Game("Petrolu", "Rapid", "10-0", "Petrolistii dau muie la rapid", 20));
		gameArray.add(new Game("Chelsea", "Barcelona", "0-2", "Red card for Chelsea", 30));
		gameArray.add(new Game("Juventus", "Real Madrid", "3-1", "Juventus fans say that real madrid are wankers", 44));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

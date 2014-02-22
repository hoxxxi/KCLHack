package com.example.kclhack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import data_types.Football_List_Adapter;
import data_types.Game;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
private ArrayList<Game> gameArray;
private final static UUID PEBBLE_APP_UUID = UUID.fromString("03dce863-84b3-4d20-b237-911680bca82b");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameArray = new ArrayList<Game>();
		ListView gamesList = (ListView) findViewById(R.id.gamesList);
		populate();
		Football_List_Adapter adapter = new Football_List_Adapter(MainActivity.this, gameArray);
		gamesList.setAdapter(adapter);
		
		//check if the the pebble is connected
		boolean connected = PebbleKit.isWatchConnected(getApplicationContext());
		Log.i(getLocalClassName(), "Pebble is " + (connected ? "connected" : "not connected"));
		sendAlertToPebble();
		  
		  // Closing my app
		 // PebbleKit.closeAppOnPebble(getApplicationContext(), PEBBLE_APP_UUID);
		
	}
	
	public void sendAlertToPebble() {
	    final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");
	  
	   

	    final Map data = new HashMap();
	    data.put("title", "This is a test");
	    data.put("body", "This is a medium body. Not too long, not too short");
	    final JSONObject jsonData = new JSONObject(data);
	    final String notificationData = new JSONArray().put(jsonData).toString();
	    
	    i.putExtra("messageType", "PEBBLE_ALERT");
	    i.putExtra("sender", "MyAndroidApp");
	    i.putExtra("notificationData", notificationData);

	    Log.d("send", "About to send a modal alert to Pebble: " + notificationData);
	    sendBroadcast(i);
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

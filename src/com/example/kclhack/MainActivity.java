package com.example.kclhack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.getpebble.android.kit.PebbleKit;

import data_types.Football_List_Adapter;
import data_types.Game;

public class MainActivity extends Activity {
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gameArray = new ArrayList<Game>();
		try {
			populate();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		ListView gamesList = (ListView) findViewById(R.id.gamesList);
		
		adapter = new Football_List_Adapter(MainActivity.this, gameArray);
		gamesList.setAdapter(adapter);
		

		// check if the the pebble is connected
		boolean connected = PebbleKit.isWatchConnected(getApplicationContext());
		Log.i(getLocalClassName(), "Pebble is "
				+ (connected ? "connected" : "not connected"));
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
		final String notificationData = new JSONArray().put(jsonData)
				.toString();

		i.putExtra("messageType", "PEBBLE_ALERT");
		i.putExtra("sender", "MyAndroidApp");
		i.putExtra("notificationData", notificationData);

		Log.d("send", "About to send a modal alert to Pebble: "
				+ notificationData);
		sendBroadcast(i);
	}

	private void populate() throws ExecutionException {
		try{
			gameArray=new Thread().execute().get();
		}
		catch (InterruptedException e){
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	protected class Thread extends AsyncTask<Void, Void, ArrayList<Game>> {

		@Override
		protected ArrayList<Game> doInBackground(Void... params) {
			return Game.getPastGames();
			}
	}
}

package fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.kclhack.DetailedData;
import com.example.kclhack.R;
import com.getpebble.android.kit.PebbleKit;

import data_types.Football_List_Adapter;
import data_types.Game;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PastFragment extends Fragment implements OnItemClickListener {
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		return inflater.inflate(R.layout.past_fragment, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		gameArray = new ArrayList<Game>();
		try {
			populate();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		ListView gamesList = (ListView) getView().findViewById(R.id.pastGamesList);
		gamesList.setOnItemClickListener(this);
		
		adapter = new Football_List_Adapter(getView().getContext(), gameArray);
		gamesList.setAdapter(adapter);
		

		// check if the pebble is connected
		boolean connected = PebbleKit.isWatchConnected(getView().getContext());
		Log.i("pastfragment", "Pebble is "
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
		getActivity().sendBroadcast(i);
	}

	private void populate() throws ExecutionException {
		try{
			gameArray=new Thread().execute().get();
		}
		catch (InterruptedException e){
			
		}
	}


	protected class Thread extends AsyncTask<Void, Void, ArrayList<Game>> {

		@Override
		protected ArrayList<Game> doInBackground(Void... params) {
			return Game.getPastGames();
			}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent openDetails = new Intent(getActivity(), DetailedData.class);
		Game selectedGame = gameArray.get(arg2);
		openDetails.putExtra("TeamOne", selectedGame.getTeam_One());
		openDetails.putExtra("TeamTwo", selectedGame.getTeam_Two());
		openDetails.putExtra("Details", selectedGame.getDetails());
		startActivity(openDetails);
	}

}

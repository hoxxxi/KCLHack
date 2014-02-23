package fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.kclhack.R;

import data_types.Football_List_Adapter;
import data_types.Game;
import fragments.PastFragment.Thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LiveFragment extends Fragment {
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.live_fragment, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// THE ACTUAL CODE SHOULD BE HERE
		super.onActivityCreated(savedInstanceState);
		gameArray = new ArrayList<Game>();
		// YOU NEED TO POPULATE THE ARRAY

		try {
			gameArray = new Thread().execute().get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (InterruptedException e) {

		}

		ListView gamesList = (ListView) getView().findViewById(
				R.id.liveGamesList);

		adapter = new Football_List_Adapter(getView().getContext(), gameArray);
		gamesList.setAdapter(adapter);

	}

	protected class Thread extends AsyncTask<Void, Void, ArrayList<Game>> {

		@Override
		protected ArrayList<Game> doInBackground(Void... params) {
			return Game.getLiveGames();
		}
	}

}

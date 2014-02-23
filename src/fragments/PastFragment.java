package fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kclhack.R;
import com.getpebble.android.kit.PebbleKit;
import com.novoda.imageloader.core.util.DirectLoader;

import data_types.Football_List_Adapter;
import data_types.Game;

public class PastFragment extends Fragment implements OnItemClickListener {
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;
	public PastFragment fragment=this;
	public ListView gamesList;
	public static ImageView hostTeam;
	public static ImageView guestTeam;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		return inflater.inflate(R.layout.past_fragment, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gameArray = new ArrayList<Game>();
		try {
			populate();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		gamesList = (ListView) getView().findViewById(R.id.pastGamesList);
		gamesList.setOnItemClickListener(this);
		
		adapter = new Football_List_Adapter(getView().getContext(), gameArray);
		gamesList.setAdapter(adapter);
		

		// check if the pebble is connected
		boolean connected = PebbleKit.isWatchConnected(getView().getContext());
		Log.i("pastfragment", "Pebble is "
				+ (connected ? "connected" : "not connected"));
		//sendAlertToPebble();

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
		/**Intent openDetails = new Intent(getActivity(), DetailedData.class);
		Game selectedGame = gameArray.get(arg2);
		openDetails.putExtra("TeamOne", selectedGame.getTeam_One());
		openDetails.putExtra("TeamTwo", selectedGame.getTeam_Two());
		startActivity(openDetails);*/
		Game game=(Game) gamesList.getItemAtPosition(arg2);
		
		SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance("Details",game);
        dialogFragment.show(fragment.getFragmentManager(), "Mak");
		
	}
	public static class SimpleDialogFragment extends DialogFragment {
        String title;
        static Game game;

        /**
         * This method creates a new dialogue fragment from the title and
         * content with a simple dialog box
         *
         * @param String
         *            title int in value resources, containing title String
         * @param String
         *            content the int of the layout xml file
         * @return a dialog fragment with title content and an ok button;
         */
        public static SimpleDialogFragment newInstance(String title, Game input) {
                SimpleDialogFragment newDialogFragment = new SimpleDialogFragment();
                game = input;
                Bundle args = new Bundle();
                args.putString("title", title);
                newDialogFragment.setArguments(args);
                return newDialogFragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
                title = getArguments().getString("title");
                return createDialog(title);
        }

        /**
         * This method creates a new dialogue from the title and content (from
         * resources) of the dialogue
         *
         * @param String
         *            title int in value resources, containing title String
         * @param String
         *            content the int of the layout xml file
         */
        public Dialog createDialog(String title) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                                getActivity());
                dialogBuilder.setTitle(title);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.fragment_dialog, null);
                
                TextView teamOneName = (TextView) view.findViewById(R.id.item_TeamOne_TV);
                TextView teamTwoName = (TextView) view.findViewById(R.id.item_TeamTwo_TV);
                TextView score = (TextView) view.findViewById(R.id.item_Score_TV);
                TextView time = (TextView) view.findViewById(R.id.item_time_TV);

                hostTeam = (ImageView) view.findViewById(R.id.host_team);
                guestTeam = (ImageView) view.findViewById(R.id.guest_team);
                
                teamOneName.setText(game.getTeam_One());
                teamTwoName.setText(game.getTeam_Two());
                score.setText(game.getScore());
                time.setText(game.getTime());
                
                String strHome = "http://internal.wolfmax.co.uk/football/logo/a" + game.getHomeId()+".png";
                String strAway = "http://internal.wolfmax.co.uk/football/logo/a" + game.getAwayId()+".png";

                
                //Image Loading
               new ImageLoaderTask().execute(strHome,strAway,teamOneName,teamTwoName);
                
                dialogBuilder.setView(view);
                dialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                                // if this button is clicked, just close
                                                // the dialog box and do nothing
                                                dialog.dismiss();
                                        }
                                });
                dialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                                // TODO Refresh PEBBLE
                                        }
                                });
                return dialogBuilder.create();
        }
        
        //Image Loading
        protected class ImageLoaderTask extends AsyncTask<Object, Void, Bitmap>{
    	
    		Bitmap bb;
    	@Override
    	protected Bitmap doInBackground(Object... params) {

    		
    		bb = new DirectLoader().download((String) params[1]);
    		
    		Bitmap b = new DirectLoader().download((String) params[0]);
    		return b;
    	}

    	@Override
    	protected void onPostExecute(Bitmap result) {
    		
    		
    		guestTeam.setImageBitmap(bb);
    		hostTeam.setImageBitmap(result);
    	}
    		
   	}
}

}

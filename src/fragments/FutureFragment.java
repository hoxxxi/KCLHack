package fragments;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.kclhack.R;

import data_types.Football_List_Adapter;
import data_types.Game;
import fragments.LiveFragment.SimpleDialogFragment;
import fragments.PastFragment.Thread;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FutureFragment extends Fragment implements OnItemClickListener{
	private ArrayList<Game> gameArray;
	private Football_List_Adapter adapter;
	ListView gamesList;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.future_fragment, container, false);
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
		gamesList = (ListView) getView().findViewById(
				R.id.futureGamesList);

		adapter = new Football_List_Adapter(getView().getContext(), gameArray);
		gamesList.setAdapter(adapter);
		
		gamesList.setOnItemClickListener(this);
	}

	protected class Thread extends AsyncTask<Void, Void, ArrayList<Game>> {

		@Override
		protected ArrayList<Game> doInBackground(Void... params) {
			return Game.getUpcomingGames();
		}
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

                ImageView hostTeam = (ImageView) view.findViewById(R.id.host_team);
                ImageView guestTeam = (ImageView) view.findViewById(R.id.guest_team);
                
                teamOneName.setText(game.getTeam_One());
                teamTwoName.setText(game.getTeam_Two());
                score.setText(game.getScore());
                time.setText(game.getTime());
                
                String strHome = "http://internal.wolfmax.co.uk/football/logo/a" + game.getHomeId()+".png";
                String strAway = "http://internal.wolfmax.co.uk/football/logo/a" + game.getHomeId()+".png";

                
                //Image Loading
//                new ImageLoaderTask().execute(strHome,strAway,teamOneName,teamTwoName);
                
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
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Game game=(Game) gamesList.getItemAtPosition(arg2);
		
		SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance("Details",game);
        dialogFragment.show(this.getFragmentManager(),"");		
	}

}

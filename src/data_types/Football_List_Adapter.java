package data_types;

import java.util.ArrayList;

import com.example.kclhack.R;
import com.novoda.imageloader.core.util.DirectLoader;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Football_List_Adapter extends ArrayAdapter<Game> {
	

	public String urlEndHome;
	public String urlEndAway;
	public String urlStart = "http://internal.wolfmax.co.uk/football/logo/a";
	private LayoutInflater li;
	ArrayList<Game> gameList;
	public String strHome;
	public String strAway;
	
	public Football_List_Adapter(Context context, ArrayList<Game> gameList) {
		super(context, R.layout.item_list_football,gameList);
		
		
		
		li = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		this.gameList=gameList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = convertView;
		if(itemView==null)
			itemView = li.inflate(R.layout.item_list_football, parent, false);
		//get current game

		
		Game currentGame = gameList.get(position);
		urlEndHome = currentGame.getHomeId();
		urlEndAway = currentGame.getAwayId();
		
		strHome = urlStart + urlEndHome + ".png";
		strAway = urlStart + urlEndAway + ".png";
		
		
		
		
		
		//fill views
		ImageView hostTeam = (ImageView) itemView.findViewById(R.id.host_team);
		ImageView guestTeam = (ImageView) itemView.findViewById(R.id.guest_team);
		//profile pic should be changed here
		TextView teamOne = (TextView) itemView.findViewById(R.id.item_TeamOne_TV);
		TextView score = (TextView) itemView.findViewById(R.id.item_Score_TV);
		TextView teamTwo = (TextView) itemView.findViewById(R.id.item_TeamTwo_TV);
		TextView time = (TextView) itemView.findViewById(R.id.item_time_TV);
		//put values
		teamOne.setText(currentGame.getTeam_One());
		score.setText(currentGame.getScore());
		teamTwo.setText(currentGame.getTeam_Two());
		time.setText(currentGame.getTime()+"\"");
		new ImageLoaderTask().execute(strHome,strAway,hostTeam,guestTeam);

		return itemView;
	}
	
	protected class ImageLoaderTask extends AsyncTask<Object, Void, Bitmap>{
		String urlEndHomeString;
		String urlEndAwayString;
		 ImageView hostTeam;
		 ImageView guestTeam;
		Bitmap bb;
	@Override
	protected Bitmap doInBackground(Object... params) {
		urlEndHomeString = (String) params[0];
		urlEndAwayString = (String) params[1];
		hostTeam= (ImageView) params[2];
		guestTeam = (ImageView) params[3];
		
		bb = new DirectLoader().download(urlEndAwayString);
		
		Bitmap b = new DirectLoader().download(urlEndHomeString);
		return b;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		
		
		guestTeam.setImageBitmap(bb);
		hostTeam.setImageBitmap(result);
	}
		
	}
	

}
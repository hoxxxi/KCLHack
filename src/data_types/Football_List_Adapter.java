package data_types;

import java.util.ArrayList;
import java.util.Random;

import com.example.kclhack.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class Football_List_Adapter extends ArrayAdapter<Game> {

	private LayoutInflater li;
	ArrayList<Game> gameList;
	
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
		//fill views
		ImageView leaguePic = (ImageView) itemView.findViewById(R.id.item_our_immage);
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
		return itemView;
	}
	
	

}

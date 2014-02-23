package fragments;

import com.example.kclhack.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class LiveFragment extends Fragment {
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		return inflater.inflate(R.layout.live_fragment, container, false);
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		// THE ACTUAL CODE SHOULD BE HERE
		super.onActivityCreated(savedInstanceState);
		
	}

}

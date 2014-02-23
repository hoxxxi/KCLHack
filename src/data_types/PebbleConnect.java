package data_types;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class PebbleConnect {
	public static void sendAlertToPebble(Activity currentActivity, String title, String body) {
		
		final Intent i = new Intent("com.getpebble.action.SEND_NOTIFICATION");
		final Map data = new HashMap();
		data.put("title", title);
		data.put("body", body);
		final JSONObject jsonData = new JSONObject(data);
		final String notificationData = new JSONArray().put(jsonData)
				.toString();
		i.putExtra("messageType", "PEBBLE_ALERT");
		i.putExtra("sender", "MyAndroidApp");
		i.putExtra("notificationData", notificationData);
		Log.d("send", "About to send a modal alert to Pebble: "
				+ notificationData);
		currentActivity.sendBroadcast(i);
		
	}

}

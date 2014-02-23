package data_types;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
import com.example.kclhack.MainActivity;
 
public class Game {
        private String team_One, team_Two, score,homeid,awayid;
        private String baseURL = "http://internal.wolfmax.co.uk/football/FakeData";
        private static String latestUpdate;
        private String time;
        public static ArrayList<GameDetail> gameDetails = new ArrayList<GameDetail>();
       
        public String getHomeId() {
                return homeid;
        }
 
        public void setHomeId(String id) {
                this.homeid = id;
        }
       
        public String getAwayId()
        {
                return awayid;
        }
       
        public void setAwayId(String id)
        {
                this.awayid = id;
        }
       
 
        public Game(String team_One, String team_Two, String score, String time) {
                super();
                this.team_One = team_One;
                this.team_Two = team_Two;
                this.score = score;
                this.time = time;
        }
 
        public String getTeam_One() {
                return team_One;
        }
 
        public void setTeam_One(String team_One) {
                this.team_One = team_One;
        }
 
        public String getTeam_Two() {
                return team_Two;
        }
 
        public void setTeam_Two(String team_Two) {
                this.team_Two = team_Two;
        }
 
        public String getScore() {
                return score;
        }
 
        public void setScore(String score) {
                this.score = score;
        }
 
        public String getLatestUpdate() {
                return latestUpdate;
        }
 
        public void setLatestUpdate(String latestEvent) {
                this.latestUpdate = latestEvent;
        }
 
        public String getTime() {
                return time;
        }
 
        public void setTime(String time) {
                this.time = time;
        }
       
        public static void fetchLiveDetails(String url)
        {
                //"http://api.statsfc.com/live.json?key=gL7Q3AhOOdQCZI0GggwCC4KlUwf3DaAWXUhfhyLJ&competition=premier-league&team=" + game.getTeam_One()
                String jsonDetailsString = readData(url);
                try
                {
                        JSONArray arr = new JSONArray(jsonDetailsString);
                        JSONObject ob = arr.getJSONObject(0);
                        JSONArray detailsArray = ob.getJSONArray("incidents");
                       
                        for(int i=0;i<detailsArray.length();i++)
                        {
                                JSONObject detailObject = detailsArray.getJSONObject(i);
                               
                                String type = detailObject.getString("type");
                                String team = detailObject.getString("team");
                                String player = detailObject.getString("player");
                                String minute = detailObject.getString("minute");
                               
                                Log.v("tx",minute);
                                GameDetail detail = new GameDetail(type, team, player, minute);
                                gameDetails.add(detail);
                                latestUpdate = gameDetails.get(gameDetails.size()-1).getDetailType() + "," + gameDetails.get(gameDetails.size()-1).getDetailTeam() + "," + gameDetails.get(gameDetails.size()-1).getDetailPlayer() + "," + gameDetails.get(gameDetails.size()-1).getDetailTime();
                        }
                }catch(JSONException e)
                {
                        e.printStackTrace();
                }
        }
       
        public void addDetail(GameDetail detail)
        {
                gameDetails.add(detail);
        }
       
        public GameDetail getLatestDetail()
        {
                return gameDetails.get(gameDetails.size()-1);
        }
       
        //Adding all the JSON methods for fetchin information from the website
       
        //reading from website and returning JSON
        public static String readData(String url) {
                // Create download objects
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                StringBuilder content = new StringBuilder();
                try {
                        // Execute response and create input stream
                        HttpResponse response = client.execute(get);
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode == 200) {
                                InputStream in = response.getEntity().getContent();
                                BufferedReader reader = new BufferedReader(
                                                new InputStreamReader(in));
                                // Build string from input stream
                                String readLine = reader.readLine();
                                while (readLine != null) {
                                        content.append(readLine);
                                        readLine = reader.readLine();
                                }
                        } else {
                                Log.w("DATA RETRIEVAL",
                                                "Unable to read data.HTTP response code = "
                                                                + responseCode);
                                content = null;
                        }
                } catch (ClientProtocolException e) {
                        Log.e("readData", "ClientProtocolException:\n" + e.getMessage());
                } catch (IOException e) {
                        Log.e("readData", "IOException:\n+e.getMessage()");
                }
                // return data
                if (content == null) {
                        return (null);
                } else {
                        return (content.toString());
                }
        }
       
        //Retrieving the games from the last week
        public static ArrayList<Game> getPastGames() {
                ArrayList<Game> gamesListPast = new ArrayList<Game>();
 
                String jsonString = readData("http://api.statsfc.com/results.json?key=gL7Q3AhOOdQCZI0GggwCC4KlUwf3DaAWXUhfhyLJ&competition=premier-league&from="
                                + getTodayAndBefore7Days().get(1)
                                + "&to="
                                + getTodayAndBefore7Days().get(0));
                try {
                        JSONArray arr = new JSONArray(jsonString);
                        for (int i = 0; i < arr.length(); i++) {
                                JSONObject ob = arr.getJSONObject(i);
                                String homeID = ob.getString("home_id");
                                String awayID = ob.getString("away_id");
                               
                                String time = ob.getString("date");
                                String score = ob.getJSONArray("fulltime").getString(0) + "-"
                                                + ob.getJSONArray("fulltime").getString(1);
                                String homeTeam = ob.getString("home");
                                String awayTeam = ob.getString("away");
 
                                Game game = new Game(homeTeam, awayTeam, score, time);
                                game.setHomeId(homeID);
                                game.setAwayId(awayID);
                                gamesListPast.add(game);
                        }
                } catch (JSONException e) {
                        e.printStackTrace();
                }
 
                return gamesListPast;
        }
       
        //Getting a list of live games being played
        public static ArrayList<Game> getLiveGames() {
                ArrayList<Game> gamesListLive = new ArrayList<Game>();
                //"http://api.statsfc.com/live.json?key=gL7Q3AhOOdQCZI0GggwCC4KlUwf3DaAWXUhfhyLJ&competition=premier-league&timezone=CET"
                String jsonString = readData("http://internal.wolfmax.co.uk/football/FakeData1.txt");
                try {
                        JSONArray arr = new JSONArray(jsonString);
                        for (int i = 0; i < arr.length(); i++) {
                                JSONObject ob = arr.getJSONObject(i);
                               
                                String homeTeam = ob.getString("home");
                                String awayTeam = ob.getString("away");
                                String score = ob.getJSONArray("fulltime").getString(0) + "-" + ob.getJSONArray("fulltime").getString(1);
                                String time = ob.getString("date");
                                String homeID = ob.getString("home_id");
                                String awayID = ob.getString("away_id");
 
                                Game game = new Game(homeTeam, awayTeam, score, time+"\"");
                                game.setHomeId(homeID);
                                game.setAwayId(awayID);
                                gamesListLive.add(game);
                        }
                } catch (JSONException e) {
                        e.printStackTrace();
                }
               
 
                return gamesListLive;
 
        }
       
        //Getting all the fixtures for the following week
        public static ArrayList<Game> getUpcomingGames() {
                ArrayList<Game> gamesListUpcoming = new ArrayList<Game>();
                String jsonString = readData("http://api.statsfc.com/fixtures.json?key=gL7Q3AhOOdQCZI0GggwCC4KlUwf3DaAWXUhfhyLJ&competition=premier-league&from="
                                + getTodayAndAfter7Days().get(0)
                                + "&to="
                                + getTodayAndAfter7Days().get(1));
                try {
                        JSONArray arr = new JSONArray(jsonString);
                        for (int i = 0; i < arr.length(); i++) {
                                JSONObject ob = arr.getJSONObject(i);
                                String date = ob.getString("date");
                                String homeTeam = ob.getString("home");
                                String awayTeam = ob.getString("away");
                                String homeID = ob.getString("home_id");
                                String awayID = ob.getString("away_id");
                                String score = "0=0";
 
                                Game game = new Game(homeTeam, awayTeam, score, date);
                                game.setHomeId(homeID);
                                game.setAwayId(awayID);
                                gamesListUpcoming.add(game);
 
                        }
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                ;
 
                return gamesListUpcoming;
        }
       
        //Getting the date today and after 7 days
        public static ArrayList<String> getTodayAndAfter7Days() {
                ArrayList<String> array = new ArrayList<String>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                array.add(dateFormat.format(today));
                calendar.add(Calendar.DATE, 7);
                Date after = calendar.getTime();
                array.add(dateFormat.format(after));
 
                return array;
        }
 
        //Getting the date today and before 7 days
        public static ArrayList<String> getTodayAndBefore7Days() {
                ArrayList<String> array = new ArrayList<String>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                array.add(dateFormat.format(today));
                calendar.add(Calendar.DATE, -7);
                Date before = calendar.getTime();
                array.add(dateFormat.format(before));
 
                return array;
        }
}
package data_types;

public class Game {
private String team_One, team_Two, score, latestUpdate;
private int time;
public Game(String team_One, String team_Two, String score, String latestUpdate,
		int time) {
	super();
	this.team_One = team_One;
	this.team_Two = team_Two;
	this.score = score;
	this.latestUpdate = latestUpdate;
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
public int getTime() {
	return time;
}
public void setTime(int time) {
	this.time = time;
}
}

package data_types;

public class GameDetail 
{
	private String type;
	private String team;
	private String player;
	private String time;
	
	public GameDetail(String type, String team, String player, String time)
	{
		this.type = type;
		this.team = team;
		this.player = player;
		this.time = time;
	}
	
	public String getDetailType()
	{
		return type;
	}
	
	public String getDetailTeam()
	{
		return team;
	}
	
	public String getDetailPlayer()
	{
		return player;
	}
	
	public String getDetailTime()
	{
		return time;
	}
}
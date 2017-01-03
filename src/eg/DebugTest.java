package eg;

public class DebugTest {
	private String _name;
	private String _team;
	
	
	
	public void set_name(String _name) {
		this._name = _name;
	}



	public void set_team(String _team) {
		this._team = _team;
	}



	public String personInfo(){
		String info="my name is "+_name +" and my team is "+_team;
		return info;
	}
}

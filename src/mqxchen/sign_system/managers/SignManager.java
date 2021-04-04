package mqxchen.sign_system.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Sign;

public class SignManager {
	
	private List<Sign> selectedSigns = new ArrayList<Sign>();
	private List<String> selectedServers = new ArrayList<String>();
	private List<String> selectedServersMaximumPlayers = new ArrayList<String>();
	private HashMap<String, Integer> currentPlayers = new HashMap<String, Integer>();

	
	
	public List<Sign> getSelectedSigns(){
		return this.selectedSigns;
	}
	public List<String> getSelectedServers(){
		return this.selectedServers;
	}
	public List<String> getSelectedServerMaximumPlayers(){
		return this.selectedServersMaximumPlayers;
	}

	public HashMap<String, Integer> getCurrentPlayers(){
		return this.currentPlayers;
	}
		
}

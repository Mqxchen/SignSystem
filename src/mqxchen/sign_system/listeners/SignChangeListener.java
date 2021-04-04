package mqxchen.sign_system.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import mqxchen.sign_system.main.Main;
import mqxchen.sign_sytem.managers.SignManager;

public class SignChangeListener implements Listener{
	
	private Main main;
	private SignManager sm;
	public SignChangeListener(Main main) {
		this.main = main;
		this.sm = this.main.getSignManager();
	}
	
	@EventHandler
	private void onSignChange(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("[joinsign]")){
			String server = event.getLine(1);
			String maxplayer = event.getLine(2);
			
			Sign sign = (Sign) event.getBlock().getState();
			
			sign.setLine(0, server);
			sign.setLine(1, "[§aGameState§r]");

			//sign.setLine(2, "MapName"); update Later
			sign.setLine(2, "/"+maxplayer);
			sign.setLine(3, event.getLine(3).replace("&", "§"));

			sign.update();
			
			
			this.sm.getSelectedServers().add(server);
			this.sm.getSelectedSigns().add(sign);
			this.sm.getSelectedServerMaximumPlayers().add(maxplayer);
		}
	}

}

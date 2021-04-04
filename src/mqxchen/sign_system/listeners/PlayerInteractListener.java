package mqxchen.sign_system.listeners;

import java.sql.SQLException;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import mqxchen.sign_system.main.Main;
import mqxchen.sign_system.managers.BungeeCordManager;
import mqxchen.sign_system.managers.SignManager;

public class PlayerInteractListener implements Listener{

	private Main main;
	private SignManager sm;
	private BungeeCordManager bm;
	
	
	public PlayerInteractListener(Main main) {
		this.main = main;
		this.sm = this.main.getSignManager();
		this.bm = this.main.getBungeeCordManager();
	}
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
		
		//Player clicked a Block
		if (event.getClickedBlock() != null) {
			//Player clicked a Sign
			if (event.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) event.getClickedBlock().getState();
				//Sign is selected
				if (this.sm.getSelectedSigns().contains(sign)) {
					String selectedserver = sm.getSelectedServers().get(sm.getSelectedSigns().indexOf(sign));
					if (this.sm.getCurrentPlayers().get(selectedserver) < Integer.parseInt(this.sm.getSelectedServerMaximumPlayers().get(this.sm.getSelectedServers().indexOf(selectedserver)))) {
						if (this.main.getGameStateReader().getGameState(selectedserver).equals("lobby") || this.main.getGameStateReader().getGameState(selectedserver).equals("starting")) {
							this.bm.sendPlayerToServer(event.getPlayer(), selectedserver);
						}else {
							event.getPlayer().sendMessage("Der Server ist bereits voll");
						}
					}else {
						event.getPlayer().sendMessage("Der Server ist bereits voll");
					}
			
				}else {
					return;
				}
			}
		}else {
			return;
		}
		
	}
	
	
	
}

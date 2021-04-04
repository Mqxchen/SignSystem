package mqxchen.sign_system.managers;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import mqxchen.sign_system.main.Main;

public class BungeeCordManager implements PluginMessageListener{

	private Main main;
	private SignManager sm;
	
	public BungeeCordManager(Main main) {
		this.main = main;
		this.sm = this.main.getSignManager();
		this.main.getServer().getMessenger().registerOutgoingPluginChannel(main, "BungeeCord");
		this.main.getServer().getMessenger().registerIncomingPluginChannel(main, "BungeeCord", this);
		this.updateServerSigns();
	}
	
	@Override
	public void onPluginMessageReceived(String chanel, Player player, byte[] msg) {
		
		if (chanel.equals("BungeeCord")) {
			
			ByteArrayDataInput in = ByteStreams.newDataInput(msg);
			String subchanel = in.readUTF();
			
			if (subchanel.equals("PlayerCount")) {
				String server = in.readUTF();
				int playercount = in.readInt();
				if (this.sm.getSelectedServers().contains(server)) {
					Sign sign = this.sm.getSelectedSigns().get(this.sm.getSelectedServers().indexOf(server));
					
					this.sm.getCurrentPlayers().put(server, playercount);
					
					String gamestate = "";
					try {
						gamestate = main.getGameStateReader().getGameState(server);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
					
					
					
					sign.setLine(0, "");
					
					if (gamestate.equals("lobby")) {
						if (playercount == 0) {
							sign.setLine(1, "[Lobby]");
							sign.setLine(0, server);

						}else if (Integer.toString(playercount).equals(main.getSignManager().getSelectedServerMaximumPlayers().get(main.getSignManager().getSelectedServers().indexOf(server)))) {
							sign.setLine(0, server);
							sign.setLine(1, "[§eLobby§r]");
						}else {
							sign.setLine(0, server);
							sign.setLine(1, "[§aLobby§r]");
						}
					}else if (gamestate.equals("starting")) {
						if (Integer.toString(playercount).equals(main.getSignManager().getSelectedServerMaximumPlayers().get(main.getSignManager().getSelectedServers().indexOf(server)))) {
							sign.setLine(0, server);
							sign.setLine(1, "[§eLobby§r]");
						}else {
							sign.setLine(0, server);
							sign.setLine(1, "[§aLobby§r]");
						}
					}else if (gamestate.equals("active")) {
						sign.setLine(0, server);
						sign.setLine(1, "[§cActive§r]");
					}else if (gamestate.equals("prerestarting")) {
						sign.setLine(0, server);
						sign.setLine(1, "[§9Restarting§r]");
					}else if (gamestate.equals("restarting")) {
						sign.setLine(0, server);
						sign.setLine(1, "[§9Restarting§r]");
					}else {
						sign.setLine(0, server);
						sign.setLine(1, "[§4ERROR§r]");
					}
					sign.setLine(2, Integer.toString(playercount) + "/" +
							this.sm.getSelectedServerMaximumPlayers().get(this.sm.getSelectedServers().indexOf(server)));
					
					sign.update();
				}
			}
			
		}else {
			return;
		}
		
	}
	
	public void sendPlayerToServer(Player player, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(this.main, "BungeeCord", out.toByteArray());
	}

	private void updateServerSigns() {
		SignManager sm2 = this.sm;
		Main main2 = this.main;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable() {
			@Override
			public void run() {
				for (String server : sm2.getSelectedServers()) {
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("PlayerCount");
					out.writeUTF(server);
					main2.getServer().sendPluginMessage(main2, "BungeeCord", out.toByteArray());
				}
			}
		}, 50, 50);
	}
	
	
}

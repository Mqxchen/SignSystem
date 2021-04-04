package mqxchen.sign_system.main;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import mqxchen.sign_system.listeners.PlayerInteractListener;
import mqxchen.sign_system.listeners.SignChangeListener;
import mqxchen.sign_system.mysql.GameStateReader;
import mqxchen.sign_system.mysql.MySQL;
import mqxchen.sign_sytem.managers.BungeeCordManager;
import mqxchen.sign_sytem.managers.DataManager;
import mqxchen.sign_sytem.managers.SignManager;

public class Main extends JavaPlugin{
	
	
	private PluginManager pm;
	private SignManager sm;
	private BungeeCordManager bm;
	private MySQL mysql;
	private GameStateReader  gsr;
	private DataManager dm;
	
	@Override
	public void onEnable() {
		this.pm = this.getServer().getPluginManager();
		this.sm = new SignManager();
		this.bm = new BungeeCordManager(this);
		registerEvents();
		
		
		loadConfig();
		loadDatabase();

		this.gsr = new GameStateReader(this);
		
		
		
	}
	
	@Override
	public void onDisable() {
	}
	
	private void registerEvents() {
		this.pm.registerEvents(new SignChangeListener(this), this);
		this.pm.registerEvents(new PlayerInteractListener(this), this);
	}

	private void loadConfig() {
		this.dm = new DataManager(this);
	}
	
	private void loadDatabase() {
		this.mysql = new MySQL(
				this.dm.getCustomConfig().getString("GameStateDatabaseName"),
				this.dm.getCustomConfig().getString("GameStateDatabaseUser"),
				this.dm.getCustomConfig().getString("GameStateDatabasePassword"));
	}
	
	public SignManager getSignManager() {
		return this.sm;
	}
	public BungeeCordManager getBungeeCordManager() {
		return this.bm;
	}
	public MySQL getMySQL() {
		return this.mysql;
	}
	
	public GameStateReader getGameStateReader() {
		return this.gsr;
	}
}

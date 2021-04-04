package mqxchen.sign_sytem.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import mqxchen.sign_system.main.Main;



public class DataManager {

	private Main main;
	private File customConfigFile;
    private FileConfiguration customConfig;
	
	public DataManager(Main main) {
		
		this.main = main;
		createCustomConfig();
		}
	
	 private void createCustomConfig() {
	        customConfigFile = new File(main.getDataFolder(), "config.yml");
	        if (!customConfigFile.exists()) {
	            customConfigFile.getParentFile().mkdirs();
	            main.saveResource("config.yml", false);
	            
	            customConfig= new YamlConfiguration();
		        
		 
				//MySql Server GameState data base login information
				customConfig.set("GameStateDatabaseName", "servergamestates");
				customConfig.set("GameStateDatabaseUser", "username");
				customConfig.set("GameStateDatabasePassword", "password");
	            
				try {
					customConfig.save(customConfigFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
	         }

	        customConfig= new YamlConfiguration();
	        try {
	            customConfig.load(customConfigFile);
	        } catch (IOException | InvalidConfigurationException e) {
	            e.printStackTrace();
	        }
	    }
		
	public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
	
	
}

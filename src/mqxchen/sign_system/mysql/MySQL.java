package mqxchen.sign_system.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;



public class MySQL {

    private Connection connection;

    private String database;
    private String user;
    private String password;

    public MySQL(String database, String user, String password){
        this.database = database;
        this.user = user;
        this.password = password;

        connect();

    }

    public void connect(){
        try {
        	connection = DriverManager.getConnection("jdbc:mysql://localhost/"+ database, user, password);
        } catch (SQLException e) {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&cSignSystem&r] &aKonnte sich nicht mit der Datenbank verbinden."));
        	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "[&cSignSystem&r] &aUeberpruefen sie ob die Config korekt ausgefuellt wurde."));        }
    }

    public void disconnect(){
        try {
            if (this.hasConnection()) {
                this.connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean hasConnection(){
        if (this.connection != null){
            return true;
        }
        return false;
    }

    public Connection getConnection(){
        return this.connection;
    }




}

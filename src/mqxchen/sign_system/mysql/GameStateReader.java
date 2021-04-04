package mqxchen.sign_system.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mqxchen.sign_system.main.Main;

public class GameStateReader {
	
	private Main main;
	
	public GameStateReader(Main main) {
		this.main = main;
	}

	public boolean isInDataBase(String server) throws SQLException {
		
		   
		PreparedStatement st;
		try {
			st = main.getMySQL().getConnection().prepareStatement("SELECT * FROM servers WHERE servername = ?");
			st.setString(1, server);
	        ResultSet resultSet =  st.executeQuery();
	        while(resultSet.next()){
	            return true;
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return false;  
	}
	
	public String getGameState(String server) throws SQLException {
		String gamestate = "";
        if(isInDataBase(server)){
            PreparedStatement st = null;
            st = main.getMySQL().getConnection().prepareStatement("SELECT gamestate FROM servers WHERE servername = ?");
            st.setString(1, server);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                gamestate = resultSet.getString("gamestate");
            }
        }
        return gamestate;
	}
	
	
}

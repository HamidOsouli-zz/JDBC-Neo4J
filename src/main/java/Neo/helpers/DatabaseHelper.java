package Neo.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseHelper
{
   private static DatabaseHelper instance;
   private Connection connection;
   private PropertiesHelper propertiesHelper;
   private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

   public static DatabaseHelper getInstance() {
      if (instance == null) {
         instance = new DatabaseHelper();
      }
      return instance;
   }

   private DatabaseHelper() {
      try {
         this.propertiesHelper = PropertiesHelper.getInstance();
         Class.forName(this.propertiesHelper.getProperty("DRIVER_NAME"));
      } catch (ClassNotFoundException e) {
         logger.error("Cannot connect to JDBC driver");
         throw new RuntimeException(e);
      }
   }

   public Connection getConnection(){
      String url = this.propertiesHelper.getProperty("URL");
      String username = this.propertiesHelper.getProperty("USERNAME");
      String password = this.propertiesHelper.getProperty("PASSWORD");
      try {
         this.connection = DriverManager.getConnection(url, username, password);
         return this.connection;
      } catch(SQLException e) {
         logger.error("Cannot connect to " + url + " with " + username + " and " + password);
         throw new RuntimeException(e);
      }
   }

   private static void closeConnection( Connection con ) throws SQLException {
      if ( con != null ) {
         con.close();
      }
   }

   private static void closePreparedStatement( PreparedStatement stmt ) throws SQLException {
      if ( stmt != null ) {
         stmt.close();
      }
   }

   private static void closeResultSet( ResultSet rs ) throws SQLException {
      if ( rs != null ) {
         rs.close();
      }
   }

   public static void closeAll(Connection con, PreparedStatement stmt, ResultSet rs) {
      try {
         closeConnection(con);
         closePreparedStatement(stmt);
         closeResultSet(rs);
      } catch(SQLException sqlException) {
         logger.error("Cannot close database connection or resultSet or statement");
      }
   }
}

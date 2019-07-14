package Neo.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCHelper
{
   private static Connection connection;
   private static Properties properties;
   static Logger logger = LoggerFactory.getLogger(JDBCHelper.class);

   static
   {
      try
      {
         properties = JDBCProperties.getInstance();
         Class.forName(properties.getProperty("DRIVER_NAME"));
      }
      catch ( ClassNotFoundException | IOException e )
      {
         logger.error(e.getMessage());
      }
   }

   public static Connection getConnection() throws SQLException {
      connection = DriverManager.getConnection( properties.getProperty("URL"), properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"));
      return connection;
   }

   public static void closeConnection( Connection con ) throws SQLException
   {
      if ( con != null )
      {
         con.close();
      }
   }

   public static void closePreparedStatement( PreparedStatement stmt ) throws SQLException
   {
      if ( stmt != null )
      {
         stmt.close();
      }
   }

   public static void closeResultSet( ResultSet rs ) throws SQLException
   {
      if ( rs != null )
      {
         rs.close();
      }
   }

   public static void closeAll(Connection con, PreparedStatement stmt, ResultSet rs) throws SQLException {
      closeConnection(con);
      closePreparedStatement(stmt);
      closeResultSet(rs);
   }

}

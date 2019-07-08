package Neo.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Neo.entity.Person;
import Neo.helpers.JDBCHelper;
import org.json.*;
public class JDBCDaoImpl
{
   public static final String INSERT_SQL_QUERY     = "CREATE (n:Person {uid: ?, firstName:?, lastName: ?, email:?}) return n";
   public static final String UPDATE_SQL_QUERY     = "UPDATE PERSON SET FIRST_NAME=? WHERE ID=?";
   public static final String SELECT_SQL_QUERY     = "SELECT ID,FIRST_NAME,LAST_NAME,EMAIL FROM PERSON WHERE ID=?";
   public static final String SELECT_ALL_SQL_QUERY = "MATCH (n:Person) RETURN n";
   public static final String DELETE_SQL_QUERY     = "DELETE FROM PERSON WHERE ID=?";
   public static final String DELETE_ALL_SQL_QUERY = "DELETE FROM PERSON";

   public static void main( String[] args )
   {
      Person person = new Person(1, "James", "Bond", "007@jamesbond.com" );
      Person person2 = new Person(54, "Forest", "Gump", "forestgump@jamesbond.com");
      try
      {
         // Create
         insert( person );
         insert( person2 );
         // Read(get all)
         List<Person> persons = getAll();
         for ( Person p : persons )
         {
            System.out.println( p );
         }
         System.out.println("\n--------------------------------------------------------------------------------------" );
         // Update
//         person.setFirstName( "Updated name" );
//         update( person );
//         System.out.println( "Updated the first name of person 2. This is \"U\" of CRUD " );
//         System.out.println();
//         System.out.println( "--------------------------------------------------------------------------------------" );
//         // Read(get one )
//         Person tempPerson2 = get( 2 );
//         System.out.println( "Retrived person2 from DB " + tempPerson2 );
//         System.out.println();
//         System.out.println( "--------------------------------------------------------------------------------------" );
//         // Delete
//         delete( 2 );
//         System.out.println( "Deleted person2 from DB.This is \"D\" of CRUD " );
//         System.out.println();
//         System.out.println( "--------------------------------------------------------------------------------------" );
//         // Read(get all)
//         List<Person> tempPersons = getAll();
//         System.out.println( "Retrived all persons from DB. Notice person 2 is not present" );
//         for ( Person p : tempPersons )
//         {
//            System.out.println( p );
//         }
//         System.out.println();
//         System.out.println( "--------------------------------------------------------------------------------------" );
//         // Delete
//         deleteAll();
//         System.out.println( "Deleted all records" );
      }
      catch ( SQLException e )
      {
         System.out.println( "Exception occured " + e.getMessage() );
      }
      catch ( Exception e )
      {
         System.out.println( "Exception occured " + e.getMessage() );
      }
   }

   private static void deleteAll() throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         ps = con.prepareStatement( DELETE_ALL_SQL_QUERY );
         ps.execute();
         System.out.println( "deleteAllRecords => " + ps.toString() );
      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
   }

   private static void delete( int id ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement( DELETE_SQL_QUERY );
         ps.setLong( 1, id );
         ps.execute();
         System.out.println( "deletePerson => " + ps.toString() );
      }
      catch ( SQLException e )
      {
         throw e;
      }

      finally
      {
         try
         {
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
   }

   private static Person get( long id ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      Person person = new Person();
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return person;
         }
         ps = con.prepareStatement( SELECT_SQL_QUERY );
         ps.setLong( 1, id );
         rs = ps.executeQuery();
         System.out.println( "retrivePerson => " + ps.toString() );
         while ( rs.next() )
         {
            person.setId( rs.getLong( "ID" ) );
            person.setFirstName( rs.getString( "FIRST_NAME" ) );
            person.setLastName( rs.getString( "LAST_NAME" ) );
            person.setEmail( rs.getString( 4 ) ); // this is to show that we can retrive using index of the column
         }

      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closeResultSet( rs );
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
      return person;
   }

   private static void update( Person person ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;

      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         con.setAutoCommit( false );
         ps = con.prepareStatement( UPDATE_SQL_QUERY );
         ps.setString( 1, person.getFirstName() );
         ps.execute();
         System.out.println( "updatePersonFirstName => " + ps.toString() );
         con.commit();

      }
      catch ( SQLException e )
      {
         try
         {
            if ( con != null )
            {
               con.rollback();
               throw e;
            }
         }
         catch ( SQLException e1 )
         {
            throw e1;
         }
      }
      finally
      {
         try
         {
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }

   }

   private static List<Person> getAll() throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      List<Person> persons = new ArrayList<Person>();
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return persons;
         }
         ps = con.prepareStatement( SELECT_ALL_SQL_QUERY );
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            Person p = new Person();
            JSONObject data = new JSONObject(rs.getString( "n" ));
            System.out.println(data);
            System.out.println(data.getString("firstName"));
            p.setFirstName(data.getString("firstName"));
            p.setLastName(data.getString("lastName"));
            p.setEmail(data.getString("email"));
            persons.add( p );
         }
      }
      catch ( SQLException e )
      {
         throw e;

      }

      finally
      {
         try
         {
            JDBCHelper.closeResultSet( rs );
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }
      return persons;
   }

   private static void insert( Person p ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         if ( con == null )
         {
            System.out.println( "Error getting the connection. Please check if the DB server is running" );
            return;
         }
         con.setAutoCommit( false );
         ps = con.prepareStatement( INSERT_SQL_QUERY );
         ps.setLong( 1, p.getUId() );
         ps.setString( 2, p.getFirstName() );
         ps.setString( 3, p.getLastName() );
         ps.setString( 4, p.getEmail() );
         ps.execute();
         System.out.println( "insertPerson => " + ps.toString() );
         con.commit();
      }
      catch ( SQLException e )
      {
         try
         {
            if ( con != null )
            {
               con.rollback();
            }
         }
         catch ( SQLException e1 )
         {
            throw e1;
         }
         throw e;
      }
      finally
      {
         try
         {
            JDBCHelper.closePreparedStatement( ps );
            JDBCHelper.closeConnection( con );
         }
         catch ( SQLException e )
         {
            throw e;
         }
      }

   }

}

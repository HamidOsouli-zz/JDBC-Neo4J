package Neo.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Neo.di.PersonDI;
import Neo.entity.Person;
import Neo.helpers.JDBCHelper;
import org.json.*;

public class PersonDaoImpl implements PersonDI
{
   public final String INSERT_SQL_QUERY     = "CREATE (n:Person {uid: ?, firstName:?, lastName: ?, email:?}) return n";
   public final String UPDATE_SQL_QUERY     = "MATCH (n:Person {uid: ?}) SET n.firstName=?, n.lastName=?, n.email=?";
   public final String SELECT_SQL_QUERY     = "MATCH (n:Person {uid: ?}) RETURN n";
   public final String SELECT_ALL_SQL_QUERY = "MATCH (n:Person) RETURN n";
   public final String DELETE_SQL_QUERY     = "MATCH (n:Person {uid: ?}) OPTIONAL MATCH (n)-[r]-() DELETE n,r RETURN n";
   public final String DELETE_ALL_SQL_QUERY = "MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r";
   public final String ADD_AS_A_COLLEAGUE = "MATCH (a:Person {uid: ?}), (b: Person {uid: ?}) MERGE (a)-[r:Hamkar {since: \"2019\"}]->(b)";

   @Override
   public void deleteAll() throws SQLException
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

   @Override
   public void delete( long uid ) throws SQLException
   {
      Connection con = null;
      PreparedStatement ps = null;
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement( DELETE_SQL_QUERY );
         ps.setLong( 1, uid );
         ps.execute();
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

   @Override
   public List<Person> getAll() throws SQLException
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
            p.setUId(data.getLong("uid"));
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

   @Override
   public Person get( long uid ) throws SQLException
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
         ps.setLong( 1, uid );
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            JSONObject p = new JSONObject(rs.getString("n"));
            person.setUId( p.getLong("uid") );
            person.setFirstName( p.getString( "firstName" ) );
            person.setLastName( p.getString( "lastName" ) );
            person.setEmail( p.getString( "email" ) );
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

   @Override
   public void update( long uid, Person person ) throws SQLException
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
         ps.setLong( 1, uid );
         ps.setString( 2, person.getFirstName() );
         ps.setString( 3, person.getLastName() );
         ps.setString( 4, person.getEmail() );
         ps.execute();
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

   @Override
   public void create( Person p ) throws SQLException
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

   @Override
   public void addAsColleague( Person p1, Person p2 ) throws SQLException
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
         ps = con.prepareStatement(ADD_AS_A_COLLEAGUE);
         ps.setLong( 1, p1.getUId() );
         ps.setLong( 2, p2.getUId() );
         ps.execute();
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

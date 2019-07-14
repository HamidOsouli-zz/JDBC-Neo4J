package Neo.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Neo.di.PersonDI;
import Neo.entity.Person;
import Neo.helpers.JDBCHelper;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDao implements PersonDI
{
   static Logger logger = LoggerFactory.getLogger(PersonDao.class);

   private Connection con = null;
   private PreparedStatement ps = null;
   private ResultSet rs = null;

   @Override
   public void deleteAll() throws SQLException
   {
      try {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r");
         ps.execute();
      }
      catch (SQLException ex) {
         logger.error(ex.getMessage());
         throw ex;
      }
      finally {
         // before
//         try
//         {
//            JDBCHelper.closePreparedStatement(ps);
//            JDBCHelper.closeConnection(con);
//         }
//         catch ( SQLException e )
//         {
//            throw e;
//         }
         // after
         JDBCHelper.closeAll(con, ps, rs);
      }
   }

   @Override
   public void delete( int uid ) throws SQLException
   {
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) OPTIONAL MATCH (n)-[r]-() DELETE n,r RETURN n");
         ps.setInt( 1, uid );
         ps.execute();
      }
      catch ( SQLException  e )
      {
         logger.error(e.getMessage());
         throw e;
      }
      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }
   }

   @Override
   public List<Person> getAll() throws SQLException
   {
      List<Person> persons = new ArrayList<Person>();
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement("MATCH (n:Person) RETURN n");
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            Person p = new Person();
            JSONObject data = new JSONObject(rs.getString( "n" ));
            p.setUId(data.getInt("uid"));
            p.setFirstName(data.getString("firstName"));
            p.setLastName(data.getString("lastName"));
            p.setEmail(data.getString("email"));
            persons.add( p );
         }
      }
      catch ( SQLException e )
      {
         logger.error(e.getMessage());
         throw e;
      }

      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }
      return persons;
   }

   @Override
   public Person get( int uid ) throws SQLException
   {
      Person person = new Person();
      try
      {
         con = JDBCHelper.getConnection();
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) RETURN n");
         ps.setInt( 1, uid );
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            JSONObject p = new JSONObject(rs.getString("n"));
            person.setUId( p.getInt("uid") );
            person.setFirstName( p.getString( "firstName" ) );
            person.setLastName( p.getString( "lastName" ) );
            person.setEmail( p.getString( "email" ) );
         }
      }
      catch ( SQLException e )
      {
         logger.error(e.getMessage());
         throw e;
      }
      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }
      return person;
   }

   @Override
   public void update( int uid, Person person ) throws SQLException
   {
      try
      {
         con = JDBCHelper.getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) SET n.firstName=?, n.lastName=?, n.email=?, n.edited=?");
         ps.setInt( 1, uid );
         ps.setString( 2, person.getFirstName() );
         ps.setString( 3, person.getLastName() );
         ps.setString( 4, person.getEmail() );
         ps.setLong(5, person.getEdited());
         ps.execute();
         con.commit();
      }
      catch ( SQLException e )
      {
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error("cannot rollback the operation" +  rollbackExe.getMessage());
            throw rollbackExe;
         }
         logger.error(e.getMessage());
         throw e;
      }
      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }

   }

   @Override
   public void create( Person p ) throws SQLException
   {
      try
      {
         con = JDBCHelper.getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("CREATE (n:Person {uid: ?, firstName:?, lastName: ?, email:?, created: ?}) return n");
         ps.setInt(1, p.getUId());
         ps.setString(2, p.getFirstName());
         ps.setString(3, p.getLastName());
         ps.setString(4, p.getEmail());
         ps.setLong(5, p.getCreated());
         ps.execute();
         con.commit();
      }
      catch ( SQLException e )
      {
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error(rollbackExe.getMessage());
            throw rollbackExe;
         }
         logger.error(e.getMessage());
         throw e;
      }
      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }
   }

   @Override
   public void addAsColleague( Person p1, Person p2 ) throws SQLException
   {
      try
      {
         con = JDBCHelper.getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("MATCH (a:Person {uid: ?}), (b: Person {uid: ?}) MERGE (a)-[r:Hamkar {since: \"2019\"}]->(b)");
         ps.setInt( 1, p1.getUId() );
         ps.setInt( 2, p2.getUId() );
         ps.execute();
         con.commit();
      }
      catch ( SQLException e )
      {
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error("cannot rollback the operation" +  rollbackExe.getMessage());
            throw rollbackExe;
         }
         logger.error(e.getMessage());
         throw e;
      }
      finally
      {
         JDBCHelper.closeAll(con, ps, rs);
      }
   }
}

package Neo.dao.person;
import java.sql.*;
import java.util.ArrayList;
import Neo.entity.Person;
import Neo.helpers.DatabaseHelper;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonDaoImpl implements PersonDao
{
   private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

   private Connection con = null;
   private PreparedStatement ps = null;
   private ResultSet rs = null;

   @Override
   public void deleteAll() {
      try {
         con = DatabaseHelper.getInstance().getConnection();
         ps = con.prepareStatement("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r");
         ps.execute();
      }
      catch (SQLException ex) {
         logger.error("error in deleting all entities with relations",ex);
         throw new RuntimeException(ex);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
   }

   @Override
   public void delete( Person p )
   {
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) OPTIONAL MATCH (n)-[r]-() DELETE n,r RETURN n");
         ps.setInt( 1, p.getUId() );
      }
      catch ( SQLException  e )
      {
         logger.error("error in deleting the user with uid of " + p.getUId());
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
   }

   @Override
   public ArrayList<Person> getAll()   {
      ArrayList<Person> persons = new ArrayList<Person>();
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         ps = con.prepareStatement("MATCH (n:Person) RETURN n");
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            Person p = new Person();
            JSONObject data = new JSONObject(rs.getString( "n" ));
            this.setPersonObject(p, data);
            persons.add( p );
         }
      }
      catch ( SQLException e )
      {
         logger.error("Cannot fetch all persons");
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
      return persons;
   }

   @Override
   public Person get( Person p )
   {
      Person person = new Person();
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) RETURN n");
         ps.setInt( 1, p.getUId() );
         rs = ps.executeQuery();
         while ( rs.next() )
         {
            JSONObject jsonObject = new JSONObject(rs.getString("n"));
            this.setPersonObject(person, jsonObject);
         }
      }
      catch ( SQLException e )
      {
         logger.error("Cannot get person with uid of " + p.getUId());
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
      return person;
   }

   @Override
   public Person update( Person person )
   {
       Person p = new Person();
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("MATCH (n:Person {uid: ?}) SET n.firstName=?, n.lastName=?, n.email=?, n.edited=? RETURN n");
         ps.setInt(1, person.getUId());
         ps.setString(2, person.getFirstName());
         ps.setString(3, person.getLastName());
         ps.setString(4, person.getEmail());
         ps.setLong(5, person.getCreated());
         ps.setLong(6, person.getEdited());
         ps.execute();
         con.commit();
         ResultSet rs = ps.executeQuery();
         while ( rs.next() )
         {
            JSONObject jsonObject = new JSONObject(rs.getString("n"));
            this.setPersonObject(p, jsonObject);
         }
      }
      catch ( SQLException e )
      {
         logger.error("Cannot update person with uid of " + person.getUId());
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error("cannot rollback the operation in updating user with uid of " + person.getUId());
         }
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
      return p;
   }

   @Override
   public Person create( Person p )
   {
      Person person = new Person();
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("CREATE (n:Person {uid: ?, firstName:?, lastName: ?, email:?, created: ?, edited: ?}) return n");
         ps.setInt(1, p.getUId());
         ps.setString(2, p.getFirstName());
         ps.setString(3, p.getLastName());
         ps.setString(4, p.getEmail());
         ps.setLong(5, p.getCreated());
         ps.setLong(6, p.getEdited());
         ResultSet rs = ps.executeQuery();
         con.commit();
         while ( rs.next() )
         {
            JSONObject jsonObject = new JSONObject(rs.getString("n"));
            this.setPersonObject(person, jsonObject);
         }
      }
      catch ( SQLException e )
      {
         logger.error("Cannot create person with uid of " + p.getUId());
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error("cannot rollback the operation in creating user with uid of " + p.getUId());
         }
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
      return person;
   }

   @Override
   public void addAsColleague( Person p1, Person p2 )
   {
      try
      {
         con = DatabaseHelper.getInstance().getConnection();
         con.setAutoCommit( false );
         ps = con.prepareStatement("MATCH (a:Person {uid: ?}), (b: Person {uid: ?}) MERGE (a)-[r:Hamkar " + "{since: \"2019\"}]->(b)");
         ps.setInt( 1, p1.getUId() );
         ps.setInt( 2, p2.getUId() );
         ps.execute();
         con.commit();
      }
      catch ( SQLException e )
      {
         logger.error("Cannot make relationship between " + p1.getUId() + " and " + p2.getUId());
         try
         {
            con.rollback();
         }
         catch ( SQLException rollbackExe )
         {
            logger.error("cannot rollback the operation in adding relationship between " + p1.getUId() + " and " + p2.getUId());
         }
         throw new RuntimeException(e);
      } finally {
         DatabaseHelper.closeAll(con, ps, rs);
      }
   }

   private void setPersonObject (Person person, JSONObject jsonObject) {
      person.setUId( jsonObject.getInt("uid") );
      person.setFirstName( jsonObject.getString( "firstName" ) );
      person.setLastName( jsonObject.getString( "lastName" ) );
      person.setEmail( jsonObject.getString( "email" ) );
      person.setCreated( jsonObject.getLong( "created" ) );
      person.setEdited( jsonObject.getLong( "edited" ) );
   }
}

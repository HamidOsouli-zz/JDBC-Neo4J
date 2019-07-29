package neo.repositories;

import neo.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    @Query("MATCH (a:Person), (b:Person) WHERE ID(a) = {firstId}  and ID(b) = {lastId} CREATE (a)-[r:Colleague {since: \"since\"}]->(b)")
    void addAsColleague(@Param("firstId") long first, @Param("lastId") long last, @Param("since") Long since);
}
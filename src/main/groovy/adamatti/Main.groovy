package adamatti

import groovy.util.logging.Slf4j
import org.neo4j.driver.v1.AuthTokens
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.neo4j.driver.v1.Session
import org.neo4j.driver.v1.StatementResult

@Slf4j
class Main {
	public static void main(String [] args){
		log.info "Started"
		new Main().testList()
		log.info "Ended"
	}
	private void testList(){
		Driver driver = GraphDatabase.driver( "bolt://docker.me" ,AuthTokens.basic( "neo4j", "neo4j" ))
		Session session = driver.session()

		this.test(session)

		session.close()
		driver.close()
	}

	private void test(Session session){
		session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" )
		//StatementResult result = session.run("MATCH (u:User) RETURN u.name")
		StatementResult result = session.run("MATCH (u:Person) RETURN u.name")
		while (result.hasNext()) {
			log.info "A: " + result.next().dump()
		}
	}
}

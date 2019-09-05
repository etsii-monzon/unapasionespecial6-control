
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select avg(1.0*(select a.score from Audit a where a.position.id=p.id)) from Position p")
	Double avgAuditScoresPerPosition();

	@Query("select min(1.0*(select a.score from Audit a where a.position.id=p.id)) from Position p")
	Double minAuditScoresPerPosition();

	@Query("select max(1.0*(select a.score from Audit a where a.position.id=p.id)) from Position p")
	Double maxAuditScoresPerPosition();

	@Query("select stddev(1.0*(select a.score from Audit a where a.position.id=p.id)) from Position p")
	Double stdDevAuditScoresPerPosition();

	@Query("select avg(a.position.salary) from Audit a where a.score = (select max(1.0*(select a.score from Audit a where a.position.id=p.id)) from Position p)")
	Double avgSalaryFromHighestAuditScorePositions();

}

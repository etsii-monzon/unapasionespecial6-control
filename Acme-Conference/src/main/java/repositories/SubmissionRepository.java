
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Reviewer;
import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select avg(1.0*(select count(s) from Submission s where s.conference.id=c.id)) from Conference c")
	Double avgSubmissionsPerConference();

	@Query("select min(1.0*(select count(s) from Submission s where s.conference.id=c.id)) from Conference c")
	Integer minSubmissionsPerConference();

	@Query("select max(1.0*(select count(s) from Submission s where s.conference.id=c.id)) from Conference c")
	Integer maxSubmissionsPerConference();

	@Query("select stddev(1.0*(select count(s) from Submission s where s.conference.id=c.id)) from Conference c")
	Double stdDevSubmissionsPerConference();

	@Query("select s from Submission s where s.ticker = ?1")
	Submission checkTicker(String ticker);

	@Query("select s from Submission s join s.reviewers rw where rw = ?1")
	Collection<Submission> findSubmissionsOfReviewer(Reviewer r);

	@Query("select s from Submission s order by s.status")
	Collection<Submission> getSubmissionGroupedByStatus();
}

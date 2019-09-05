
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select a from Hacker a where a.userAccount.id = ?1")
	Hacker findByUserAccountId(int userAccountId);

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of applications per hacker.
	@Query("select avg(h.applications.size) from Hacker h")
	Double averageApplicationsPerHacker();

	@Query("select min(h.applications.size) from Hacker h")
	Integer minApplicationsPerHacker();

	@Query("select max(h.applications.size) from Hacker h")
	Integer maxApplicationsPerHacker();

	@Query("select stddev(h.applications.size) from Hacker h")
	Double stddevApplicationsPerHacker();

	//The hackers who have made more applications.
	@Query("select h from Hacker h where h.applications.size = (select max(h.applications.size) from Hacker h)")
	Collection<Hacker> findHackersMoreApplications();

}


package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("select avg(1.0*(select count(r) from Registration r where r.conference.id=c.id)) from Conference c")
	Double avgRegistrationsPerConference();

	@Query("select min(1.0*(select count(r) from Registration r where r.conference.id=c.id)) from Conference c")
	Integer minRegistrationsPerConference();

	@Query("select max(1.0*(select count(r) from Registration r where r.conference.id=c.id)) from Conference c")
	Integer maxRegistrationsPerConference();

	@Query("select stddev(1.0*(select count(r) from Registration r where r.conference.id=c.id)) from Conference c")
	Double stdDevRegistrationsPerConference();
}


package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	@Query("select c.welcomeSP from Configuration c")
	String findWelcomeSP();

	@Query("select c.welcomeEN from Configuration c")
	String findWelcomeEN();
}

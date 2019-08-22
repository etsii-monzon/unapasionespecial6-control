
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Wert;

@Repository
public interface WertRepository extends JpaRepository<Wert, Integer> {

	@Query("select w from Wert w where w.ticker = ?1")
	Wert checkTicker(String ticker);

}

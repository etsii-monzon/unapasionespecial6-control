
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Romp;

@Repository
public interface RompRepository extends JpaRepository<Romp, Integer> {

	@Query("select w from Romp w where w.ticker = ?1")
	Romp checkTicker(String ticker);

	@Query("select w from Conference c join c.romps w where w.draftMode = false AND c.id = ?1")
	Collection<Romp> getFinalRomps(int conferenceId);

	//Media y Std
	@Query("select avg(1.0*(select count(r) from c.romps r where r.draftMode=false)) from Conference c")
	Double avgRompsPerConference();

	@Query("select stddev(1.0*(select count(r) from c.romps r where r.draftMode=false)) from Conference c")
	Double stddevRompsPerConference();

	//XXX versus XXX

	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/count(p) from Romp p")
	Double draftVersusTotal();

	@Query("select sum(case when p.draftMode = 0 then 1.0 else 0.0 end)/count(p) from Romp p")
	Double finalVersusTotal();

}

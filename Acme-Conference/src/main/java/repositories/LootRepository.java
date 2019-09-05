
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Loot;

@Repository
public interface LootRepository extends JpaRepository<Loot, Integer> {

	@Query("select w from Loot w where w.ticker = ?1")
	Loot checkTicker(String ticker);

	@Query("select avg(1.0*(select count(r) from c.loots r where r.draftMode=false)) from Conference c")
	Double avgNumberLootsPerConference();

	@Query("select stddev(1.0*(select count(r) from c.loots r where r.draftMode=false)) from Conference c")
	Double stdNumberLootsPerConference();

	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/count(p) from Loot p")
	Double ratioLootUnpublished();
	@Query("select sum(case when p.draftMode = 0 then 1.0 else 0.0 end)/count(p) from Loot p")
	Double ratioLootPublished();

	//Media y Std
	//	@Query("select avg(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	//	Double avgConferencePerCategory();
	//
	//	@Query("select stddev(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	//	Double stdDevConferencePerCategory();

	//XXX versus XXX
	//	@Query("select sum(case when f.processions.size = 0 then 1.0 else 0.0 end)/sum(case when f.processions.size > 0 then 1.0 else 0.0 end) from Finder f")
	//	String dashboardQueryB3();
	//	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/sum(case when p.draftMode = 0 then 1.0 else 0.0 end) from Wert p")
	//	Double dashboardQueryAcmeParadeB4();

}

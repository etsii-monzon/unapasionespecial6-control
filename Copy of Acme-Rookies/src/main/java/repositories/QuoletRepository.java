
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select w from Quolet w where w.name = ?1")
	Quolet checkTicker(String ticker);

	@Query("select avg(1.0*(select count(r) from c.quolets r where r.draftMode=false)) from Audit c")
	Double avgNumberQuoletsPerAudit();

	@Query("select stddev(1.0*(select count(r) from c.quolets r where r.draftMode=false)) from Audit c")
	Double stdNumberQuoletsPerAudit();

	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/count(p) from Quolet p")
	Double ratioQuoletUnpublished();
	@Query("select sum(case when p.draftMode = 0 then 1.0 else 0.0 end)/count(p) from Quolet p")
	Double ratioQuoletPublished();

}

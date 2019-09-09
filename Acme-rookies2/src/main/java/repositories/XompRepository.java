
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Xomp;

@Repository
public interface XompRepository extends JpaRepository<Xomp, Integer> {

	@Query("select w from Xomp w where w.ticker = ?1")
	Xomp checkTicker(String ticker);

	@Query("select avg(1.0*(select count(r) from c.xomps r where r.draftMode=false)) from Position c")
	Double avgNumberXompsPerAudit();

	@Query("select stddev(1.0*(select count(r) from c.xomps r where r.draftMode=false)) from Position c")
	Double stdNumberXompsPerAudit();

	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/count(p) from Xomp p")
	Double ratioXompUnpublished();
	@Query("select sum(case when p.draftMode = 0 then 1.0 else 0.0 end)/count(p) from Xomp p")
	Double ratioXompPublished();

}

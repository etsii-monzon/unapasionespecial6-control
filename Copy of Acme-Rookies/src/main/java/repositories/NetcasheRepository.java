
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Netcashe;

@Repository
public interface NetcasheRepository extends JpaRepository<Netcashe, Integer> {

	//	@Query("select w from Netcashe w where w.ticker = ?1")
	//	Netcashe checkTicker(String ticker);

	@Query("select avg(1.0*(select count(r) from c.netcashes r where r.draftMode=false)) from Audit c")
	Double avgNumberNetcashesPerAudit();

	@Query("select stddev(1.0*(select count(r) from c.netcashes r where r.draftMode=false)) from Audit c")
	Double stdNumberNetcashesPerAudit();

	@Query("select sum(case when p.draftMode = 1 then 1.0 else 0.0 end)/count(p) from Netcashe p")
	Double ratioNetcashesUnpublished();
	@Query("select sum(case when p.draftMode = 0 then 1.0 else 0.0 end)/count(p) from Netcashe p")
	Double ratioNetcashesPublished();

}

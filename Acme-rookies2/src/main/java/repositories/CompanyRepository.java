
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;
import domain.Position;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select a from Company a where a.userAccount.id = ?1")
	Company findByUserAccountId(int userAccountId);

	@Query("select p from Company c join c.positions p")
	Collection<Position> findPosistionsOfCompany();

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	number of positions per company.
	@Query("select avg(c.positions.size) from Company c")
	Double averagePositionsPerCompany();

	@Query("select min(c.positions.size) from Company c")
	Integer minPositionsPerCompany();

	@Query("select max(c.positions.size) from Company c")
	Integer maxPositionsPerCompany();

	@Query("select stddev(c.positions.size) from Company c")
	Double stddevPositionsPerCompany();

	//The companies that have offered more positions.
	@Query("select c from Company c where c.positions.size = (select max(c.positions.size) from Company c)")
	Collection<Company> findCompaniesMorePositions();

	@Query("select c from Company c where c.commercialName like concat('%',?1,'%')")
	Collection<Company> searchCompanyByCommercialName(String keyword);

}

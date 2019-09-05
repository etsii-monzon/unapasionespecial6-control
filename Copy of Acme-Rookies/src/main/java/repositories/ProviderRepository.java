
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select a from Provider a where a.userAccount.id = ?1")
	Provider findByUserAccountId(int userAccountId);

	@Query("select min(p.items.size) from Provider p")
	Integer minItemsPerProvider();

	@Query("select max(p.items.size) from Provider p")
	Integer maxItemsPerProvider();

	@Query("select avg(p.items.size) from Provider p")
	Double avgItemsPerProvider();

	@Query("select stddev(p.items.size) from Provider p")
	Double stdDevItemsPerProvider();

	@Query("select p from Provider p order by p.items.size desc")
	Collection<Provider> findTopProviders();

}

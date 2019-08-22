
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.userAccount.id = ?1")
	Author findByUserAccountId(int userAccountId);

	@Query("select a from Author a join a.submissions sub where sub.id = ?1")
	Author findAuthorBySubmissionId(int id);
}

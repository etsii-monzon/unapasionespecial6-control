
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c  where (c.title like concat('%',?1,'%') or c.venue like concat('%',?1,'%')or c.summary like concat('%',?1,'%'))")
	Collection<Conference> searchConferenceByKeyword(String keyword);

	@Query("select avg(c.fee) from Conference c")
	Double avgFeesPerConference();

	@Query("select stddev(c.fee) from Conference c")
	Double stdDevFeesPerConference();

	@Query("select min(c.fee) from Conference c")
	Double minFeesPerConference();

	@Query("select max(c.fee) from Conference c")
	Double maxFeesPerConference();

	@Query("select c.endDate from Conference c")
	Collection<Date> findEndDateFromConferencs();

	@Query("select c.startDate from Conference c")
	Collection<Date> findStartDateFromConferencs();

	@Query("select c from Conference c  where c.category = ?1")
	Collection<Conference> getConferencesByCategory(Category category);

	@Query("select c from Conference c  where c.draftMode = false")
	Collection<Conference> getFinalConferences();

	@Query("select c from Conference c  where (c.title like concat('%',?1,'%') or c.venue like concat('%',?1,'%')or c.summary like concat('%',?1,'%')or c.acronym like concat('%',?1,'%'))")
	Collection<Conference> searchConferenceByKeyword2(String keyword);

	@Query("select c from Conference c  where c.fee < ?1")
	Collection<Conference> searchConferenceByMaxFee(double fee);

	@Query("select avg(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	Double avgConferencePerCategory();

	@Query("select min(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	Integer minConferencePerCategory();

	@Query("select max(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	Integer maxConferencePerCategory();

	@Query("select stddev(1.0*(select count(s) from Conference s where s.category.id=c.id)) from Category c")
	Double stdDevConferencePerCategory();

	@Query("select c from Conference c join c.romps w where w.id=?1 ")
	Conference getConferenceByRomp(int rompId);

}

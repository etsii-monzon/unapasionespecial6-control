
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	//	@Query("select p from Position p where (p.title like concat('%',?1,'%') or p.description like concat('%',?1,'%') or p.profile like concat('%',?1,'%') or p.skills like concat('%',?1,'%') or p.technologies like concat('%',?1,'%'))")
	//	Collection<Position> searchPositions(String keyword);

	@Query("select p from Position p join p.skills s join p.technologies t where (p.title like concat('%',?1,'%') or p.description like concat('%',?1,'%') or p.profile like concat('%',?1,'%') or s like concat('%',?1,'%') or t like concat('%',?1,'%'))")
	Collection<Position> searchPositions(String keyword);

	//	The average, the minimum, the maximum, and the standard deviation of the
	//	salaries offered.

	@Query("select avg(p.salary) from Position p")
	Double averageSalaries();

	@Query("select min(p.salary) from Position p")
	Double minSalary();

	@Query("select max(p.salary) from Position p")
	Double maxSalary();

	@Query("select stddev(p.salary) from Position p")
	Double stddevSalary();

	//The best and the worst position in terms of salary.

	@Query("select distinct p.title from Position p where p.salary = (select max(p.salary) from Position p)")
	String findBestPosition();

	@Query("select distinct p.title, p.salary from Position p where p.salary = (select min(p.salary) from Position p)")
	String findWorstPosition();

}

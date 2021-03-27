package site.netlex.domain;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubsectionRepository extends CrudRepository<Subsection, Long>{
	User findBySubsDbId(String subsDbId);
	@Query("SELECT s FROM Subsection s WHERE s.section = ?1")
	Collection <Subsection> findBySection(long secDbId);
	
}
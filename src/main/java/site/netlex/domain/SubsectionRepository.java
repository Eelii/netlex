package site.netlex.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubsectionRepository extends CrudRepository<Subsection, Long>{
	Subsection findBySubsDbId(Long subsDbId);
	
	@Query("SELECT s.subsDbId FROM Subsection s WHERE s.section.secDbId = ?1 and s.position = ?2")
	long findBySectionDbIdAndSubsPosition(long secDbId, int position);
	
}
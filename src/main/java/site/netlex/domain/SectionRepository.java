package site.netlex.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long>{
	Section findBySecDbId(long secDbId);
	@Query("SELECT s FROM Section s WHERE s.statute = ?1")
	List <Section> findByStatDbId(long statDbId);
	@Query("SELECT s.secDbId FROM Section s WHERE s.statute.statDbId = ?1 and s.identifier = ?2")
	long findSectionIdByIdentifierAndStatDbId(long statDbId, String identifier);
	
}
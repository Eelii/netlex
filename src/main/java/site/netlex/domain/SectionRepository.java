package site.netlex.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long>{
	Section findBySecDbId(Long secDbId);
	@Query("SELECT s FROM Section s WHERE s.statute = ?1")
	List <Section> findByStatDbId(Long statDbId);
	@Query("SELECT s.secDbId FROM Section s WHERE s.statute.statDbId = ?1 and s.identifier = ?2")
	Long findSectionIdByIdentifierAndStatDbId(Long statDbId, String identifier);
	
	
}
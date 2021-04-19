package site.netlex.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StatuteRepository extends CrudRepository<Statute, Long>{
	Statute findByStatDbId(Long statDbId);
	Statute findByStatuteId(String statuteId);
	
	//Assuming all statutes have unique statute id's. Prone to error. TODO?
	@Query("SELECT s.statDbId FROM Statute s WHERE s.statuteId = ?1")
	Long findDbIdByStatId(String statuteId);
}
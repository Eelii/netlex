package site.netlex.domain;

import org.springframework.data.repository.CrudRepository;

public interface StatuteRepository extends CrudRepository<Statute, Long>{
	Statute findByStatDbId(Long statDbId);
	Statute findByStatuteId(String statuteId);
	
}
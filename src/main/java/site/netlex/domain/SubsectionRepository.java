package site.netlex.domain;

import org.springframework.data.repository.CrudRepository;

public interface SubsectionRepository extends CrudRepository<Subsection, Long>{
	User findBySubsDbId(String subsDbId);
}
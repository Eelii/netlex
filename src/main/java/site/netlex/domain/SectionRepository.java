package site.netlex.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long>{
	User findBySecDbId(String secDbId);
	Section findByStatute(Long statDbId);
}
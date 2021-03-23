package site.netlex.domain;

import org.springframework.data.repository.CrudRepository;

public interface ParagraphRepository extends CrudRepository<Paragraph, Long>{
	User findByParDbId(String parDbId);
}
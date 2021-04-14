package site.netlex;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import site.netlex.domain.Paragraph;
import site.netlex.domain.ParagraphRepository;


@ExtendWith(SpringExtension.class)  // JUnit5 eli Jupiter
@DataJpaTest
public class ParagraphRepositoryTest {

	@Autowired
	ParagraphRepository paraRepo;
	
	@Test
	public void paragraphSaves() {
		
		Paragraph newPara1 = new Paragraph();
		newPara1.setPreamble(true);
		newPara1.setText("Tämä on momentin kohdan esipuhe");
		newPara1.setPosition(0);
		paraRepo.save(newPara1);
		Paragraph newPara2 = new Paragraph();
		newPara2.setPreamble(false);
		newPara2.setText("Tämä on momentin 1. kohta");
		newPara2.setPosition(1);
		
		paraRepo.save(newPara2);
		
		Iterable<Paragraph> paraIter = paraRepo.findAll();
		List<Paragraph> paraList = new ArrayList<>();
		for (Paragraph p: paraIter) {paraList.add(p);}
			
		assertThat(paraList.get(0).getPosition()).isEqualTo(0);
		assertThat(paraList.get(1).getPosition()).isEqualTo(1);
		
	}
	
}

package site.netlex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import site.netlex.domain.Section;
import site.netlex.domain.SectionRepository;

@ExtendWith(SpringExtension.class)  // JUnit5 eli Jupiter
@DataJpaTest
public class SectionRepositoryTest {
	
	@Autowired 
	SectionRepository secRepo;

	@Test
	public void sectionSaves() throws Exception {
		Section newSec = new Section();
		newSec.setHeading("Tietokannan testaaminen");
		newSec.setIdentifier("4b§");
		
	}
}
package site.netlex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import site.netlex.domain.Section;
import site.netlex.domain.SectionRepository;

@ExtendWith(SpringExtension.class)  // JUnit5 
@DataJpaTest
public class SectionRepositoryTest {
	
	@Autowired 
	SectionRepository secRepo;
	
	
	@Test
	public void sectionSaves() throws Exception {
		
		Section newSec = new Section();
		newSec.setHeading("Tietokannan testaaminen");
		newSec.setIdentifier("4b§");
		secRepo.save(newSec);
		
		//findAll()-method returns an iterable which has to be iterated to a list via a lambda expression.
		List<Section> foundSecs = new ArrayList<>();
	    secRepo.findAll().forEach(foundSecs::add);
	    
		assertThat(foundSecs).isNotNull();
		assertThat(foundSecs.get(0).getHeading()).isEqualTo("Tietokannan testaaminen");
		assertThat(foundSecs.get(0).getIdentifier()).isEqualTo("4b§");
		
	}
	
	@Test 
	public void sectionClassIsSection() throws Exception {
		Section newSec = new Section();
		newSec.setHeading("Tietokannan testaaminen");
		newSec.setIdentifier("4b§");
		secRepo.save(newSec);
		
		List<Section> foundSecs = new ArrayList<>();
	    secRepo.findAll().forEach(foundSecs::add);
	    
	    assertThat(foundSecs.get(0)).isInstanceOf(Section.class);
	}
}
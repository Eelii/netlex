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
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteRepository;

@ExtendWith(SpringExtension.class)  // JUnit5 eli Jupiter
@DataJpaTest
public class StatuteRepositoryTest {
	
	@Autowired 
	StatuteRepository statRepo;
	
	@Test
	public void statuteSaves() throws Exception {
		Statute newStat = new Statute();
		newStat.setNum(123);
		newStat.setYear(1984);
		newStat.setShortName("testaamisesta annetun lain muuttamisesta");
		newStat.setStatuteType("Asetus");
		newStat.setLanguage("suomi");
		statRepo.save(newStat);
		Statute statFound = statRepo.findByStatuteId(newStat.getStatuteId());
		assertThat(statFound).isNotNull();
		assertThat(statFound.getStatDbId()).isNotNull();
	}
	
	@Test
	public void statuteInits() throws Exception {
		Statute newStat = new Statute();
		newStat.setNum(123);
		newStat.setYear(1984);
		newStat.setShortName("testaamisesta annetun lain muuttamisesta");
		newStat.setStatuteType("Asetus");
		newStat.setLanguage("suomi");
		newStat.initStatute();
		assertThat(newStat.getFullName()).isEqualTo(newStat.getStatuteType()+" "+newStat.getShortName());
		
	}
}

package site.netlex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import site.netlex.domain.Section;
import site.netlex.domain.SectionRepository;
import site.netlex.domain.Statute;
import site.netlex.domain.StatuteRepository;
import site.netlex.domain.Subsection;
import site.netlex.domain.SubsectionRepository;

@SpringBootApplication
public class NetlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetlexApplication.class, args);
	}

	@Bean public CommandLineRunner demo(StatuteRepository statRepo, SectionRepository secRepo, SubsectionRepository subsecRepo) {
		return (args) ->{
			

			Statute testStatute = new Statute();
			testStatute.setYear(9999);
			testStatute.setNum(999);
			testStatute.setLanguage("suomi");
			testStatute.setStatuteType("Laki");
			testStatute.setShortName("eräiden testiasetuksen muuttamisasetuksesta annettujen lakien muuttamisesta");
			testStatute.initStatute();
			statRepo.save(testStatute);
			
			Section testSection = new Section();
			testSection.setHeading("Tietokantojen testaaminen");
			testSection.setIdentifier("99§");
			testSection.setStatute(testStatute);
			secRepo.save(testSection);
			List<Section> secList = new ArrayList<Section>();
			secList.add(testSection);
			testStatute.setSections(secList);
			
			
			
			Subsection testSubsection1 = new Subsection();
			testSubsection1.setSection(testSection);
			testSubsection1.setPosition(1);
			testSubsection1.setText("Tietokantoja on testattava asianmukaiset huolellisuusvaatimukset täyttäen sekä noudattaen, mitä "
					+ "tietokantatestaamisesta annetun lain 99 luvun 3 §:n 4, 7 ja 10 momentissa säädetään.");
			
			Subsection testSubsection2 = new Subsection();
			testSubsection2.setSection(testSection);
			testSubsection2.setPosition(2);
			testSubsection2.setText("Tietokantojen asianmukaisesta testaamisesta voidaan poiketa, mikäli tietokantaa käytetään yksinomaan "
					+ " kansan moraalin kehittämisestä annetun lain 6 §:n 5 momentin mukaisen varoittavan esimerkin esittämiseen.");
			
			subsecRepo.save(testSubsection1);
			subsecRepo.save(testSubsection2);
			
			List<Subsection> subsList = new ArrayList<Subsection>();
			subsList.add(testSubsection1);
			subsList.add(testSubsection2);
			testSection.setSubsections(subsList);
			
			System.out.println("STATDB IS: " + statRepo.findDbIdByStatId("999/9999"));
			System.out.println(secRepo.findSectionIdByIdentifierAndStatDbId(statRepo.findByStatuteId("999/9999").getStatDbId(), "99§"));
		};
	}
}

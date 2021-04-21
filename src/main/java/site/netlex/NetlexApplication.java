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
import site.netlex.domain.User;
import site.netlex.domain.UserRepository;

@SpringBootApplication
public class NetlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetlexApplication.class, args);
	}

	@Bean public CommandLineRunner demo(StatuteRepository statRepo, SectionRepository secRepo, SubsectionRepository subsecRepo, UserRepository userRepo) {
		return (args) ->{
			

			//An alternative test statute. Currently statutes and all their subparts are created within every test class.
			//Test classes will need new assertions if test statute(s) are to be created in command line runner.
			
			/*Statute testStatute = new Statute();
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
			
			User admin = new User("admin", "$2a$04$Az7Ii0qveCIyVHtGlFm5Mu2gEnEDHZIkqlamOFiIj1msFhwv1A31q", "ADMIN", "yllapito@valtio.fi", "Aada", "Admini", 0);
			userRepo.save(admin);*/
		};
	}
}

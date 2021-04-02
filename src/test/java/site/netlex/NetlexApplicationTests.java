package site.netlex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import site.netlex.web.SiteController;
import site.netlex.web.StatuteController;
import site.netlex.web.StatuteRestController;


@ExtendWith(SpringExtension.class)  // JUnit5 eli Jupiter
@SpringBootTest
class NetlexApplicationTests {

		
		@Autowired
		private StatuteController statute;
		
		@Autowired
		private StatuteRestController rest;
		
		@Autowired 
		private SiteController site;
		
		//Smoke test
		@Test
		public void contextLoads() throws Exception {
			assertThat(statute).isNotNull();
			assertThat(rest).isNotNull();
			assertThat(site).isNotNull();
		}

	}


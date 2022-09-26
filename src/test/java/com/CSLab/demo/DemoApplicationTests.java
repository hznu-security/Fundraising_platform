package com.CSLab.demo;

import org.CSLab.demo.DemoApplication;
import org.CSLab.demo.EnrollAdmin;
import org.CSLab.demo.RegisterUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= DemoApplication.class)
class DemoApplicationTests {

	@Test
	void contextLoads() throws Exception {
    EnrollAdmin e = new EnrollAdmin();
    e.enroll();
	 
    RegisterUser r = new RegisterUser();
    r.register();
     
	}

}

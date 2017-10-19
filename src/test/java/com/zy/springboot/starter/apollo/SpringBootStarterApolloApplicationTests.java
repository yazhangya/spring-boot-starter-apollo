package com.zy.springboot.starter.apollo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApolloStarterConfig.class})
public class SpringBootStarterApolloApplicationTests {


	@Value("${jdbc.driverClassName}")
	private String jdbcUrl;

	@Test
	public void contextLoads() {
		Assert.assertEquals(jdbcUrl,"com.mysql.jdbc.Driver");
	}

}

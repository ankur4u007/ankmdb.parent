package ankmdb.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ankmdb.client.service.IClientService;

public class AnkMDBClient {

	public static void main(final String[] args) {
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-client.xml");
		final IClientService cService = (IClientService) ctx.getBean("sp-clientService");
		cService.process();
	}

}

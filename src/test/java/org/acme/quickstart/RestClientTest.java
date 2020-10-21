package org.acme.quickstart;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;

/**
 * Runs against localhost:8080 (see HelloApi), so quarkusDev needs to be running.
 */
@QuarkusTest
class RestClientTest {
	@Inject @RestClient HelloApi helloService;
	
	@Test
	void canEcho() {
		Uni<String> response = helloService.greeting("foobar");
		
		System.out.println("Got " + response);
		response.subscribe()
			.with(System.out::println);
	}

}

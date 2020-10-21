package org.acme.quickstart;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@QuarkusTest
class RestClientTest {
	@Inject @RestClient HelloApi helloService;
	
	@Test
	void canConsumeSingle() throws InterruptedException, ExecutionException, TimeoutException {
		Uni<String> response = helloService.greeting("foobar");

		String out = response
			.subscribe()
			.asCompletionStage()
			.get(5, SECONDS);
		
		assertEquals("hello foobar", out);
	}

	@Test
	void canConsumeList() {
		Multi<String> response = helloService.greetings(7, "foobar");
		
		List<String> data = response
			.collectItems().asList()
			.await().atMost(Duration.ofSeconds(5));
		
		assertEquals(data, List.of("foo"));
	}
}

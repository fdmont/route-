package org.acme.quickstart;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

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

	@Test
	void canConsumeStream() {
		Multi<String> response = helloService.greetingsAsStream(7, "foobar");
		
		List<String> data = response
			.collectItems().asList()
			.await().atMost(Duration.ofSeconds(5));
		
		assertEquals(data, List.of("foo"));
	}

	@Test
	void canConsumeUsingRestEasyClient() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		
		List<String> items = new ArrayList<>();
		
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target("http://localhost:8081/hello/stream/3/foobar");
		try (SseEventSource eventSource = SseEventSource.target(target).build()) {
			eventSource.register(event -> {
				System.out.println("Got event " + event);
				String data = event.readData();
				items.add(data);
				
				// Without this (knowing when to terminate) the client
				// reconnects and starts another stream.
				if (data.contains("2")) {
					System.out.println("** Force termination");
					latch.countDown();
				}
				
			}, ex -> {
				fail("Streaming failed", ex);
			}, () -> {
				latch.countDown();
			});
			eventSource.open();
			
			// Wait for end of streaming
			boolean streamEndedBeforeTimeout = latch.await(5, TimeUnit.SECONDS);
			if (!streamEndedBeforeTimeout) {
				fail("Stream did not end");
			}
		}

		assertEquals(List.of("hello foobar - 0", "hello foobar - 1", "hello foobar - 2"), items);
	}
}

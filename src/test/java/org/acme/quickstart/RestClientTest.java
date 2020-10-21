package org.acme.quickstart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

/**
 * Runs against localhost:8080 (see HelloApi), so quarkusDev needs to be running.
 */
@QuarkusTest
class RestClientTest {
	@Inject @RestClient HelloApi helloService;
	
	@Test
	void canConsumeSingle() throws InterruptedException {
		Uni<String> response = helloService.greeting("foobar");

		CountDownLatch done = new CountDownLatch(1);
		AtomicReference<String> item = new AtomicReference<>();

		response
			.subscribe()
			.with(s -> {
				item.set(s);
				done.countDown();;
			});
		
		if (!done.await(5, TimeUnit.SECONDS)) {
			fail("Failed to receive data in time");
		}
		
		assertEquals("hello foobar", item.get());
	}

//	@Test
	void canConsumeList() {
		Multi<String> response = helloService.greetings(7, "foobar");
		
		response
			.subscribe()
			.with(
					msg -> System.out.println("Message " + msg),
					failure -> fail("List consumtion failed", failure));
	}

}

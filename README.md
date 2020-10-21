# Testing
	
Unit test, RestClientTest uses HelloApi and MP rest client to access resource ReactiveGreetingResource.

Works fine for Uni, but Multi fails for both list and stream variants:

```
javax.ws.rs.ProcessingException: java.lang.NullPointerException
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.filterRequest(ClientInvocation.java:696)
	at org.jboss.resteasy.microprofile.client.impl.MpClientInvocation.filterRequest(MpClientInvocation.java:75)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.invoke(ClientInvocation.java:485)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.invoke(ClientInvocation.java:65)
	at org.jboss.resteasy.plugins.providers.sse.client.SseEventSourceImpl$EventHandler.run(SseEventSourceImpl.java:332)
	at org.jboss.resteasy.plugins.providers.sse.client.SseEventSourceScheduler$1.run(SseEventSourceScheduler.java:92)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:304)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: java.lang.NullPointerException
	at org.jboss.resteasy.microprofile.client.utils.ClientRequestContextUtils.getMethod(ClientRequestContextUtils.java:25)
	at org.jboss.resteasy.microprofile.client.MethodInjectionFilter.filter(MethodInjectionFilter.java:15)
	at org.jboss.resteasy.client.jaxrs.internal.ClientInvocation.filterRequest(ClientInvocation.java:683)
	... 11 more
```

# Creation

Project created with
	
	mvn io.quarkus:quarkus-maven-plugin:1.8.3.Final:create     -DprojectGroupId=org.acme     -DprojectArtifactId=quarkus-rest-client-mutiny     -DclassName="org.acme.quickstart.ReactiveGreetingResource"     -Dpath="/hello"     -Dextensions="resteasy-mutiny, resteasy-jsonb" -DbuildTool=gradle

Adding code by following:

	https://quarkus.io/guides/getting-started-reactive

Add client:

	./gradlew addExtension --extensions quarkus-rest-client-mutiny
	


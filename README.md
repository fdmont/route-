Created with
	
	mvn io.quarkus:quarkus-maven-plugin:1.8.3.Final:create     -DprojectGroupId=org.acme     -DprojectArtifactId=quarkus-rest-client-mutiny     -DclassName="org.acme.quickstart.ReactiveGreetingResource"     -Dpath="/hello"     -Dextensions="resteasy-mutiny, resteasy-jsonb" -DbuildTool=gradle

Following

	https://quarkus.io/guides/getting-started-reactive

Add client

	./gradlew addExtension --extensions quarkus-rest-client-mutiny
	

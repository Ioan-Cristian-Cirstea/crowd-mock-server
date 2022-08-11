install:
	mvn install
	java -jar target/crowd-mock-server-0.0.1-SNAPSHOT.jar &

.PHONY:
clean:
	mvn clean package

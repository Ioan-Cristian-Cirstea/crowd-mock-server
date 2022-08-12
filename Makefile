install:
	mvn install
	java -jar target/crowd-mock-server-0.0.1-SNAPSHOT.jar

connect-to-db:
	psql --host=localhost --port=6000 --username=crowd --dbname=crowd

.PHONY:
clean:
	mvn clean package

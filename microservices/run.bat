cd common-lib
mvn clean install

cd discovery-server
mvn clean package
java -jar target\discovery-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..

cd config-server
mvn clean package
java -jar target\config-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..

cd api-gateway
mvn clean package
java -jar target\api-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=default

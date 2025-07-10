cd common-lib
mvn clean install -DskipTests
cd ..

cd discovery-server
mvn clean package -DskipTests
start "Discovery-Server" cmd /k java -jar target\discovery-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..

cd config-server
mvn clean package -DskipTests
start "Config-Server" cmd /k java -jar target\config-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..

cd api-gateway
mvn clean package -DskipTests
start "Api-Gateway" cmd /k java -jar target\api-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
cd ..

pause

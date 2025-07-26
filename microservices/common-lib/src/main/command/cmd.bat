:: cd discovery-server
:: mvn clean package
:: java -jar target\discovery-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default

:: cd ..
:: cd config-server
:: mvn clean package
:: java -jar target\config-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=default

:: cd ..
:: cd api-gateway
:: mvn clean package
:: java -jar target\api-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=default

:: cd ..
:: cd common-lib
:: mvn clean install


REM Set variables
set alias=servicename
set keystore=%alias%-keystore.p12
set keystore_password=changeit
set keystore_type=PKCS12
set cert_file=%alias%-keystore.pem
set truststore=%alias%-truststore.jks

REM Generate keypair
keytool -genkeypair -alias %alias% ^
 -keyalg RSA -keysize 2048 -validity 365 ^
 -storetype %keystore_type% ^
 -keystore %keystore% ^
 -storepass %keystore_password% ^
 -dname "CN=example.com, OU=IT, O=MyOrg, L=City, S=State, C=NP"

REM Export certificate
keytool -exportcert -rfc ^
 -keystore %keystore% ^
 -storetype %keystore_type% ^
 -alias %alias% ^
 -file %cert_file% ^
 -storepass %keystore_password%

REM Import certificate into truststore
echo yes | keytool -importcert ^
 -file %cert_file% ^
 -alias %alias% ^
 -keystore %truststore% ^
 -storepass %keystore_password%

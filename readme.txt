JVM Params
-Dhp.ws.log.dir=C:/logs/hp

Tomcat server 
	<Resource auth="Container" driverClassName="org.h2.Driver"	
						name="hpDS" type="javax.sql.DataSource" url="jdbc:h2:file:~/myh2" />
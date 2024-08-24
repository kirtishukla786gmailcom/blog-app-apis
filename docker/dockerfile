# Alpine Linux with OpenJDK JRE
FROM openjdk:17
#WORKDIR /dbt
# copy WAR into image
COPY docker/blog-app-apis-0.0.1-SNAPSHOT.jar /app.jar 
# run application with this command line with active profile
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=dev","/app.jar"]

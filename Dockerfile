# ===============================
#   BUILD STAGE (Maven + JDK17)
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy only pom first to cache dependencies
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the source code
COPY src ./src

# Build jar
RUN mvn -B -DskipTests package


# ===============================
#   RUNTIME STAGE (Corretto)
# ===============================
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# For security: non-root user
RUN addgroup -S appgroup \
    && adduser -S appuser -G appgroup
USER appuser

# Copy the JAR built in previous stage
COPY --from=build /workspace/target/*.jar app.jar

# Render injects $PORT; Spring must use it
ENV SERVER_PORT=8080

EXPOSE 8080

# Java options are optional
ENV JAVA_OPTS="-Xms256m -Xmx512m"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]

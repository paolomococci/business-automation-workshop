# sample-planning

## scaffolding

```text
mvn io.quarkus.platform:quarkus-maven-plugin:2.4.0.Final:create \
    -DprojectGroupId=local.mocaccino \
    -DprojectArtifactId=sample-planning \
    -DprojectVersion=0.0.1-SNAPSHOT \
    -DclassName="local.mocaccino.planning.SamplePlanning" \
    -Dextensions="resteasy,resteasy-jackson,optaplanner-quarkus,optaplanner-quarkus-jackson" \
    -DbuildTool=gradle
```

## to start the application in development mode

```shell
./gradlew quarkusDev
```
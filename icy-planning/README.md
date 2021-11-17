# icy-planning

## scaffolding

```shell
mvn io.quarkus.platform:quarkus-maven-plugin:2.4.2.Final:create -DprojectGroupId=local.mocaccino -DprojectArtifactId=icy-planning -DprojectVersion=0.0.1-SNAPSHOT -DclassName="local.mocaccino.planning.IcyPlanning" -Dextensions="resteasy,resteasy-jackson,optaplanner-quarkus,optaplanner-quarkus-jackson" -DbuildTool=gradle
```

## I adding some dependencies

```shell
./gradlew addExtension --extensions="quarkus-jdbc-h2,quarkus-hibernate-orm-panache,quarkus-hibernate-orm-rest-data-panache"
```

## to start the application in development mode

```shell
./gradlew quarkusDev
```

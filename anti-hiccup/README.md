# anti-hiccup

How to get rid of hiccup planning and live happily in a world where components and finished products flow efficiently.

---

## scaffolding:

```shell
mvn io.quarkus.platform:quarkus-maven-plugin:2.5.1.Final:create -DprojectGroupId=local.mocaccino -DprojectArtifactId=anti-hiccup -DprojectVersion=0.0.1-SNAPSHOT -DclassName="local.mocaccino.planning.AntiHiccup" -Dextensions="resteasy,resteasy-jackson,optaplanner-quarkus,optaplanner-quarkus-jackson"
```

After that, I have added what is needed to make the UI offered by Vaadin work correctly, in particular the EnableWebsockets class.

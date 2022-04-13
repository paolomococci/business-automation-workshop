# sample-planning

## scaffolding

```shell
mvn io.quarkus.platform:quarkus-maven-plugin:2.8.0.Final:create \
    -DprojectGroupId=local.mocaccino \
    -DprojectArtifactId=sample-planning \
    -DprojectVersion=0.0.1-SNAPSHOT \
    -DclassName="local.mocaccino.planning.SamplePlanning" \
    -Dextensions="resteasy,resteasy-jackson,optaplanner-quarkus,optaplanner-quarkus-jackson" \
    -DbuildTool=gradle
```

## I adding some dependencies

```shell
./gradlew addExtension --extensions="quarkus-jdbc-h2,quarkus-hibernate-orm-panache,quarkus-hibernate-orm-rest-data-panache"
```

## to start the application in development mode

```shell
./gradlew quarkusDev
```

## REST resources

```text

sample

    GET sample
        Produces: text/plain

auditoriums

    GET auditoriums
        Produces: application/json
    POST auditoriums
        Consumes: application/json
        Produces: application/json
    DELETE auditoriums/{id}
    GET auditoriums/{id}
        Produces: application/json
    PUT auditoriums/{id}
        Consumes: application/json
        Produces: application/json

billboard

    GET billboard
        Consumes: application/json
        Produces: application/json
    POST billboard/start
        Consumes: application/json
        Produces: application/json
    POST billboard/stop
        Consumes: application/json
        Produces: application/json

lectures

    GET lectures
        Produces: application/json
    POST lectures
        Consumes: application/json
        Produces: application/json
    DELETE lectures/{id}
    GET lectures/{id}
        Produces: application/json
    PUT lectures/{id}
        Consumes: application/json
        Produces: application/json

timeslots

    GET timeslots
        Produces: application/json
    POST timeslots
        Consumes: application/json
        Produces: application/json
    DELETE timeslots/{id}
    GET timeslots/{id}
        Produces: application/json
    PUT timeslots/{id}
        Consumes: application/json
        Produces: application/json


```

## persistence unit

```text
create sequence hibernate_sequence start with 1 increment by 1;

    create table AUDITORIUM (
       id bigint not null,
        label varchar(255),
        primary key (id)
    );

    create table LECTURE (
       id bigint not null,
        audience varchar(255),
        lecturer varchar(255),
        topic varchar(255),
        auditorium_id bigint,
        timeslot_id bigint,
        primary key (id)
    );

    create table TIMESLOT (
       id bigint not null,
        dayOfWeek integer,
        localTimeStart time,
        localTimeStop time,
        primary key (id)
    );

    alter table LECTURE 
       add constraint FKh1oyowty83akc1me1n3ngjh8g 
       foreign key (auditorium_id) 
       references AUDITORIUM;

    alter table LECTURE 
       add constraint FK16s9cr10puw80189u5lbtlfvv 
       foreign key (timeslot_id) 
       references TIMESLOT;

```

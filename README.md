# vue-spring-boot-mongo-template

This is a simple template project designed for those looking for 
a configured backend and frontend using `Vue 3` and `Spring Boot 3`.
The project consists of two modules: one for the frontend and one for the backend.
Authorization is implemented using simple `JWT` and is configured on both sides.
Additionally, it utilizes `MongoDB`.
I am using this project for quick prototyping and personal projects.
Please note that the configuration may not be optimal and could contain issues or typos.

# Frontend

## Usage:

Install Dependencies:

```
./gradlew yarnInstall
```

Build Vue App:

```
./gradlew vueBuild
```

Run Vue App:

```
./gradlew startVueServer
```

Stop Vue App:

```
./gradlew stopVueServer
```

Clean Build Output:

```
./gradlew cleanVueBuild
```

# Backend

```
./gradlew clean :backend:bootRun --args='--spring.profiles.active=local'

```
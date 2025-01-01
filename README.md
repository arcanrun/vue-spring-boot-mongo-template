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
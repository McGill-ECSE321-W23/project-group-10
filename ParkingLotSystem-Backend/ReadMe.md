# Parking Lot System Backend

> A gradle project

## Build Setup

``` bash

# ensure having the newest version of Gradle installed
gradle --version

# run the tests
./gradlew test

# build backend
./gradlew build

# run the integration tests
./gradlew clean test

# check enforce a certain treshold on the test cases with this Jacoco plugin
./gradlew jacocoTestCoverageVerification

```

For detailed explanation on how things work, checkout the [docs for gradle](https://docs.gradle.org/current/userguide/command_line_interface.html).

## System initialization
If the backend is run for the first time, the manager account and the parking lot system settings need to be setup. Send the following API requests to do so:

1. POST http://localhost:8080/api/manager/manager@pls.com?name=defaultName&phone=1231231234&password=pass12345
2. POST http://localhost:8080/api/parking-lot-system/0?openTime=10:00:00&closeTime=12:00:00

These settings (manager name, manager phone, etc.) can be changed at any time.

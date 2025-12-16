plugins {
    id("browserbase.kotlin")
    application
}

dependencies {
    implementation(project(":browserbase-kotlin"))
}

application {
    // Use `./gradlew :browserbase-kotlin-example:run` to run `Main`
    // Use `./gradlew :browserbase-kotlin-example:run -Pexample=Something` to run `SomethingExample`
    mainClass = "com.stagehand.api.example.${
        if (project.hasProperty("example"))
            "${project.property("example")}ExampleKt"
        else
            "MainKt"
    }"
}

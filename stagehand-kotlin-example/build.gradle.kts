plugins {
    id("stagehand.kotlin")
    application
}

dependencies {
    implementation(project(":stagehand-kotlin-core"))
    implementation(project(":stagehand-kotlin-client-okhttp"))
    implementation("com.microsoft.playwright:playwright:1.43.0")
}

application {
    // Use `./gradlew :stagehand-kotlin-example:run` to run `Main`
    // Use `./gradlew :stagehand-kotlin-example:run -Pexample=Something` to run `SomethingExample`
    mainClass = "com.browserbase.api.example.${
        if (project.hasProperty("example"))
            "${project.property("example")}ExampleKt"
        else
            "MainKt"
    }"
}

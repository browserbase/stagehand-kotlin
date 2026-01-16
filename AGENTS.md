# Overview

This is a stateless SDK client for the Stagehand API provided by Browserbase.com, built using Stainless.

The Stagehand API allows users to control Browserbase cloud browsers using a natural language interface with these high-level primitives:

- `act("do xyz on this page")` - Perform actions on the page
- `observe("look for xyz elements on this page")` - Find interactive elements
- `extract("find xyz information on this page")` - Extract structured data from pages

The other calls provided are `start()` and `end()` to begin and end a browser session, and `navigate()` which is a helper to visit a specific URL.

These primitives are intended to be combined with your browser driver library of choice, e.g. Selenium, Playwright for Java/Kotlin, etc.

**Links:**
- GitHub: https://github.com/browserbase/stagehand-kotlin
- Documentation: https://docs.stagehand.dev/v3/sdk/kotlin
- Maven Central: `com.browserbase.api:stagehand-kotlin`

## Usage

Refer to the README.md "# Usage" section and `./examples` directory for detailed usage examples.

For installation instructions, see the "# Installation" section of the README.

For running examples, see the "## Running the Example" section of the README.

## Common Tasks

```bash
# Set environment variables
export BROWSERBASE_API_KEY="your-bb-api-key"
export BROWSERBASE_PROJECT_ID="your-bb-project-uuid"
export MODEL_API_KEY="sk-proj-your-llm-api-key"

# Run the example
./gradlew run
```

```kotlin
// Quick start
val client: StagehandClient = StagehandOkHttpClient.fromEnv()
val startResponse = client.sessions().start(
    SessionStartParams.builder().modelName("openai/gpt-4o").build()
)
val sessionId = startResponse.data.sessionId
client.sessions().navigate(SessionNavigateParams.builder()
    .id(sessionId).url("https://example.com").build())
client.sessions().act(SessionActParams.builder()
    .id(sessionId).input("click login").build())
client.sessions().end(SessionEndParams.builder().id(sessionId).build())
```

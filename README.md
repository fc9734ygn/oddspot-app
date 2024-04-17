# Oddspot Frontend

Oddspot is a Kotlin (Android + iOS + Backend) project that aims to provide a platform for sharing
and discovering interesting spots around the world that aren't commercial establishments. The
project is still in its early stages of development and has not been released yet. It is intended to
become a commercial project in the future (if it works out) but also serves as a learning/portfolio
project.

## Features (as of right now):

- User registration and login
- Spot discovery (map), submission, details, wishlist, etc.
- Tutorial
- User profile and settings

## Architecture Overview:

The project is built with Kotlin Multiplatform Mobile (KMM) and follows the MVVM (with a taste of
MVI, I suppose) architecture pattern that's very common in modern Android development. The UI is
built for both Android and iOS with Compose Multiplatform, with just a few components built
independently for each platform (e.g., Google Maps integration).

## Technologies and Frameworks (and opinions):

### Compose Multiplatform

- Allows me to build the UI for iOS in Kotlin.

### Ktor (client)

- Allows me to use the same network client library for frontend and backend.

### SQLDelight

- Allows me to use the same database library (reverse ORM, for lack of a better word) for frontend
  and backend.

### Koin

- In my opinion, the best DI library for Kotlin projects. Supports pretty much everything Kotlin
  does.

### KSP

- Allows me to use Koin annotations.

### Voyager

- Simplifies navigation; haven't found any issues with it yet.

### Kermit

- Simple and effective logging library. Can't remember why I chose it over Napier and others.

### Kotlin-result by Michael Bull

- My favorite Result/Either Monad implementation for Kotlin.
- Allows me to handle my errors explicitly and transform the exceptions into domain errors **when
  needed**.
- I try to follow the "exceptions are for exceptional cases" philosophy but without wrapping every
  Throwable into a domain error without any reason.

#### Alternatives:

- Arrow - too heavy for me.
- Result from Kotlin stdlib - doesn't allow sealed classes for error types, which is the main reason
  I use Result type.
- Other Result libraries - I prefer Michael Bull's library syntax.

Interesting read: [The Result Monad](https://adambennett.dev/2020/05/the-result-monad/)

### Other Small Libs for UI, etc.

- When it comes to UI code, I don't want to spend too much time on it.
- I use small libraries that do the job and don't require me to write too much code.
- As it is my own project, I can usually adjust the designs to fit the libraries I use.

## Tests:

I haven't written any tests for the frontend as the features and designs have been changing a lot.
Usually, in Android projects, I prioritize unit tests for the domain layer (sometimes for ViewModels
as well) and E2E or integration tests (JUnit 4 or 5, Compose Rule/Espresso, MockK).

## Future Enhancements:

The project is still in its early stages, and there are a lot of things that need to be done.
Unfortunately, the task board is private.

## How to Run:

1. Deploy/run your backend.
2. Add `const val API_BASE_URL = "your-api-url"` somewhere in your commonMain directory.
3. Add `secrets.properties` to the project's root directory with the
   content `MAPS_API_KEY=your-google-maps-api-key`.
4. Add `MAPS_API_KEY=DEFAULT_API_KEY` line to your `local.properties` file (project root directory).
5. Build the app for Android or iOS following regular KMP project build procedures.

Last update: 2024-04-17

# Star Wars demo app 

## Features
* Clean Architecture with MVVM 
* Kotlin Coroutines with Flow
* Dagger Hilt



Hi there 👋🏼👋🏼👋🏼
Thanks for checking out my project. For the rest of this document, I will be explaining the reasons for the technical decisions I made for this case study, the problems I faced, and what I learnt from them.

## Table of content

- [Prerequisite](#prerequisite)
- [Design](#Design)
- [Architecture](#architecture)
- [Testing](#testing)


## Prerequisite
To build this project, you require:
- Android Studio 4.2 canary 7 or higher
- Gradle 6.5 or higher 

<h2 align="left">Screenshots</h2>
<p align="center">
  <img src="https://github.com/Hoossayn/StartwarsDemo/assets/35276272/c8b7a0e3-95ed-4b1e-97a7-2cb621541455" width="200">

</p>

## Design
Before taking any coding and architecture decisions, I first had to come up with an idea of how I wanted the app to look, and the kind of experience I wanted users to have when using the app. This also guided my decisions on what architecture and tools were best suited to bring about a good user experience. In addition to having a search screen and a detail screen, I think it'd be nice to have a search history screen where users can revisit any previously viewed character without having to search and wait for results.
As seen in the images of the app [above](#Design), the app launches into the search screen where the user can either see their search history.

Typing text into the search bar transitions the user into a `searching state` which leads to either a loaded search results state, empty or error state.

Seeing that the app requires a lot of state handling, I opted to use the `Model-View-ViewModel` architecture for the presentation layer of the app. This will be discussed in-depth in the section below.

## Architecture

The application follows clean architecture because of the benefits it brings to software which includes scalability, maintainability and testability.
It enforces separation of concerns and dependency inversion, where higher and lower level layers all depend on abstractions. In the project, the layers are separated into different Folders namely:

- Domain
- Data
- Ui
- Di

The cache and remote layers are implementation details that can be provided in any form (Firebase, GraphQl server, REST, ROOM, SQLDelight, etc) as long as it conforms to the business rules / contracts defined in the data layer which in turn also conforms to contracts defined in domain.

For dependency injection and asynchronous programming, the project uses Dagger Hilt and Coroutines with Flow. Dagger Hilt is a fine abstraction over the vanilla dagger boilerplate, and is easy to setup. Coroutines and Flow brings kotlin's expressibility and conciseness to asynchronous programming, along with a fine suite of operators that make it a robust solution.

#### Presentation
As stated earlier, the presentation layer is implemented with MVVM architecture. The project has a kotlin folder called `ui` which defines the contract that presenters should implement. The layer is also platform agnostic, making it easy to change implementation details (ViewModel, etc).



#### Ui
For each screen, we used Kotlin flow to provides a powerful way to manage state updates and render them in your UI. Here's a breakdown of how it works
 1. Define the state :
    - Use StateFlow<T> to represent the current state of your UI.
    - T can be any data class holding your UI data.
    - StateFlow ensures the value is always available and updates are delivered efficiently.
 2.  Update State in ViewModel:
     - The ViewModel holds the business logic and manages the state.
     - It exposes the StateFlow for the UI to observe. 
     - In response to user actions or data changes, the ViewModel updates the state using functions like:
       - Launching coroutines to fetch data.
       - Transforming data based on user interaction.
       - Emitting a new state object to the StateFlow.

3. Render State in UI:
    - The UI component (Fragment, Activity) observes the StateFlow from the ViewModel.
    - It uses a lifecycle-aware collector (e.g., lifecycleScope.launchWhenStarted) to listen for state changes.
    - Whenever a new state is emitted, the collector calls a rendering function.
    - This rendering function takes the new state as an argument and updates the UI components accordingly.


The detail screen is a bit more complex requiring 4 view components to render each detail - `PopulationtView`, `FilmView`, `SpeciesView`, and `ProfileView`. These views encapsulate logic for rendering success, error and empty states for the corresponding detail. The data for the views are fetched concurrently, allowing any of the views to render whenever its data is available. It also allows the user to retry the data fetch for each individual component if it fails. The state for each of view is decoupled from one another and is cached in a Flow persisted in the Fragment's Viewmodel.

#### Domain
The domain layer contains the app business logic. It defines contracts for data operations and domain models to be used in the app. All other layers have their own representation of these domain models, and Mapper classes (or adapters) are used to transform the domain models to each layer's domain model representation.
Usecases which represent a single unit of business logic are also defined in the domain layer, and are consumed by the presentation layer.
Writing mappers and models can take a lot of effort and result in boilerplate, but they make the codebase much more maintainable and robust by separating concerns.

#### Data
The Data layer implements the contract for providing data defined in the domain layer, and it in turn provides a contract that will be used to fetch data from the remote and cache data sources.
We have two data sources - `Remote` and `Cache`. Remote relies on Retrofit library to fetch data from the swapi.dev REST api, while the cache layer will use Room library to persist the search history.

## Testing
Testing is done with Junit4 testing framework, and with Google Truth for making it easier to verify interactions between objects and their dependencies, and simulate the behavior of the real objects.
Each layer has its own tests. The remote layer makes use of Mockwebserver to test the api requests and verify that mock Json making assertions. The test uses fake objects for all tests instead of mocks, responses provided in the test resource folder are returned.
The data source layer includes test for the `StarWarsDataSourceImpl` this ensures that the overridden methods returns expected results
The repository layer includes test for the `StarWarsRepositoryImpl` this also ensures that the proper status are being emitted. 

The mappers and extension methods are also being tested.

The presentation layer is extensively unit-tested to ensure that the correct view state is produced when an intent is processed. The intent processors are tested to ensure that they produce the right results for each intent. The view state reducer tests then verify that the correct view state is computed from those results. Viewmodel tests verify that each call to process an Intent produces the correct view state. It looks trivial since it is similar to the reducer test but it is still very important.

The case study can do with more UI tests that verify that the view state is rendered as expected. However, the extensive unit and integration test coverage ensures that the app works as expected.


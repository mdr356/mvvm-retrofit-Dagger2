Amiibo App

- Dagger 2 is one of the open-source DI frameworks which generates a lot of boilerplate for us. Therefore that is being used in the project.
- retrofit, A type-safe HTTP client for Android, is used for our HTTP Get calls.
- Project is build using MVVM Architecture
- there is a base activity that deals with the dialog and showing of error, as that is common throughout the application.
- Every view has a ViewModel, which is being injected into the view
- The main view ViewModel has a repository that deals with getting data from the backend if the SQL database does not have the data stored already.
- When a user is on the MainActivity, they can filter the Amiibo that they have purchased.
- Once users navigate, they have the capability to view more details about the Amiibo; those details are stored in an SQL database.
- They have the option to purchase an Amiibo or delete it from the database.

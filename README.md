# Android Coding Challenge Raheel Masood

#The Brief
A simple app starts with an Edit Text field and Shorten It button.
Once a valid URL is added, the url is added to the list of history given that the URL is valid.
A user may delete a url from the list of history.
The app uses local DB to maintain history of urls.
A url can be copied to the clipBoard

#Architecture and design.
Single Activity Architecture using:
1) MVVM + Clean
2) DI Using HILT.
3) Coroutine with flow
4) 2 way dataBinding.
5) Unit test of repository and view model included.
6) Navigation graph.
7) Error Handling, in case of no internet or error from network. Once the internet resumes, user can hit the request again.
8) Room Database to save urls in history. (Module is added)

#General Logic for the code
Steps...
Views will call view model.
View model is injected with use case.
Use case makes a call to repository.
Repository returns the data to the view model.
View model returns the response to the view I.e fragment.
Local DB is used to maintain history

#Test:
#Unit Tests are included in test folder:
FetchShortenURLTest: This will use mock responses to fetch a given URL.
MainViewModelTest: We are check Channel's events.

#AndroidTest:
EntityRoomDBTest: Simple CRUD operations are tested in this folder. 
To run these test a device are required, since no Activity is attached, It will run quick.
# Monitoring #

This repository contains the basic code and folder structure for company projects.

Uses:
- Kotlin as main language
- Koin as DI
- Android Architecture Components 

Project structure contains 3 main layers:
- presentation
- domain
- data

### 1. Presentation ###
Contains such folders as:
- activity - _all project activities belong here_
- fragments - _all project fragments belong here_
- adapter - _all project adapters (for RecyclerView etc.) belong here_
- base - _all base classes which others inherit from_ 
- custom_view - _all custom views which should exist belong here_

Activities inherit AppCompatActivity which can work with architecture components because of implementation LifecycleOwner.  
Fragments inherit Fragment from support lib because of the same reason.  

### 2. Domain ###
Contains ViewModels for architecture components and ... _todo ? makeServices_

**BaseViewModel** inherit **AndroidViewModel** from architecture components.  
We use **AndroidViewModel** instead of regular **ViewModel** because of possibility have a link on application (context including).  
ViewModels have to not have link on activity/fragment because of leaks.

### 3. Data ###
Contains such folders as:
- database - all classes for working with database
  - dao - all Data Access Objects
  - entity - all entities for database and app
- server - all classes for communicating with server
  - interceptor - interceptors for retrofit
  - pojo - plain java objects for server requests and responses
  
## Android Architecture Components ##
### Model ###
Realization of this layer is in **data** folder

We have to create repository for every type of logic.
In it we decide where get the data. It communicate with database and server.

Server layer just communicate with API and don't know about other logic.
Same is for database.

All entities should have an annotation `@Entity`  

All DAO classes should have an annotation `@Dao`  
In DAO classes we use CRUD. All queries we need to write by ourselves.
Other operation doesn't demand it.

We need create main Database class which should have an annotation `@Database` and list of entities.
inside body we make getters for all DAO classes.
  
After initialization Database we can call DAO and methods inside like `db.getExampleDAO().getAll()`
If our UI needs the live objects we need wrap the results in **LiveData**

It doesn't contains links on outside classes.

### ViewModel ###

Main interactor between UI and app logic.
It observe the view lifecycle and don't loose any data after some changes occur with view.

This layer doesn't know about server and database. It communicates only with repository instance.
And provides access for data.

It doesn't contains links on any view because of leaks.

### View ###
App uses all components from google library. It leads to MVVM architecture.  
Activities or Fragments have to inherit LifecycleOwner which already realized in AppCompatActivity and Fragment from support library.  
After creating, we need to get a ViewModel instance.
After that we can call methods from it.
If we use LiveData objects we can observe all changes and set them immediately.

It contains link only on own ViewModel.

## DI ##

As base DI approach we use Koin.
It's use all advantages of DSL. Also takes less code and therefore it simpler to initialize.

We have separate folder for it.
Inside we specify all modules which we will use in the project.
Basically we have module for all repository dependency and another one for ViewModels.

Initializing occurs in class which inherits from Application where we just set the list of modules.

We use injection into constructor, but also can use `inject<>`

For ViewModels it has additional extension therefore we use just `getViewModel()` in any view class.

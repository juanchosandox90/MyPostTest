MyPostTestApp 
=

Sample project to show how a sample/test app can have some clean architecture.

Main benefits:
- Easy to understand some concepts from Clean Architecture.
- Applies & follows design principles (separation of concerns for example).
- Layer separation by modules (not packages) to set clear boundaries between each layer.
- Easy to read, scale, test and maintain.

Some extra notes:
- Simplified Clean Architecture approach.

Quick overview of the app
-

Using the API endpoints
- GET http://jsonplaceholder.typicode.com/posts
- GET http://jsonplaceholder.typicode.com/users
- GET http://jsonplaceholder.typicode.com/comments

Create a simple Android app with three screens:
- Screen1: Post list screen that contains:
    - User avatar, name and username of each post.
    - Post title and ellipsized body.

Taping a cell should take you to Screen2.

- Screen2: Post Detail screen that contains:
    - User avatar, name and username of this post.
    - Post title and full post body.
    - Number of comments this post has.
    - List of comments under this post, displaying the avatar and name of the user who has written this comment and the body of this comment.

Taping the user avatar from Screen1 or Screen2 (selected user) should take you to Screen3.

- Screen3: User Detail screen that contains:
    - User related information (name, username, email, phone, address, website and company.
    - Map showing the location of this user.
    
Domain Layer
=

Notes regarding the Domain layer
-

- Domain layer shouldn't include presentation and data layers.
- Domain layer will have all of your business logic.
- Domain layer will have use cases/interactors. These are in charge of retrieving data from 1 or multiple data sources (repositories/gateways).
- Domain layer could be improved by adding request objects and or policies instead of having cache/remote methods in its interface.
This is to avoid and reduce the abstract leaking of information caused by the contract of the repositories that sit in the domain layer.
- Caching and Remote loading of information from use cases can have different approaches and there is no right or wrong.
You just need to investigate around the pros and cons of each one and decide one based on the current requirements and the near future of the project.
This means you could have for example a unique source of truth in the data layer with some logic around the data management and would still be a valid approach.

Data Layer
=

Notes regarding the Data layer
-

- Data layer only has access to the domain layer. This is done by adding to gradle: `implementation project(':domain')`
- Data layer is responsible to save/load from/to disk/cloud.
- Data layer is responsible to have the repositories implementation.
These implementations will implement an interface (contract) from the domain layer and it will be the only way to
pull or push data into the data layer.
- Data layer is responsible to map entities to domain models when pulling something from the repositories.
- Data layer is responsible to map domain models to entities when pushing something to the repositories.
- Data layer could be improved by extracting each data source into different modules with different models.
This is to avoid sharing annotations/information from different data sources (for ex. cache models would not have Moshi annotations since these are only required for remote models).

Presentation Layer
=

Notes regarding the Presentation layer
-

- Presentation layer includes both data and domain layers.  This is done by adding to gradle: `implementation project(':domain')` and `implementation project(':data')`
- Access to data layer is only required due to the setup of Retrofit.
- Access to the domain layer is required in order to execute use cases from the presenter.
This is could be improved but it is currently done like this for simplicity.
- Presentation layer is responsible to display information in the screen and to forward user events.
I'm currently using MVVM (From Android Architecture Components) for the presentation layer but this could be easily switched to MVP or MVI.
- Presentation layer is responsible to map domain models to presentation models when pulling something from the use cases (domain).
- Presentation layer is responsible to map presentation models to domain models when pushing something to the use cases (domain).


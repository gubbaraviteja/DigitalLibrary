# DigitalLibrary
It provides REST API for basic library activities. For the POC sake, application uses in-memory derby database and thus all the data will be lost once the application is restarted. The REST  API will be available on `http://localhost:9004/`

# Building the project
To do the build, run the command under project's root directory: `mvn clean package`

# Running the application
The default port for the application is configured as 9004 in `application.properties`.
Run the command to start the application `java -jar target/libraryApi-0.0.1.jar`

# API Documentation
 Swagger is used along with springfox libraries to generate documentation. Once the application is started the documentation will be available on `http://localhost:9004/swagger-ui.html#/`
 
 # Use cases thought for developing the API
  * Adding single resource or multiple resources in bulk
  A resource can be anything that library can offer to the users. We are restricting it to BOOK, MUSIC, JOURNAL and MOVIE for this application. A resource can have details like Title, Author, PublishedDate, WaitingList, etc.
  * Finding a resource by name
  * Suggesting a resource to Library
    If the resource is not present in the library, the user can suggest it so that Library adminstration can use these kind of requests for better planning. 
  * Adding a resource to wishlist
    The user can also add any resource to his wishlist and gets notifications in scenarios like if its added newly to library or it become available for borrowing as its waitlist gets decreased.
  * Retrieving the library catalogue with all the resources
  * Adding single user or multiple users in bulk
  * Retrieving of all users

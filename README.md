# Internet of Things (IoT) - PoC #
An Internet of Things (IoT) is a concept that just about anything can be enhanced
by making any physical product connected to the internet.

In this example, the product is a refrigerator that is capable of keeping track of 
the items stored inside of it.  The way this product will be enhanced is by 
making the list of items stored in a web server (cloud) so that the items can be 
modified by any web connected application such as a website or mobile application.

This sample implementation contains the following components:

* [Web Server](web) - This is responsible for managing the state for all applications.
* [Executable](exec) - This is the implementation the refrigerator will use to interface 
with the Web Server.
* [Single Page Application](spa) - A demonstration of how a website would be able to interact 
with the device.

## TODO ##
This project requires some updates before it would be a shippable product.

1. __CI/CD__ - Each project will need to produce a `publish` and `deploy` task for execution
in a CI/CD server.
2. __Authentication and Authorization__ - Since the web server will serve multiple Owners, it 
is required that data retrieval and manipulation only be done by the approved Owner.  By using
an Oauth2 authentication model, each UI will be able to interact with a Owner's data.
3. __Device Registration__ - An IoT device must be associated with an Owner.  This way, the device 
can be registered as a User with permission to manipulate the Owner's data.
4. __Offline Mode__ - An IoT device the losses connectivity should still be able to function normally.
This is the same kind of problem to solve for mobile applications to ensure user's are not required 
to re-do their effort whenever possible.
5. __Data Synchronization__ - Once an IoT device is able to function offline, there MUST be a
facility to sync the data with the web server.
6. __Automatic Updates__ - This project only has a demonstration of how things might function, but a
real device MUST be able to receive updates.  Since this is for an IoT device, it MUST be able to 
automatically update to stay in sync with updates to the central web server.

## Development ##
Each project documents their specific needs.  However, each one will start up its own dependencies 
when running locally.

When manually testing each project should be started in their own terminal/command prompt.


    // Start the web server locally
    gradlew :web:bootRun
    
    // Start the executable shell locally
    gradlew :exec:bootRun
    
    // Start the single page applciation locally
    // TODO : not implemented yet
    
## Testing ##
All tests will be run by their respective projects and can be executed for the whole project
with the command:

    gradlew test
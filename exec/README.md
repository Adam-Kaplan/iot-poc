# IoT Executable - PoC #
This project is to demonstrate a possible implementation that could 
be placed on an IoT device for interfacing with the actual device.

An implementation of the [SmartFridgeManager](/src/main/java/com/example/iot/exec/service/SmartFridgeManager.java) 
interface is expected to be passed to a device library.  So this code only shows 
the implementation that will interact with the web server [HttpManagerService](/src/main/java/com/example/iot/exec/service/HttpManagerService.java).

## TODO ##
Because this is such a thin wrapper, there is not much specific to this project 
that needs to be done.  As major features are implemented, this will need to be 
updated as well.

1. __Shell for Development Only__ - Will need to figure out a way to remove the shell capabilities
for before deploying to a device.  Otherwise it should be considered a security vulnerability.

## Development ##
This project uses [Spring Shell](https://docs.spring.io/spring-shell/docs/current/reference/htmlsingle/) to 
allow local developers a way to interact with the code in a way similar to the device.  

Start the application by executing the `bootRun` task for this project.

After starting the project, type `help` into the command prompt to see all commands.

Since this is setup to use a terminal/command prompt for input, all logged data will be sent to log files
found in `./logs`.

## Testing ##
TODO : Unit Tests
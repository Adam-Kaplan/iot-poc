# IoT Shared JAR - PoC #
This project exists to share common classes between the Web Server and 
Executable projects since they are implemented in Java.

## TODO ##

1. __Consistent Versioning__ - Move dependency versions into the parent gradle file 
to avoid conflicts.

## Development ##
Developers MUST keep in mind that all dependencies will be included in all 
projects that consume this JAR.  It is imperative that dependencies be 
strictly maintained to ensure there are no conflicts.

## Testing ##
Currently, there are only data objects with annotations.  There is no logic to test.
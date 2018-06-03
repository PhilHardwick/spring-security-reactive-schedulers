# Spring security schedulers example

## How to run
Start the application with gradle:

    ./gradlew bootRun
    
And then, in a separate terminal window, run the performance tests:

    ./gradlew gatlingRun
    
To run the application with elastic scheduler for password encoding, run the application with:

    ./gradlew -Pelastic bootRun
    
And then run the performance tests with the same command as above.
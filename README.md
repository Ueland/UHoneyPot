# UHoneyPot

Simple Java application that listens on defined network ports and dump incoming data to files.

Upon recieving traffic, it gets logged to a logs-folder from
where you have started the program in the following format:

```logs/*port number*/*timestamp*.log```

## Installing from source

### Prerequisites

In order to compile and run the program you need the following applications/environments installed:
* Java (8+) runtime
* Java (8+) compiler
* Maven
* Git

Note that only Java runtime is required to actually run the program.

### Get the source

run 
```git clone https://github.com/Ueland/UHoneyPot.git```

### Compiling
Go into your UHoneyPot folder and run: 
```mvn clean install```

Upon compiling a target/app.jar file will be created which
is the executable jar containing the program.

### Running

If you have compiled the program yourself and are on Linux, you can simply
 go to the UHoneyPot folder and run the run.sh file. 
 
You can also run the program directly: 
```java -jar app.jar```
 
## Usage examples
### Via run.sh
#### Listen on the default Telnet port
```./run.sh 21```

#### Listen on Telnet and HTTP port
```./run.sh 21 80```

#### Listen on a range
```./run.sh 21-23```

#### Listen on multiple ranges
```./run.sh 21-23 80-90 440-443```
 
### Via java
#### Listen on the default Telnet port
```java -jar app.jar 21```

#### Listen on Telnet and HTTP port
```java -jar app.jar 21 80```

#### Listen on a range
```java -jar app.jar 21-23```

#### Listen on multiple ranges
```java -jar app.jar 21-23 80-90 440-443```
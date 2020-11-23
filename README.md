# Encrypt test

This project created to test the performance of json encryption and decryption in project [Secured Json](https://github.com/ShubhSingh/secured-json) through a jmeter script.

In encrypt-test it is included as a dependency

```
<dependency>
  <groupId>com.example</groupId>
  <artifactId>secured-json</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Prerequisite

First build project [Secured Json](https://github.com/ShubhSingh/secured-json)

## How to test in jmeter

This project generates an encrypt-test-jar-with-dependencies.jar which needs to be copied into 
```
apache-jmeter-5.2\lib\ext
```

Then open jmeter UI using
```
apache-jmeter-5.2\bin\jmeter.bat
```
To see performance test results in Jmeter Open and Run test script provided at
```
encrypt-test\src\test\resources\Java Request.jmx
```

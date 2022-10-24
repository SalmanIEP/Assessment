# Amazon Test Automation using Java and Selenium WebDriver


## Languages and Frameworks

This project using the following languages and frameworks:

* [Java 11](https://openjdk.java.net/projects/jdk/11/) as the programming language
* [TestNG](https://testng.org/doc/) as the UnitTest framework to support the test creation
* [Selenium WebDriver](https://www.selenium.dev/) as the web browser automation framework using the Java binding
* [AssertJ](https://joel-costigliola.github.io/assertj/) as the fluent assertion library
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy
* [JavaFaker](https://github.com/DiUS/java-faker) as the faker data generation strategy
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging management strategy
* [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) as the Selenium binaries management
* [Owner](http://owner.aeonbits.org/) to minimize the code to handle the properties file




### Page Objects pattern
The Framework is following the page object model

#### AbstractPageObject
This class has a protected constructor to remove the necessity to init the elements using the Page Factory. 
Also, it sets the timeout from the `timeout` property value located on `general.properties` file.

All the Page Object classes should extend the `AbstractPageObject`.
It also tries to remove the `driver` object from the Page Object class as much as possible.

### Execution types

There are two execution types: **local** and **remote**.
The `TargetFactory` class will resolve the target execution based on the `target` property value located on `general.properties` file.
Its usage is placed on the `BaseWeb` class before each test execution.

#### Local execution
This execution type uses WebdriverManager class to instantiate the web browsers.
When the `target` is `local` the `createDriver()` method is used from the `BrowserFactory` class to return the browser instance.

The browser used in the test is placed on the `browser` property in the `local.properties` file.

#### Remote execution
This execution is based on any Selenium Grid approach to execute the tests in remote machines (local or remote/cloud grid).
When the `target` is `remote` the `getOptions` method is used from the `BrowserFactory` to return the browser option 
class as the remote execution needs the browser capability.
The `DriverFactory` class has an internal method `createRemoteInstance` to return a `RemoteWebDriver` instance based on 
the browser capability.

Add the `grid.url` and `grid.port` to run test remotely
property values on the `grid.properties` file. You must update these values before the start.

If you are using the `docker-compose.yml` file to start the Docker Selenium grid, the values on the `grid.properties` file should work.


#### BrowserFactory class
This Factory class is a Java enum that has all implemented browsers to use during the test execution.
Each browser is an enum, and each enum implements two methods:
* `createDriver()`: creates the browser instance for the local execution. The browser driver is automatically managed by the WebDriverManager library
* `getOptions()`: creates a new browser Options setting some specific configurations. It's used for the remote executions

You can see that the `createDriver()` method used the `getOptions()` to use specific configuration, as starting the browser maximized and others.
The `getOptions()` is also used for the remote execution as it is a subclass of the `AbstractDriverOptions` and can be 
automatically accepted as either a `Capabilities` or `MutableCapabilities` class, which is required by the `RemoteWebDriver` class.

#### DriverManager class
The class
create a `ThreadLocal` for the WebDriver instance, to make sure there's no conflict when we run it in parallel.

### BaseTest
This testing pattern was implemented
class to automatically run the pre (setup) and post (teardown) conditions.

The pre-condition uses `@BeforeMethod` from TestNG creates the browser instance based on the values passed either local or remote execution.
The post-condition uses `@AfterMethod` to close the browser instance.
Both have the `alwaysRun` parameter as `true` to force the run on a pipeline.

Pay attention that it was designed to open a browser instance to each `@Test` located on the test class.

This class also the `TestListener` that is a custom TestNG listener, and will be described in the next section.

### TestListener


The following method is used to help logging errors and attach additional information to the test report:
* `onTestStart`: add the browser information into the test report
* `onTestFailure`: log the exceptions and add a screenshot to the test report
* `onTestSkipped`: add the skipped test on the log

### Logging
All the log is done by the Log4J using the `@Log4j2` annotation.

The `log4j2.properties` has two strategies: console and file.
A file with all the log information will be automatically created on the user folder with `test_automation.log` filename. 
If you want to change it, update the `appender.file.fileName` property value.

The `log.error` is used to log all the exceptions this architecture might throw. Use `log.info` or `log.debug` to log 
important information

### Parallel execution
The parallel test execution is based on the 
feature on TestNG. This is used by `parallel.xml` test suite file which has the `parallel="tests"` attribute and value, 
whereas `test` item inside the test suite will execute in parallel.
The browser in use for each `test` should be defined by a parameter, like:
```xml
<parameter name="browser" value="chrome"/>
```

You can define any parallel strategy.

It can be an excellent combination together with the grid strategy.

#### Execution with Docker Selenium Distributed
This project has the `docker-compose.yml` file to run the tests in a parallel way using Docker Selenium.
To be able to run it in parallel the file has the [Dynamic Grid Implementation](https://github.com/SeleniumHQ/docker-selenium#dynamic-grid-) that will start the container on demand.

This means that Docker Selenium will start a container test for a targeting browser.

Please not you need the following before run it in parallel:
* Docker installed
* Pull the images for Chrome and Firefox
  * `docker pull selenium-standalog-chrome` 
  * `docker pull selenium-standalog-firefox` 
* Pay attention at the `grid/config.toml` file that has comments for each specific SO
* Start the Grid running the following command inside the `grid` folder
  * `docker-compose up` 


There are 3 properties (configuration) files located on `src/test/java/resources/`:
* `general.properties`: general configuration as the target execution, base url, timeout, and faker locale
* `grid.properties`: url and port for the Selenium grid usage
* `local.properties`:  browser to use in the local execution

The properties were divided into three different ones to better separate the responsibilities and enable the changes easy 
without have a lot of properties inside a single file.


### Profiles executors on pom.xml

There is a profile called `web-execution` created to execute the test suite `local.xml` inside `src/test/resources/suites` folder.
To execute this suite, via the command line you can call the parameter `-P` and the profile id.

Eg: executing the multi_browser suite
``` bash
mvn test -Pweb-execution -Dtestng.dtd.http=true 
```


### Pipelines

The two files of the pipeline as a code are inside `pipelines` folder.

* Jenkins: `Jenkinsfile` to be used on a Jenkins pipeline located at `pipelines`
* GitLab CI: `.gitlab-ci.yml` to be used on a GitLab CI `pipelines`
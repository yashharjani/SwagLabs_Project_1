# SwagLabs_Project1
This project has Manual Test cases and Automation test cases. 

- Manual test cases are in ManualTestCases folder.
- Automation test cases code are in SourceCode\SwagLabs_Project folder.

Automation Test Reports are stored in Artifacts -> Reports folder for the reference.

Automation Test Cases are created using Cucumber and Selenium using Java programming language. 

Automation test cases are implemented with Page Object Model (POM) design pattern using Page Factory class.

To open the project/assignment, following two ways can be followed:

- Clone the project using git and open the maven project in an IDE (Eclipse). 
- Download the Zip file and extract it. Then open maven project.

The project file structure is:

- Under src/test/java, there are Page object classes, Test runner class, and Step definitions.
- Under src/test/resources, there are Drivers folder, and Feature file folder.
- Reports are generated in two folders: SwagLabs_Project\Reports and SwagLabs_Project\target\HTMlReports\report.html
- There is a POM.xml file in which all dependencies and plugins are there. 
- There is also a testng.xml file located at SwagLabs_Project\testng.xml for cross browser testing. 

Feature file description:

- There are two feature files. One is for Login page consisting of 7 scenarios of Login functionality. 
- And another feature file contains end-to-end flow scenarios with 3 postive scenarios and 3 negative scenarios.
- For login feature file, most of the scenarios are scenario outline for parameterization. 
- I have configured two browsers in this project: Chrome driver and Edge driver.

Step Definition description:
- I have created only one step definition file for both the Feature files. All the methods are in this file only.
- Step Definition has mainly the business logic of the Automation scripts (feature files). 
- Page element locators and action method are in pages folder.
- Mostly I have used Map interface that works on a Key and Value pair concept for most of the When, and Then methods.
- Using Map interface, we can get the user expected value and we can find what's the actual value. And respectively, I have asserted using Assert.assertEquals(expectedValue, actualValue).
- I have tried to maintain readablity of the code by creating few seperate methods as well (apart from given, when, then annotation methods) that are mainly called by annotated methods.

To run the automation scripts, following two ways can be followed:

- Run the testng.xml file using TestNG. This will first run all the scenarios in Chrome browser and then in Edge browser.
- Run through command line. Open the cmd terminal where pom.xml file is located. Write and run the command (mvn install).
- Running feature file individually using cucumber or running TestRunner class using Junit or TestNG will not work as I am also doing cross-browser testing from testng.xml file.
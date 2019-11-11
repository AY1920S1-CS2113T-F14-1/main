[![Build Status](https://travis-ci.org/AY1920S1-CS2113T-F14-1/main.svg?branch=master)](https://travis-ci.org/AY1920S1-CS2113T-F14-1/main)  [![Build status](https://ci.appveyor.com/api/projects/status/smfjdes9c0yy98vi?svg=true)](https://ci.appveyor.com/project/Greatnest/main-7vh7t) [![Build Status](https://dev.azure.com/lindonng0501/MooMooMoney/_apis/build/status/AY1920S1-CS2113T-F14-1.main?branchName=master)](https://dev.azure.com/lindonng0501/MooMooMoney/_build/latest?definitionId=3&branchName=master)
# MooMooMoney
![Alt text](images/Ui.png?raw=true "Ui Mockup")

MooMooMoney is a command line money management application written in Java 11.

It allows a user to:
1. Add a category.
2. Set a budget for that category.
3. Add an expenditure into that category.
4. View savings in a formatted table.
5. View expenditure in a graph format.
6. Schedule payment on a future date.
7. Notify users when they are about to overspent.

## Setting up

**Prerequisites**

*   JDK 11
*   Recommended: IntelliJ IDE
*   Fork this repo to your GitHub account and clone the fork to your computer

**Importing the project into IntelliJ**

1.  Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first).
2.  Set up the correct JDK version.
*   Click `Configure` > `Structure for new Projects` (in older versions of Intellij:`Configure` > `Project Defaults` > `Project Structure`).
*   If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11.
*   Click `OK`.
   
3.  Click `Import Project`.
4.  Locate the project directory and click `OK`.
5.  Select `Create project from existing sources` and click `Next`.
6.  Rename the project if you want. Click `Next`.
7.  Ensure that your src folder is checked. Keep clicking `Next`.
8.  Click `Finish`.

## Developing the project
Gradle is integrated into the project and can be used to run various functions.

**Examples**
1. `gradle run` to start the application
2. `gradle test` to run JUnit Tests written
3. `gradle checkstyleMain checkstyleTest` to run a coding standard check based on the configuration.

**Setting up**
1.  If the dropdown box beside the run button is empty, click the dropdown box > `Edit Configurations`  
2.  Click on `+` > `Gradle`
3.  At the end of the `Gradle Project` text box on the right, click the folder with a blue icon, select `duke` as the project.
4.  Click `OK`
5.  To run specific `gradle` commands, click on `Gradle` on the right of IntelliJ, click on the `elephant icon` labeled `Execute Gradle Task`
6.  Type in any `gradle` command as per the above examples.
7.  The option should appear in the dropdown box and it can be selected to run that task.

## Running the program
Java JAR files are regularly released under Releases which can be ran on Windows, Linux and MacOS.

1.  Install Java 11 JDK and ensure that `java --version` shows `java 11.0.X`
2.  Open command prompt and run `java -jar MooMooMoney-X.jar`

## Site Map

[About us](AboutUs.adoc)

[Developer Guide](%5BCS2113T-F14-1%5D%5BMooMooMoney%5DDeveloperGuide.pdf)

[User Guide](%5BCS2113T-F14-1%5D%5BMooMooMoney%5DUserGuide.pdf)



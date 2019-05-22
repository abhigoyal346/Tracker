# Tracker
This is my First Project in Java 
## Key Features
* It tracks employee work by capturing screenshots and couting keystrokes & Mouse Clicks
* Screenshots of employee are uploaded to server which can be retrieved by employer & admin
* Employer/Admin can add new employees
* Project can be created and assigned to employees according to requirements
* Data is stored in Mysql Database

## Software Requirements
* Netbeans IDE
* Java Runtime Environment
 
## Changes Required
1. After Opening Project in Netbeans
2. In Utility.java File (under Utility Package) there are certain changes to be done
..* Change Absolute Paths according to your system partitioning
..* Add Email-Id and Password (this is used when user clicks forgot password,a mail is sent to user email-id from added email)
..* Local Server address and port number assigned can be changed (if required)
3. In DBConnection.java file(under Database package) userName and password (default "root" and "") can be setup
 
 ## How To Run
 1. Run Server.java file (under Services Package)
 2. RUn LoginFrame.java file (under Presentation Layer Package)
 3. Enter Credetianls by default admin username is **cgc** and password is **cgc**
 
 ## Other Details
 1. DB name is db located under database folder
 2. All Jar Files are in Lib folder
 

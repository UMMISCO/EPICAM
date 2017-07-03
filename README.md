# EPICAM

EPICAM is a platform for generating epidemiological surveillance system. It is based on imogene (https://github.com/medes-imps/imogene), a platform used to generate data collection application.

# How to install
## Prerequisite
Before you install EPICAM, you must install : 
1- JDK (at least version 1.6)
2- Eclipse for JAVA EE (at least Kepler) and add Eclipse Modeling Framwork, Google Web Toolkit plugins to Eclipse
3- Git
4- apache-tomcat

## Installation JAVA
Download JAVA (at least version 1.7) at https://java.com/fr/download/ or http://www.oracle.com/technetwork/java/javase/downloads/index.html
To install it, unzip it in one folder of your machine (you can use /opt) and configure the JAVA_HOME environment variable
Test your installation by typing : java -version in a terminal → it will show the version of your JDK

## Installation of Eclipse
Epicam was tested with Eclipse Kepler. Download Eclipse at http://www.eclipse.org/downloads.
To install it, unzip it in a folder in your machine and Configure the ECLIPSE_HOME environment variable (but it is not essential).
To start working with Eclipse, :
- If you have configure ECLIPSE_HOME, start it in a terminal by typing the comand eclipse
- If not, enter in the unzip eclipse folder and start eclipse by doucble cliking on eclipse or by typing ./eclipse in a terminal

## Install Google Web Toolkit
GWT can be install via Eclipse Marketplace : 
When Eclipse is started, click on help, Eclipse Market Place and in search field, type gwt
GWT can also be installed using GWT update-site:
- Go on http://dl.google.com/eclipse/plugin/add your eclipse version
- Choose Google Plugin for Eclipse and Google Web Toolkit and install it by cliking on next button

## Install other dependencies
- Eclipse → Help → Install new software
- In click the drop down list in  and choose your Eclipse version
- In the list of plugins availlable, choose: 
  - 'EMF - Eclipse Modeling Framework SDK', 
  - 'EMF Validation Framework SDK', 
  - 'Xpand SDK' 
  - 'MWE SDK',
- Install all these plugins by clicking on next button

## Install EPICAM
To install EPICAM:
- Restart your Eclipse (if after all installations it does not restarted)
- Import all EPICAM plugins projects in Eclipse
- Go in task bar and chose run → run configurations → Eclipse platform → run with all plugin in the workspace and run it
A new Eclipse will start.

To test:
- Import an example project in the new Eclipse
- Open the meta-model and discover all entities and elements

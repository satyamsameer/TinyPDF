# TinyPDF
TinyPDF repository a lightweight Java/Swing UI based graphical user interface PDF Tool.

## About

Tiny PDF is a JAVA based utility/software for light weight works on the PDFs.
This utility can be said as a lightweight JAVA and Swing language based GUI tool for viewing, splitting and merging more than one PDF files.
This only performs some basic functions such as PDF viewer, single PDF file Splitting/Cutout and multiple PDF file Merging. This is light software and have some limitations which will be mentioned at the end of the readme file. Anyone can try it out and reviews should be given to the author while using. Some bugs/errors may occur so kindly notify in a mean time.

### Prerequisites

What things you need to deploy the project and details of them -

```
- Git setup and knowledge to pull the repository locally.
- Any JAVA/Swing UI language IDE such as Eclipse.
- A little knowledge on how to setup a JAVA project and export to .jar executable to run the software.
- Java JDK/JRE 8 or later for runtime enviroment and deployment.
- API's which are used here are- iText 0.9.1 and PDF Renderer 5.5.9
- Any system with Linux/MacOSX/Windows.
```

### Usage and Deployment

A step by step series that tell you how to get a development environment and project up and running. You can run it without any installation as it is cross platform (JAVA based GUI software) by following the below mentioned steps.

The following steps are required for setting source code in Eclipse IDE and creating a .jar executable in Eclipse.
```
1. git clone <repoRemoteName> Pull the repository using repository URL using Git Bash.
2. Create a same name Java Project i.e., TinyPDF in workspace.
3. Download the latest .jar files of iText 0.9.1 and PDF Renderer 5.5.9 and add the libraries in this project for proper working.
4. Convert it to Java project: Add nature and buildCommand elements from other Java project to your .project file (this is optional).
5. Then from Project > Properties > Java Build Path > Source, add your source file and folders (and possible libraries).
6. Then run the mainActivity.java
Note: If you want the .jar executable file export the build using Eclipse.
```


## Features

* Simple and nice front interface (PDF Viewer).
* All commands on Menubar with keyboard shortcuts for quick navigation and usability.
* Easily navigable pages with page number and Next, Previous, Last and First buttons and goto page number box.
* Magnifier and Fit to screen feature for a PDF page.
* Single PDF file splitter which cuts out one or more pages and make a new PDF file from them.
* Merging two or more PDF files and make it one PDF file.
* Simple Merger and Splitter interfaces for usability and with a status notification area.
* About window because it should be included.

## Screen Shots

Some screen shots of TinyPDF GUI interfaces.

![PDF Viewer Screen](https://github.com/satyamsameer/TinyPDF/blob/master/snaps/1.png)
![All Interfaces](https://github.com/satyamsameer/TinyPDF/blob/master/snaps/2.png)
![Zoom View](https://github.com/satyamsameer/TinyPDF/blob/master/snaps/3.png)
![Windowed View](https://github.com/satyamsameer/TinyPDF/blob/master/snaps/4.png)


## Built with

* [Java JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Development Kit
* [Eclipse](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/lunasr2) - IDE for Java and many other languages
* [iText](http://developers.itextpdf.com/downloads) - API for editing PDFs
* [PDF Renderer](https://java.net/projects/pdf-renderer) - API for rendering and working with PDF files
* [Git SCM](https://git-scm.com/downloads) - Online Repository Host Github

## Contributing

* [satyamsameer](https://github.com/satyamsameer)

## Version

Currently Initial version v1.0

## Authors

Sameer Satyam - Second Project - [satyamsameer](https://github.com/satyamsameer)

## License

This project is licensed under the [MIT License](https://github.com/satyamsameer/TinyPDF/blob/master/LICENSE.md)

## Acknowledgement

* This is my second project while learning JAVA and software development.
* Buffer memory is not implemented so PDF may load incomplete.
* Bugs, Errors, Code duplications and Code optimizations are welcome.


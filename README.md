# Muirwik Starter App

This is the starter app for [Muirwik](https://github.com/cfnz/muirwik).

The starter app uses bintray/jcenter to pickup its Muirwik dependencies.

This is the best way to start an application if you don't want to make changes to the 
component wrappers themselves but just want to use them.

## To Get Started
Make sure you have git and the yarn package manager installed (and not the yarn that comes with cmdtest in Ubuntu :-)), 
then the following should work (possibly may also need npm installed):

    git clone https://github.com/cfnz/muirwik-starterapp.git
    cd muirwik-starterapp
    ./gradlew yarn
    ./gradlew build
    ./gradlew webpackDevServerOpenBrowser

In Windows it is probably (but have not tried it) more like:

    git clone https://github.com/cfnz/muirwik-starterapp.git
    cd muirwik-starterapp
    gradlew.bat yarn
    gradlew.bat build
    gradlew.bat webpackDevServerOpenBrowser
    
## Background
This started as a sub-project/module on [Muirwik](https://github.com/cfnz/muirwik), but
to make starting easier, and because you don't need the whole Muirwik project,
in order to use it, it got made into its own stand alone project.

For more information see [Muirwik](https://github.com/cfnz/muirwik) and, for information
about the React components that this project wraps, you will want to see 
[Material UI](https://material-ui.com/) which holds lots of valuable information. 
    
## Contributing
Feedback and contributions are welcome :-). 


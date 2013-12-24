To succesfully compile project you need to add sp-copy-list.jar to local maven repository. For that do this steps: 
1) Open CMD
2) Go to project directory
3) Write mvn install:install-file -Dfile=sp-copy-list.jar -DgroupId=com.exadel -DartifactId=splistcopy -Dversion=1.0 -Dpackaging=jar
4) Recompile project..

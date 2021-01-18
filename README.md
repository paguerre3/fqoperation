# fqoperation [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
Top secret project that returns distance of src and help message


# semantic 
Why using Trilateration: http://juankenny.blogspot.com/2013/05/rt-tarea-6-geolocalizacion.html

Java library: https://github.com/lemmingapex/Trilateration


# arquitecture
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/seq-diagram.jpg?raw=true)

### package view 
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/pckge-diagram.jpg?raw=true)


# google app engine (sandbox) 
https://codelabs.developers.google.com/codelabs/cloud-app-engine-springboot#0

fresh install (please notice service is already deployed under asia-east2 region):

0. authorizate api cloud from console (google usr & pass)-->> gcloud auth list

1. check if selected is the one to work with -->> gcloud config list project

2. clone existent repo -->> git clone https://github.com/paguerre3/fqoperation.git

3. update gradle build.gradle and settings.gradle to support google-appengie-plugin: https://github.com/GoogleCloudPlatform/app-gradle-plugin

4. set app.yaml under src/main/appengine with the following lines: runtime: java11
instance_class: F1 (using smallest instance).

5. read references to understand appengine plugin with gradle (it explains steps 3, 4, 6 setup and also appengine commands to be used):
https://cloud.google.com/appengine/docs/standard/java/using-gradle?hl=es-419
https://medium.com/@fanovilla/deploying-a-spring-boot-gradle-app-to-app-engine-standard-java-11-bf7b8d15d81c
https://cloud.google.com/appengine/docs/standard/java/gradle-reference?hl=es-419 

6. write appengine deploy configurations in build.gradle (already present in build.gradle, please see previous references or existent build.gradle) 

8. update chmod to execute from gradle wrapper -->> chmod 777 gradlew

9. create google cloud app under a certain region -->> gcloud app create (region selection is being requested during creation)

10. service deploy invoking appengine from gradle wrapper -->> ./gradlew appengineDeploy

### service location: https://fqoperations.df.r.appspot.com/v1/topsecret


# license
Copyright 2021, paguerre3

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
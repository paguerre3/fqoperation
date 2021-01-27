# fqoperation [![Build Status](https://travis-ci.com/paguerre3/fqoperation.svg?token=vSTu1zSW1ehqZeuodHpi&branch=master)](https://travis-ci.com/paguerre3/fqoperation) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
Top secret project that returns distance of src and help message


# semantic 
Why using Trilateration: http://juankenny.blogspot.com/2013/05/rt-tarea-6-geolocalizacion.html

Java library: https://github.com/lemmingapex/Trilateration


# arquitecture
### sequence view 
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/seq-diagram.png?raw=true)

### package view 
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/pckge-diagram.png?raw=true)

### class view 
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/clss-diagram.png?raw=true) 

### implementation view
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/springboot-appengine.png?raw=true) 
![Screenshot](https://github.com/paguerre3/fqoperation/blob/master/design/appengine-flex-autoscale.png?raw=true) 


# google app engine (sandbox) 
https://codelabs.developers.google.com/codelabs/cloud-app-engine-springboot#0

fresh install (just for fun, please notice that v1 service is already deployed under asia-east2 region and v2 service under southamerica-east1):

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

### service V1 location (asia-east2):
### https://fqoperations.df.r.appspot.com/v1/topsecret

### service V2 locations (southamerica-east1 is more efficient): 
### https://fqoperations2.rj.r.appspot.com/v2/topsecret
### https://fqoperations2.rj.r.appspot.com/v2/topsecret_split/{name}
### https://fqoperations2.rj.r.appspot.com/v2/topsecret_split


# user manual
api references (import file/s into https://editor.swagger.io/ for visualization): https://github.com/paguerre3/fqoperation/blob/master/swaggerV1.yaml OR https://github.com/paguerre3/fqoperation/blob/master/swaggerV2.yaml


usage V1: <pre><code>curl -X POST "https://fqoperations.df.r.appspot.com/v1/topsecret" -H  "accept: application/json" -H  "Content-Type: application/json" -d '{"satellites": [{"name": "kenobi","distance": 100.0,"message": ["este", "", "", "mensaje", ""]},{"name": "skywalker","distance": 115.5,"message": ["", "es", "", "", "secreto"]},{"name": "sato","distance": 142.7,"message": ["este", "", "un", "", ""]}]}'</code></pre>

in case of V2: <pre><code>curl -X POST "https://fqoperations2.rj.r.appspot.com/v2/topsecret_split/kenobi" -H  "accept: application/json" -H  "Content-Type: application/json" -d '{"distance": 100.0,"message": ["este", "", "", "mensaje", ""]}'</code></pre> <pre><code>curl -X POST "https://fqoperations2.rj.r.appspot.com/v2/topsecret_split/skywalker" -H  "accept: application/json" -H  "Content-Type: application/json" -d '{"distance": 115.5,"message": ["", "es", "", "", "secreto"]}'</code></pre> <pre><code>curl -X POST "https://fqoperations2.rj.r.appspot.com/v2/topsecret_split/sato" -H  "accept: application/json" -H  "Content-Type: application/json" -d '{"distance": 142.7,"message": ["este", "", "un", "", ""]}'</code></pre> <pre><code>curl -X GET "https://fqoperations2.rj.r.appspot.com/v2/topsecret_split" -H  "accept: application/json"</code></pre>


# travis CI
https://travis-ci.com/github/paguerre3/fqoperation


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
# jenkins-build-agl-distro

**WORK-IN-PROGRESS**

Instructions for setting up a Declarative Pipeline for building [Automotive Grade Linux](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/).

## Preparation

* Install and configure [easy-jenkins](https://github.com/gmacario/easy-jenkins)
* Verify that the Jenkins Dashboard is accessible at `${DOCKER_URL}` (example: http://192.168.99.100:9080/)

## Step-by-step Instructions

### Create project `build-agl-distro`

* Browse `${JENKINS_URL}` > New Item
  - Name: `build-agl-distro`
  - Type: **Pipeline**
  then click **OK** and configure the project properties
  
  - Definition: Pipeline script from SCM
    - SCM: Git
      - Repositories
        - Repository URL: `https://github.com/gmacario/jenkins-build-agl-distro`
      - Branches to build: `*/use-declarative-pipeline`
      - Script Path: `Jenkinsfile`
then click **Save*

### Run project `build-agl-distro`

* Browse `${JENKINS_URL}/jobs/build-agl-distro`, then click **Build Now**

### License and Copyright

easy-jenkins is licensed under the MIT License - for details please see the `LICENSE` file.

Copyright 2016-2017, [Gianpaolo Macario](http://gmacario.github.io/)

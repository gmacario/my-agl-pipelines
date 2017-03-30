# my-agl-pipelines

**WORK-IN-PROGRESS**

Instructions for setting up a Declarative Pipeline for building [Automotive Grade Linux](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/).

### System Requirements

* [Jenkins v2.32](https://jenkins.io/) or greater with the following installed plugins:
  - [Blue Ocean Plugin v1.0.0-b23](https://wiki.jenkins-ci.org/display/JENKINS/Blue+Ocean+Plugin)
  - [Pipeline Multibranch Plugin v2.12](https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Multibranch+Plugin)
  - **NOTE**: You may use [easy-jenkins](https://github.com/gmacario/easy-jenkins) to install all the prerequisites
* Lot of time, network bandwidth and disk space...

### Usage

From the Jenkins dashboard, click **New Item**

* Enter an item name: `build-agl-distro`
* Type: **Pipeline**

then click **OK**.
Inside the project configuration page, add the following information
  
* Pipeline > Definition: Pipeline script from SCM
  - SCM: Git
    - Repositories
      - Repository URL: `https://github.com/gmacario/jenkins-build-agl-distro`
    - Branches to build: `*/master`
  - Script Path: `Jenkinsfile`
  - Lightweight checkout: Yes

then click **Save**

Select job `build-agl-distro`, then click **Build Now**.

Click on **Blue Ocean** to display the Blue Ocean Dashboard.

### License and Copyright

easy-jenkins is licensed under the MIT License - for details please see the `LICENSE` file.

Copyright 2016-2017, [Gianpaolo Macario](http://gmacario.github.io/)

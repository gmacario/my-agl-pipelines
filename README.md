# my-agl-pipelines

**WORK-IN-PROGRESS**

Instructions for for building [Automotive Grade Linux](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/) and a [Declarative Pipeline](https://jenkins.io/blog/2017/02/03/declarative-pipeline-ga/).

### System Requirements

* [Jenkins v2.32.3](https://jenkins.io/) or greater with the following installed plugins:
  - [Blue Ocean v1.0.0-rc3](https://wiki.jenkins-ci.org/display/JENKINS/Blue+Ocean+Plugin)
  - [Pipeline: Multibranch v2.14](https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Multibranch+Plugin)
  - **NOTE**: You may use [easy-jenkins](https://github.com/gmacario/easy-jenkins) to install all the prerequisites
* Lot of time, network bandwidth and disk space...

Tested on ies-genbld01-ub16 (Ubuntu 16.04.2 LTS 64-bit, Docker 17.03.1-ce)

### Usage

From the Jenkins dashboard, click **New Item**

* Enter an item name: `build-agl-distro-rpi3`
* Type: **Pipeline**

then click **OK**.
Inside the project configuration page, add the following information
  
* Pipeline > Definition: Pipeline script from SCM
  - SCM: Git
    - Repositories
      - Repository URL: `https://github.com/gmacario/my-agl-pipelines`
    - Branches to build: `*/master`
  - Script Path: `Jenkinsfile`
  - Lightweight checkout: Yes

then click **Save**

Select job `build-agl-distro-rpi3`, then click **Build Now**.

Click on **Blue Ocean** to display the Blue Ocean Dashboard.

### License and Copyright

easy-jenkins is licensed under the MIT License - for details please see the `LICENSE` file.

Copyright 2016-2017, [Gianpaolo Macario](http://gmacario.github.io/)

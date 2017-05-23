# my-agl-pipelines

Instructions for for building the [Automotive Grade Linux](https://wiki.automotivelinux.org/agl-distro) distribution using [Jenkins](https://jenkins-ci.org/) and a [Declarative Pipeline](https://jenkins.io/doc/book/pipeline/).

### System Requirements

* [Jenkins v2.46.2](https://jenkins.io/) or greater with the following installed plugins:
  - [Blue Ocean v1.0.1](https://wiki.jenkins-ci.org/display/JENKINS/Blue+Ocean+Plugin)
  - [Pipeline: Multibranch v2.14](https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Multibranch+Plugin)
  - **NOTE**: You may use [easy-jenkins](https://github.com/gmacario/easy-jenkins) to install all the prerequisites
* Lot of time, network bandwidth and disk space...

Tested on ies-genbld01-ub16 (Ubuntu 16.04.2 LTS 64-bit, Docker 17.03.1-ce)

### Usage

From the Jenkins dashboard, click **New Item**

* Enter an item name: `my-agl-pipelines`
* Type: **Multibranch Pipeline**

then click **OK**.
Inside the project configuration page, add the following information:
  
* Branch Sources > Add source > Git
    - Project Repository URL: `https://github.com/gmacario/my-agl-pipelines`

then click **Save**

Select job `my-agl-pipelines`, then click **Build Now**.

Click on **Blue Ocean** to display the Blue Ocean Dashboard.

### License and Copyright

The project is licensed under the MIT License - for details please see the `LICENSE` file.

Copyright 2016-2017, [Gianpaolo Macario](http://gmacario.github.io/)

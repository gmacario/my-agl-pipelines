# jenkins-build-agl-distro

**WORK-IN-PROGRESS**

Instructions for setting up a Pipeline for building the [AGL Distribution](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/).

## Preparation

* Install and configure [easy-jenkins](https://github.com/gmacario/easy-jenkins)
* Verify that the Jenkins Dashboard is accessible at `${DOCKER_URL}` (example: http://192.168.99.100:9080/)

## Step-by-step Instructions

### Create project `seed-agl`

* Browse `${JENKINS_URL}/script`
* Paste the contents of [seed-agl.groovy](https://github.com/gmacario/jenkins-build-agl-distro/raw/master/seed-agl.groovy) into the text area, then click **OK**

### Run project `seed-agl`

* Browse `${JENKINS_URL}/jobs/seed-agl`, then click **Build Now**

TODO

Copyright 2016, [Gianpaolo Macario](http://gmacario.github.io/)

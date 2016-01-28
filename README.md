# jenkins-build-agl-distro

Instructions and Pipeline for building the [AGL Distribution](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/).

## Prerequisites

* [easy-jenkins](https://github.com/gmacario/easy-jenkins) installed and running

## Instructions

* Browse `http://${DOCKER_MACHINE_IP}:9080/`, click **New Item**
  - Item name: `build-agl-distro`
  - Type: **Pipeline**

  then click **OK**

* Inside the project configuration page, fill-in the following information:
  - This build is parameterized: Yes
    * Choice Parameter
      - Name: `gitUrl`
      - Choices:
      
        ```
        TODO
        ```
    * Choice Parameter
      - Name: `gitBranch`
      - Choices:
      
        ```
        TODO
        ```
  - Pipeline
    - Definition: Pipeline script from SCM
    - SCM: Git
      - Repositories
        - Repository URL: `https://github.com/gmacario/jenkins-build-agl-distro.git`

  then click **Save**

* Browse `http://${DOCKER_MACHINE_IP}:9080/job/build-agl-distro`, then click **Build with Parameters**
  - Select gitUrl, gitBranch, then click **Build**

TODO: Add build results - From <http://139.181.213.96:9080/job/build-agl-distro/1/console>:

```
TODO
```

<!-- EOF -->

# build-genivi-demo-platform

## Prerequisites

* [easy-jenkins](https://github.com/gmacario/easy-jenkins) installed

## Instructions

* Browse `http://${DOCKER_MACHINE_IP}:9080/`, click **New Item**
  - Item name: `build-genivi-demo-platform`
  - Type: **Pipeline**

  then click **OK**

* Inside the project configuration page, fill-in the following information:
  - This build is parameterized: Yes
    * Choice Parameter
      - Name: `gitUrl`
      - Choices:
      
        ```
        git://git.projects.genivi.org/genivi-demo-platform.git
        https://github.com/gmacario/genivi-demo-platform
        ```
    * Choice Parameter
      - Name: `gitBranch`
      - Choices:
      
        ```
        qemux86-64
        qemu-ci
        ---
        koelsch
        porter
        silk
        ---
        minnowboard
        --------
        dev-imx6qsabresd-fido
        dev-udooneo
        dev-udooneo-fido
        dev-qemux86-64-jethro
        ```
  - Pipeline
    - Definition: Pipeline script from SCM
    - SCM: Git
      - Repositories
        - Repository URL: `https://gist.github.com/gmacario/f6f55d3ceff024de516f`

  then click **Save**

* Browse `http://${DOCKER_MACHINE_IP}:9080/job/build-genivi-demo-platform/build?delay=0sec` to trigger a build

TODO: Add build results - From <http://139.181.213.96:9080/job/build-genivi-demo-platform/1/console>:

```
TODO
```

<!-- EOF -->
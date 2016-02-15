/* 
  Project: https://github.com/gmacario/jenkins-build-agl-distro
  File:    mydsl/seed_agl_MIRROR_fetchall_push.groovy
*/
  
/*
Usage:

Browse `${JENKINS_URL}`, then click **New Item**

* Item Name: `seed_agl_MIRROR_fetchall_push`
* Type: Freestyle project

then click **OK**.

Browse `${JENKINS_URL}/job/seed_agl_MIRROR_fetchall_push/configure` and change

* Build
  - Add build step > Process Job DSLs
    - Use the provided DSL script: Yes
      - DSL Script: <paste this file>
  
then click **Save**.

Browse `${JENKINS_URL}/job/seed_agl_MIRROR_fetchall_push/`, then click **Build Now**

Result: Project `MIRROR-featchall-push` will be listed in `${JENKINS_URL}`
*/

// def jobName = "MIRROR-featchall-push"
// def gitUrl = "https://github.com/gmacario/genivi-demo-platform"
// def gitBranch = "qemux86-64"

job(jobName) {
  label('yocto')
  scm {
    /* git(gitUrl, gitBranch) {
      // TODO
    } */
  }
  steps {
    // shell "id"
    // shell "printenv"
    // shell "ps axf"
    // shell "bash -xec \"source init.sh && bitbake genivi-demo-platform\""
  }
}

// EOF

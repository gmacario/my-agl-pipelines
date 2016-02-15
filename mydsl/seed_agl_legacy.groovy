/* 
  Project: https://github.com/gmacario/jenkins-build-agl-distro
  File:    mydsl/seed_agl_legacy.groovy
*/
  
/*
Usage:

Browse `${JENKINS_URL}`, then click **New Item**

* Item Name: `seed_agl_legacy`
* Type: Freestyle project

then click **OK**.

Browse `${JENKINS_URL}/job/seed_agl_legacy/configure` and change

* Build
  - Add build step > Process Job DSLs
    - Use the provided DSL script: Yes
      - DSL Script: <paste this file>
  
then click **Save**.

Browse `${JENKINS_URL}/job/seed_agl_legacy/`, then click **Build Now**

Result: Folder `AGL-legacy` will be created in `${JENKINS_URL}`
together with a few projects inside
*/

def folderName = 'AGL-legacy'

folder(folderName) {
  displayName('build.automotivelinux.org')
  description('Replica of https://build.automotivelinux.org/')
}

freeStyleJob(folderName + '/releng-scripts') {
  scm {
      git('https://gerrit.automotivelinux.org/gerrit/AGL/releng-scripts') {
        branches('*/master')
      }
  }
}

matrixJob(folderName + '/MIRROR-featchall-push') {
  
  // Discard Old Builds: Yes / Strategy: Log Rotation / Max num of builds to keep: 2
  configure { project ->
    project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
      strategy(class: "hudson.tasks.LogRotator") {
        daysToKeep(-1)
        numToKeep(2)
        artifactDaysToKeep(-1)
        artifactNumToKeep(-1)
      }
    }
  }
  
  // TODO: Restrict where this project can be run: Yes / Label Expression: Yocto
  
  childCustomWorkspace('../${MACHINE}')
  
  // TODO: Build periodically / Schedule: TODO
  
  // TODO: Configuration Matrix
  axes {
    label('label', 'yocto')
    text('MACHINE', 'qemux86', 'qemux86-64', 'porter')
  }
  steps {
      shell "printenv"
      shell "echo TODO"
  }
}

/* OLD STUFF BELOW:

 label('yocto')

  scm {
      git(gitUrl, gitBranch) {
        // TODO
      }
  }

  steps {
      shell "id"
      // shell "printenv"
      // shell "ps axf"
      // shell "bash -xec \"source init.sh && bitbake genivi-demo-platform\""
  }
*/

// EOF

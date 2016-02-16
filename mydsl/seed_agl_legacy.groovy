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

* Discard Old Builds: Yes
  - Strategy: Log Rotation
    - Max # of builds to keep: 2

* Source Code Management: Git
  - Repositories
    - Repository URL: https://github.com/gmacario/jenkins-build-agl-distro

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
}		// end releng-scripts


matrixJob(folderName + '/CI-AGL-DemoApps-CES2016') {
    // TODO
}		// end CI-AGL-DemoApps-CES2016


matrixJob(folderName + '/CI-AGL-repo') {
    // TODO
}		// end CI-AGL-repo


matrixJob(folderName + '/CI-external-meta-openembedded') {
    // TODO
}		// end CI-external-meta-openembedded


matrixJob(folderName + '/CI-external-meta-qt5') {
    // TODO
}		// end CI-external-meta-qt5


matrixJob(folderName + '/CI-external-poky') {
    // TODO
}		// end CI-external-poky


freeStyleJob(folderName + '/CI-meta-agl') {
  configure { project ->
      // Advanced Project Options / Restrict where this project can be run: Yes / Label Expression: Yocto
      (project / 'assignedNode').value = 'yocto'
      (project / 'canRoam').value = 'false'
  }
  // TODO: Depends on https://github.com/gmacario/easy-jenkins/pull/46
  multiscm {
        git('https://gerrit.automotivelinux.org/gerrit/AGL/meta-agl') {
            branches('refs/changes/93/5393/1')			// ???
            // branches('*/master')		// ???
        }
        git('https://gerrit.automotivelinux.org/gerrit/AGL/releng-scripts') {
            branches('*/master')
            relativeTargetDir('releng-scripts')
        }
  }
  // Build > Add build step > Execute shell
  steps {
      shell "printenv"
      shell(readFileFromWorkspace('mydsl/CI-meta-agl_buildstep.sh'))
  }
  // TODO
}		// end CI-meta-agl


matrixJob(folderName + '/CI-meta-agl-demo') {
    // TODO
}		// end CI-meta-agl-demo


matrixJob(folderName + '/CI-meta-renesas') {
    // TODO
}		// end CI-meta-renesas


matrixJob(folderName + '/CI-z_sandbox') {
    // TODO
}		// end CI-z_sandbox


matrixJob(folderName + '/MIRROR-featchall-push') {
  configure { project ->
      // Discard Old Builds: Yes / Strategy: Log Rotation / Max num of builds to keep: 2
      project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
          strategy(class: "hudson.tasks.LogRotator") {
              daysToKeep(-1)
              numToKeep(2)
              artifactDaysToKeep(-1)
              artifactNumToKeep(-1)
          }
      }
      // Advanced Project Options / Restrict where this project can be run: Yes / Label Expression: Yocto
      (project / 'assignedNode').value = 'yocto'
      (project / 'canRoam').value = 'false'
  }
  // Advanced Project Options / Use custom child workspace
  childCustomWorkspace('../${MACHINE}')
  // Build Triggers / Build periodically / Schedule: TODO
  // TODO
  // Configuration Matrix
  axes {
      label('label', 'yocto')
      text('MACHINE', 'qemux86', 'qemux86-64', 'porter')
  }
  // Build > Add build step > Execute shell
  steps {
      shell "printenv"
      shell(readFileFromWorkspace('mydsl/MIRROR-featchall-push_buildstep.sh'))
  }
}		// end MIRROR-fetachall-push


matrixJob(folderName + '/RELEASE-AGL-albacore') {
    // TODO
}		// end RELEASE-AGL-albacore


matrixJob(folderName + '/SNAPSHOT-AGL-master') {
  configure { project ->
      // Discard Old Builds: Yes / Strategy: Log Rotation / Max num of builds to keep: 2
      project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
          strategy(class: "hudson.tasks.LogRotator") {
              daysToKeep(-1)
              numToKeep(2)
              artifactDaysToKeep(-1)
              artifactNumToKeep(-1)
          }
      }
      // Advanced Project Options / Restrict where this project can be run: Yes / Label Expression: Yocto
      (project / 'assignedNode').value = 'yocto'
      (project / 'canRoam').value = 'false'
  }
  // Advanced Project Options / Use custom child workspace
  childCustomWorkspace('../${MACHINE}')
  // Build Triggers / Build periodically / Schedule: TODO
  // TODO
  // Configuration Matrix
  axes {
      label('label', 'yocto')
      text('MACHINE', 'qemux86-64', 'intel-corei7-64')
  }
  // Build > Add build step > Execute shell
  steps {
      shell "printenv"
      shell(readFileFromWorkspace('mydsl/SNAPSHOT-AGL-master_buildstep.sh'))
  }
  // Post-build Actions > Archive the artifacts
  publishers {
      archiveArtifacts('${MACHINE}_default.xml')
  }
}		// end SNAPSHOT-AGL-master

// EOF

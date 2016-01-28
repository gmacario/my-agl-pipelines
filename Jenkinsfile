// jenkins-build-agl-distro/Jenkinsfile

node('yocto') {
  // def gitUrl = 'https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo'
  // def gitBranch = 'master'
  
  git url: gitUrl, branch: gitBranch
  
  // DEBUG
  sh 'id'
  sh 'printenv'
  sh 'ps axf'
  sh 'df -h'
  sh 'ls -la'
  
  // DEBUG
  sh "repo init -m default.xml -u " + gitUrl
  sh "repo sync --force-sync"
 
  sh 'ls -la'
  // TODO
  
  // Leftovers from build-gdp:
  // TODO: sh 'bash -xec "source init.sh && bitbake genivi-demo-platform"'
  
  // TODO: Archive artifacts
} // node

// EOF

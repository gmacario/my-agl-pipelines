// build-agl-distro/Jenkinsfile

node('yocto') {
  // def gitUrl = "https://github.com/gmacario/genivi-demo-platform"
  // def gitBranch = "qemux86-64"
  
  git url: gitUrl, branch: gitBranch
  
  sh 'id'
  sh 'printenv'
  sh 'ps axf'
  sh 'df -h'
  sh 'ls -la'
  
  // DEBUG
  sh "repo init -m default.xml -u " + gitUrl
  sh "repo sync --force-sync"
 
  sh 'ls -la'
  
  // TODO: sh 'bash -xec "source init.sh && bitbake genivi-demo-platform"'
  // TODO: Archive artifacts
} // node

// EOF

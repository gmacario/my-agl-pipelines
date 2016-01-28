// build-agl-distro/Jenkinsfile

node('yocto') {
  // def gitUrl = "https://github.com/gmacario/genivi-demo-platform"
  // def gitBranch = "qemux86-64"
  
  git url: gitUrl, branch: gitBranch
  
  sh 'id'
  sh 'printenv'
  sh 'ps axf'
  sh 'df -h'
  
  // TODO: sh 'bash -xec "source init.sh && bitbake genivi-demo-platform"'
  // TODO: Archive artifacts
} // node

// EOF

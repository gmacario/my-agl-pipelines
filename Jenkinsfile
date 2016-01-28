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
  
  // Adapted from https://build.automotivelinux.org/job/CI-AGL-repo/
  sh '''#!/bin/bash -xe
#
mv repoclone repoclone2 || true
mkdir -p repoclone
ionice rm -rf repoclone2
cd repoclone
'''

  sh 'repo init -m default.xml -u ' + gitUrl

  sh '''#!/bin/bash -xe
#
# mkdir -p .repo/manifests/
# cp -L ../AGL-repo/default.xml .repo/manifests/
# cat .repo/manifests/default.xml
repo sync --force-sync
repo manifest -r
'''

  // TODO: Should parameterize args to envsetup.sh (qemux86-64, agl-image-ivi-build)
  sh '''#!/bin/bash -xe
#
mv agl-image-ivi-build agl-image-ivi-build2 || true
# mkdir -p ../downloads
# ionice rm -rf agl-image-ivi-build2
# mkdir -p ../state-cache
# eval export DL_DIR=$(pwd)/downloads/
# eval export SSTATE_DIR=$(pwd)/../sstate-cache/
# 
source meta-agl/scripts/envsetup.sh qemux86-64 agl-image-ivi-build
echo "DEBUG: After source meta-agl/scripts/envsetup.sh ..."
# ln -sf ../../downloads
# ln -sf ../../sstate-cache
bitbake agl-image-ivi
cat current_default.xml
'''

  // Leftovers from build-gdp:
  // TODO: sh 'bash -xec "source init.sh && bitbake genivi-demo-platform"'
  
  sh 'ls -la'
  // TODO
  
  // TODO: Archive artifacts
} // node

// EOF

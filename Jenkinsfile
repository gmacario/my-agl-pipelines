pipeline {
  agent {
    docker {
      image 'gmacario/build-yocto:latest'
    }
    
  }
  stages {
    stage('Checkout') {
      steps {
        ws(dir: 'agl') {
          echo 'Checkout stage'
          git(url: "${params.gitUrl}", branch: "${params.gitBranch}")
          sh 'id'
          sh 'printenv'
          sh 'ps axf'
          sh 'df -h'
          sh 'ls -la'
          sh '''#!/bin/bash -xe
#
# mv repoclone repoclone2 || true
# mkdir -p repoclone
# ionice rm -rf repoclone2
# cd repoclone
#
# EOF
'''
        sh "repo init -m default.xml -u ${params.gitUrl}"
        sh '''#!/bin/bash -xe
#
# mkdir -p .repo/manifests/
# cp -L ../AGL-repo/default.xml .repo/manifests/
# cat .repo/manifests/default.xml
repo sync --force-sync
repo manifest -r
#
# EOF
'''
        }
      }
    }
    stage('Build') {
      steps {
        ws(dir: 'agl') {
          echo 'Building'
          sh '''#!/bin/bash -xe
#
# mv agl-image-ivi-build agl-image-ivi-build2 || true
# mkdir -p ../downloads
# ionice rm -rf agl-image-ivi-build2
# mkdir -p ../state-cache
# eval export DL_DIR=$(pwd)/downloads/
# eval export SSTATE_DIR=$(pwd)/../sstate-cache/
# 
#
source meta-agl/scripts/aglsetup.sh
# -m raspberrypi3 -b agl-image-ivi-build agl-devel agl-netboot agl-appfw-smack agl-demo
#
# Workaround for "Please use a locale setting which supports utf-8"
# See https://github.com/gmacario/my-agl-pipelines/issues/9
export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
export LANGUAGE=en_US.UTF-8
#
# ln -sf ../../downloads
# ln -sf ../../sstate-cache
bitbake agl-image-ivi
#
# DEBUG
ls -la
# cat current_default.xml
ls -la tmp/
ls -la tmp/deploy/
ls -la tmp/deploy/images/
ls -la tmp/deploy/images/*/
#
# EOF
'''
        }
      }
    }
    stage('Test') {
      steps {
        parallel(
          "Chrome": {
            echo 'Testing in Chrome'
            
          },
          "Firefox": {
            echo 'Testing in Firefox'
            
          }
        )
      }
    }
    stage('Deploy') {
      steps {
        echo 'INFO: Deploying'
        sh '''#!/bin/bash -xe
# DEBUG
pwd
ls -la
ls -la agl-image-ivi-build/
ls -la agl-image-ivi-build/tmp/
ls -la agl-image-ivi-build/tmp/deploy/
ls -la agl-image-ivi-build/tmp/deploy/images/
ls -la agl-image-ivi-build/tmp/deploy/images/raspberrypi3/
# EOF'''
        archive 'agl-image-ivi-build/tmp/deploy/images/*/*.rootfs.manifest'
        archive 'agl-image-ivi-build/tmp/deploy/images/*/*.rootfs.rpi-sdimg'
      }
    }
  }
  parameters {
    string(name: 'gitUrl', defaultValue: 'https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo', description: 'Git URL where to checkout sources')
    string(name: 'gitBranch', defaultValue: 'master', description: 'branch to checkout')
    // string(name: 'machine', defaultValue: 'raspberrypi3', description: 'Target machine to build image for')
  }
}

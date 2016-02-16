#!/bin/bash -xe

# Inspired by https://build.automotivelinux.org/job/CI-meta-agl/

# DEBUG
env | sort
# DEBUG

test x == xCI-meta-agl && releng-scripts/CI-meta-agl/jenkins.sh

# EOF

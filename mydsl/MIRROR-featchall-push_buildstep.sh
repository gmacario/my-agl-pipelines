#!/bin/bash -xe

# Adapted from https://build.automotivelinux.org/job/MIRROR-featchall-push/
set | grep MACHINE
repo init -u https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo
repo sync --force-sync -d

mkdir -p ../downloads
mv fetchall fetchall2 || true
(ionice rm -rf fetchall2 &) || true
mkdir -p fetchall
source meta-agl/scripts/envsetup.sh ${MACHINE} fetchall

echo '' >>conf/local.conf
echo 'IMAGE_INSTALL_append = " CES2016-demo can-utils"' >>conf/local.conf
echo 'BB_GENERATE_MIRROR_TARBALLS = "1"' >>conf/local.conf
# echo '' >>conf/local.conf

ln -sf ../../downloads
bitbake -k -c fetchall world || true

cd downloads
rm -rf git2
touch 0000.done
find . -name '*.done' | xargs rm -f
echo "TODO:" rsync -avr . 172.30.4.151::repos/mirror/

# EOF

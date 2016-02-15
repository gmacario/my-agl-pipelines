#!/bin/bash -xe

# Adapted from https://build.automotivelinux.org/job/SNAPSHOT-AGL-master/

# DEBUG
env | sort
# DEBUG

set | grep MACHINE
repo init -u https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo
repo sync

repo manifest -r >${MACHINE}_default.xml

mv agl-snap-${MACHINE} agl-snap-${MACHINE}2 || true
(ionice rm -rf agl-snap-${MACHINE}2 &) || true

if test x"meta-renesas" == x"$MYPROJECT" ; then
  export MACHINE=porter
fi

if test x"" == x"$MACHINE" ; then
  export MACHINE=qemux86
fi

mkdir -p ../downloads
mkdir -p ../sstate-cache

eval export DL_DIR=$(pwd)/../downloads/
eval export SSTATE_DIR=$(pwd)/../sstate-cache/

# source the env
source meta-agl/scripts/envsetup.sh ${MACHINE} agl-snap-${MACHINE}

# link the shared downloads and sstate-cache
ln -sf ../../downloads
ln -sf ../../sstate-cache

# echo '' >> conf/local.conf
echo 'INHERIT += "archiver"' >>conf/local.conf
echo 'ARCHIVER_MODE[src] = "original"' >>conf/local.conf
echo 'IMAGE_INSTALL_append = " CES2016-demo mc"' >>conf/local.conf
test xqemux86 == x${MACHINE} -o xqemux86-64 == x${MACHINE} && echo 'IMAGE_FSTYPES += "vmdk"' >>conf/local.conf

# finally, build the agl-demo-platform
bitbake agl-demo-platform || exit 1

test xporter == x${MACHINE} && echo TODO

export mydate=$(date +%F)-b${BUILD_ID}
mv SNAPSHOT SNAPSHOT2 || true
rm -rf SNAPSHOT2
mkdir -p SNAPSHOT/${mydate}/${MACHINE}
export DEST=$(pwd)/SNAPSHOT/${mydate}/${MACHINE}
export RSYNCSRC=$(pwd)/SNAPSHOT/
echo "TODO:" rsync -avr --progress --delete tmp/deploy ${DEST}

# TODO

cp ../${MACHINE}_default.xml ${DEST}/${MACHINE}_default.xml
cp conf/local.conf ${DEST}/local.conf
echo https://build.automotivelinux.org/job/SNAPSHOT-AGL-master/MACHINE=qemux86-64/126/
tree ${DEST}/${MACHINE}

# TODO

echo "TODO:" rsync -avr ${RSYNCSRC}/ 172.30.4.151::repos/snapshots/master/

pushd ${RSYNCSRC}
rm -rf latest
ln -sf ${mydate} latest
echo ${mydate} >latest.txt
popd

echo "TODO:" rsync -alvr ${RSYNCSRC}/ 172.30.4.151::repos/snapshots/master/

# EOF

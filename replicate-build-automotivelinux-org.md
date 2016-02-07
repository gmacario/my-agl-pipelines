# replicate-build-automotivelinux-org

Instructions to reproduce a similar configuration of the [Jenkins](https://jenkins-ci.org/) CI/CD server at <https://build.automotivelinux.org/> used for building from sources the [AGL Distribution](https://wiki.automotivelinux.org/agl-distro).

## Preconditions

* Jenkins successfully installed through [easy-jenkins](https://github.com/gmacario/easy-jenkins)
* Jenkins dashboard accessible at `${JENKINS_URL}` (example: <http://192.168.99.100:9080/>)

## Step-by-step instructions

### Create Folder "AGL"

Browse Jenkins Dashboard at `${JENKINS_URL}`, then click **New Item**

* Item name: `AGL`
* Type: **Folder**

then click **OK**.

Inside the folder configuration page, fill-in the following information:

* Name: `AGL`
* Display name: `build.automotivelinux.org`
* Description: `Replica of https://build.automotivelinux.org/`

then click **Save**.

### Create Job "releng-scripts"

<!-- (2016-01-29 18:27 CET) -->

Browse `${JENKINS_URL}/job/AGL/`, then click **New item**

* Item Name: `releng-scripts`
* Type: **Freestyle project**

then click **OK**.

Inside the project configuration page, fill-in the following information:

* Project name: `releng-scripts`
* Description: (empty)


* Source Code Management: Git
  - Repositories
    - Repository URL: `https://gerrit.automotivelinux.org/gerrit/AGL/releng-scripts`
    - Credentials: - none -
    - Branches to build
      - Branch Specifier (blank for 'any'): `*/master`
    - Repository browser: (Auto)

then click **Save**.

Browse `${JENKINS_URL}/job/AGL/job/releng-scripts/`, then click **Build Now**


### Create Job "MIRROR-fetchall-push"

<!-- (2016-02-02 15:54 CET) -->

Browse `${JENKINS_URL}/job/AGL/`, then click **New item**

* Item Name: `MIRROR-featchall-push` (the typo is intentional)
* Type: **Multi-configuration project**

then click **OK**.

Inside the project configuration page, fill-in the following information:

* Multi-configuration project name: `MIRROR-featchall-push`
* Description: (empty)
* Discard Old Builds: Yes
  - Strategy: Log Rotation
    - Days to keep builds: (empty)
    - Max # of builds to keep: 2


* Advanced Project Options
  - Restrict where this project can be run: Yes
    - Label Expression: `yocto`
  - Use custom child workspace
    - Child Directory: `../${MACHINE}`
    - Display Name: (empty)


* Build Triggers
  - Build periodically
    - Schedule: TODO


* Configuration Matrix
  - Add axis > Slaves
    - Name: `label`
    - Node/Label
      - Labels: `yocto`
  - Add axis > User-defined Axis
    - Name: `MACHINE`
    - Values:

      ```
      qemux86
      qemux86-64
      porter
      ```
  - Run each configuration sequentially: Yes

* Build > Add build step > Execute shell
  - Command:

```
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
```

then click **Save**.

<!-- (2016-02-02 15:56 CET) -->

Browse `${JENKINS_URL}/job/AGL/job/MIRROR-featchall-push/`, then click **Build Now**

**NOTE**: Job "MIRROR-featchall-push/MACHINE=porter" will fail with the following error:

```
...
+ source meta-agl/scripts/envsetup.sh porter fetchall
++ '[' -z porter ']'
++ MACHINE=porter
++ '[' -z '' ']'
++ TEMPLATECONF=/home/jenkins/workspace/AGL/MIRROR-featchall-push/MACHINE/porter/label/yocto/meta-agl-demo/templates/porter/conf
++ case "$MACHINE" in
++ COPY_MM_SCRIPT=meta-renesas/meta-rcar-gen2/scripts/setup_mm_packages.sh
++ '[' -f meta-renesas/meta-rcar-gen2/scripts/setup_mm_packages.sh ']'
++ . meta-renesas/meta-rcar-gen2/scripts/setup_mm_packages.sh
+++ MM_PKG_REVISION=20151130
+++ MM_PKG_NAME=R-Car_Series_Evaluation_Software_Package
+++ MM_PKG_USERLAND=for_Linux
+++ MM_PKG_KERNEL=of_Linux_Drivers
+++ ZIP_1=R-Car_Series_Evaluation_Software_Package_for_Linux-20151130.zip
+++ ZIP_2=R-Car_Series_Evaluation_Software_Package_of_Linux_Drivers-20151130.zip
+++ COPY_GFX_SCRIPT=copy_gfx_software_porter.sh
+++ COPY_MM_SCRIPT=copy_mm_software_lcb.sh
+++ test -f /home/jenkins/.config/user-dirs.dirs
+++ DOWNLOAD_DIR=/home/jenkins/Downloads
++ copy_mm_packages porter
++ '[' '!' -d binary-tmp ']'
++ '[' -f /home/jenkins/Downloads/R-Car_Series_Evaluation_Software_Package_for_Linux-20151130.zip -a -f /home/jenkins/Downloads/R-Car_Series_Evaluation_Software_Package_of_Linux_Drivers-20151130.zip ']'
++ echo -n 'The graphics and multimedia acceleration packages for '
The graphics and multimedia acceleration packages for ++ echo -e 'the R-Car M2 Porter board can be download from :'
the R-Car M2 Porter board can be download from :
++ echo -e '  <http://www.renesas.com/secret/r_car_download/rcar_demoboard.jsp>'
  <http://www.renesas.com/secret/r_car_download/rcar_demoboard.jsp>
++ echo -e

++ echo -n 'These 2 files from there should be store in your'
These 2 files from there should be store in your++ echo -e ''\''/home/jenkins/Downloads'\'' directory.'
'/home/jenkins/Downloads' directory.
++ echo -e '  R-Car_Series_Evaluation_Software_Package_for_Linux-20151130.zip'
  R-Car_Series_Evaluation_Software_Package_for_Linux-20151130.zip
++ echo -e '  R-Car_Series_Evaluation_Software_Package_of_Linux_Drivers-20151130.zip'
  R-Car_Series_Evaluation_Software_Package_of_Linux_Drivers-20151130.zip
++ return 1
Build step 'Execute shell' marked build as failure
Notifying upstream projects of job completion
Finished: FAILURE
```

To fix the error proceed as instructed:

1. Browse <http://www.renesas.com/secret/r_car_download/rcar_demoboard.jsp>
2. Download the following files (to download, log-in to My Renesas or create a My Renesas account):
  * `R-Car_Series_Evaluation_Software_Package_for_Linux-20151130.zip`
  * `R-Car_Series_Evaluation_Software_Package_of_Linux_Drivers-20151130.zip`
3. Copy those two files to directory `/home/Jenkins/Download` of slave `build-yocto-slave`:

```
TODO
```

**FIXME**: As of 2016-01-29, the files downloaded from the My Renesas website are newer (`*-20151228.zip`) than those expected by the script `meta-renesas/meta-rcar-gen2/scripts/setup_mm_packages.sh`.


### Create Job "SNAPSHOT-AGL-master"

<!-- (2016-02-07 17:55 CET) -->

Browse `${JENKINS_URL}/job/AGL/`, then click **New item**

* Item Name: `SNAPSHOT-AGL-master`
* Type: **Multi-configuration project**

then click **OK**.

Inside the project configuration page, fill-in the following information:

* Project name: `SNAPSHOT-AGL-master`
* Description: (empty)
* Discard Old Builds: Yes
  - Strategy: Log Rotation
    - Days to keep builds: (empty)
    - Max # of builds to keep: 2

* Advanced Project Options
  - Restrict where this project can be run: Yes
    - Label Expression: `yocto`
  - Use custom child workspace
    - Child Directory: `../${MACHINE}`
    - Display Name: (empty)

* Build Triggers
  - Build periodically
    - Schedule: TODO

* Configuration Matrix
  - Add axis > Slaves
    - Name: `label`
    - Node/Label
      - Labels: `yocto`
  - Add axis > User-defined Axis
    - Name: `MACHINE`
    - Values:

      ```
      qemux86-64
      intel-corei7-64
      ```

* Build 
  - Add build step > Execute shell
    - Command:

```
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
```

* Post-build Actions
  - Add post-build action > Archive the artifacts
    - File to archive: `${MACHINE}_default.xml` (i.e.: `intel-core7-64_default.xml`)

then click **Save**.

Browse `${JENKINS_URL}/job/AGL/job/SNAPSHOT-AGL-master/`, then click **Build Now**

<!-- (2016-02-07 17:58 CET) -->

Result: TODO


### Create Job "CI-AGL-repo"

<!-- (2016-01-29 17:27 CET) -->

Browse `${JENKINS_URL}/job/AGL/`, then click **New item**

* Item Name: `CI-AGL-repo`
*** Type: **Freestyle project

then click **OK**.

Inside the project configuration page, fill-in the following information:

* Project name: `CI-AGL-repo`
* Description: (empty)
* Discard Old Builds: Yes
  - Strategy: Log Rotation
    - Days to keep builds: (empty)
    - Max # of builds to keep: 1
* Restrict where this project can be run: Yes
  - Label Expression: `yocto`


* Source Code Management: Git
  - Repositories
    - Repository URL: `https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo`
    - Credentials: - none -
    - Branches to build
      - Branch Specifier (blank for 'any'): `*/master`
    - Repository browser: (Auto)


* Build > Add build step > Execute shell
  - Command:

```
#!/bin/bash -xe

# https://build.automotivelinux.org/job/CI-AGL-repo/

mv repoclone repoclone2 || true
mkdir -p repoclone
ionice rm -rf repoclone2
cd repoclone

repo init -m default.xml -u https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo

mkdir -p .repo/manifests/
cp -L ../AGL-repo/default.xml .repo/manifests/
cat .repo/manifests/default.xml

repo sync --force-sync
repo manifest -r

mv agl-image-ivi-build agl-image-ivi-build2 || true

mkdir -p ../downloads
ionice rm -rf agl-image-ivi-build2
mkdir -p ../sstate-cache

eval export DL_DIR=$(pwd)/../downloads/
eval export SSTATE_DIR=$(pwd)/../sstate-cache/

source meta-agl/scripts/envsetup.sh qemux86-64 agl-image-ivi-build
#
# echo '' >>conf/local.conf
# echo 'IMAGE_INSTALL_append = " CES2016-demo can-utils"' >>conf/local.conf
# echo 'BB_GENERATE_MIRROR_TARBALLS = "1"' >>conf/local.conf
# echo '' >>conf/local.conf

ln -sf ../../downloads
ln -sf ../../sstate-cache

echo TODO: bitbake agl-iamge-ivi

# EOF
```

* Build > Add build step > Execute shell
  - Command:

```
#!/bin/bash -xe

cat current_default.xml

# EOF
```

then click **Save**.

TODO


### Create Job "CI-AGL-DemoApps-CES2016"

TODO


### Create View "AGL_Build_Pipeline"

Browse `${JENKINS_URL}/job/AGL`, then click **New View**

* View name: `AGL_Build_Pipeline`
* Type: **Build Pipeline View**

then click **OK**. You will now be able to edit the View configuration:

* Name: `AGL_Build_Pipeline`
* Description: (empty)
* Build Pipeline View Title: `AGL Build Pipeline`
* Layout: Based on upstream/downstream relationship
- Select Initial Job: AGL >> AGL-Rebuild
- TODO

then click **Save**.

TODO

<!-- EOF -->

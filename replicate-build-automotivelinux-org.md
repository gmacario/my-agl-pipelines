# replicate-build-automotivelinux-org

Instructions which try to replicate the configuration at <https://build.automotivelinux.org/> for building the [AGL Distribution](https://wiki.automotivelinux.org/agl-distro) using [Jenkins](https://jenkins-ci.org/).

## Step-by-step instructions

### Create Folder "AGL"

Browse Jenkins Dashboard at `${JENKINS_DASHBOARD}` > New Item

* Item name: `AGL`
* Type: Folder

then click **OK**. You will now be able to edit the Folder configuration:

* Name: `AGL`
* Display name: `build.automotivelinux.org`
* Description: `Replica of https://build.automotivelinux.org/`

then click **Save**.

### Create Job "MIRROR-fetchall-push"

Browse `${JENKINS_DASHBOARD}/job/AGL/` > New item

* Item Name: `MIRROR-featchall-push` (notice the typo)
* Type: Multi-configuration project

then click **OK**. You will now be able to edit the project configuration:

* Multi-configuration project name: `MIRROR-featchall-push`
* Description: (empty)
* Discard Old Builds: Yes
  - Strategy: Log Rotation
    - Days to keep builds: (empty)
    - Max # of builds to keep: 3
* Build Triggers
  - Build periodically
    - Schedule: TODO
* Configuration Matrix
  - Add axis > Slaves
    - Name: `label`
    - Node/Label
      - Individual nodes: `build-yocto-slave`
  - Add axis > User-defined Axis
    - Name: `MACHINE`
    - Values:

      ```
      qemux86
      qemux86-64
      porter
      ```

* Build > Add build step > Execute shell
  - Command:

```
#!/bin/bash -xe

# Adapted from https://build.automotivelinux.org/job/MIRROR-featchall-push/
set | grep MACHINE
repo init -u https://gerrit.automotivelinux.org/gerrit/AGL/AGL-repo
repo sync --force-sync -d

mkdir -p ../downloads
test -d fetchall && mv fetchall fetchall2
test '!' -d fetchall && mkdir fetchall
rm -rf fetchall2
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
find . -name '*.done' | xargs rm
echo "TODO:" rsync -avr . 172.30.4.151::repos/mirror/

# EOF
```

then click **Save**.

**NOTE**: Job "MIRROR-featchall-push/MACHINE=porter" will fail with the following error:

```
...
+ source meta-agl/scripts/envsetup.sh porter fetchall
++ '[' -z porter ']'
++ MACHINE=porter
++ '[' -z '' ']'
++ TEMPLATECONF=/home/jenkins/workspace/MIRROR-featchall-push/MACHINE/porter/label/build-yocto-slave/meta-agl-demo/templates/porter/conf
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
2. Copy the two files to directory `/home/Jenkins/Download` of slave `build-yocto-slave`

```
TODO
```

### Create Job "CI-AGL-repo"

TODO

### Create Job "SNAPSHOT-AGL-master"

TODO

<!-- (2015-12-04 10:45 CET)

Jenkins Dashboard: New Item
Item name: SNAPSHOT-AGL-master
Type: Multi-configuration project
Click "OK"
Configure job "SNAPSHOT-AGL-master"
Multi-configuration project name: SNAPSHOT-AGL-master
Configuration Matrix > Add axis > Label expression
Name: MACHINE
Label Expressions: qemux86 qemux86-64
Build > Add build step > Execute shell
Command: TODO
Click "Save"
NOTE: Inside the shell script, should rename SNAPSHOT ==> BUILDDIR

-->

### Create Job "CI-AGL-DemoApps-CES2016"

TODO

### Create View "AGL_Build_Pipeline"

Browse `${JENKINS_DASHBOARD}/job/AGL` > New View

* View name: `AGL_Build_Pipeline`
* Type: Build Pipeline View

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
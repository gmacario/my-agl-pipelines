# jenkins-build-agl-distro/replicate-build-automotivelinux-org.md.md

Instructions which attempt at replicating the configuration at <https://build.automotivelinux.org/> in order to build the AGL Distribution with Jenkins.

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

### Create job "MIRROR-fetchall-push"

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
* Advanced Project Options
  - Restrict where this project can be run: Yes
    - Label Expression: `yocto`
* Build Triggers
  - Build periodically
    - Schedule: TODO
* Configuration Matrix > Add axis > User-defined Axis
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

TODO

#!/bin/bash

export VER="1.0.0-SNAPSHOT"
export TZ=Europe/Berlin
export TIMESTAMP=`(date +'%Y%m%d%H%M%S')`
export ARTIFACT_DIR="target"

# see https://graysonkoonce.com/getting-the-current-branch-name-during-a-pull-request-in-travis-ci/
export BRANCH=$(if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then echo $TRAVIS_BRANCH; else echo $TRAVIS_PULL_REQUEST_BRANCH; fi)
echo "TARVIS_TAG=${TRAVIS_TAG}, TRAVIS_BRANCH=$TRAVIS_BRANCH, PR=$PR, BRANCH=$BRANCH, VER=${VER}, TIMESTAMP=${TIMESTAMP}"
	
mv "${ARTIFACT_DIR}"/prismnpe-"${VER}"-win.jar "${ARTIFACT_DIR}"/dodo-"${VER}"-win-"${TIMESTAMP}".jar
mv "${ARTIFACT_DIR}"/prismnpe-"${VER}"-mac.jar "${ARTIFACT_DIR}"/dodo-"${VER}"-mac-"${TIMESTAMP}".jar
mv "${ARTIFACT_DIR}"/prismnpe-"${VER}"-linux.jar "${ARTIFACT_DIR}"/dodo-"${VER}"-linux-"${TIMESTAMP}".jar
export ARTIFACT_WIN=prismnpe-"${VER}"-win-"${TIMESTAMP}".jar
export ARTIFACT_MAC=prismnpe-"${VER}"-mac-"${TIMESTAMP}".jar
export ARTIFACT_LINUX=prismnpe-"${VER}"-linux-"${TIMESTAMP}".jar
echo "ARTIFACT_WIN=${ARTIFACT_WIN}"
echo "ARTIFACT_MAC=${ARTIFACT_MAC}"
echo "ARTIFACT_LINUX=${ARTIFACT_LINUX}"
#!/usr/bin/env bash

if [ $TRAVIS_BRANCH == 'master' ];then
    ./gradlew build uploadArchives
else
    ./gradlew build
fi


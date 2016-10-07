#!/bin/bash

bin_path=$(cd `dirname "$0"`; pwd)

export CLASSPATH=${bin_path}/../lib/*
export CLASSPATH=${bin_path}/../*:${CLASSPATH}
export CLASSPATH=${bin_path}/../.:${CLASSPATH}
export CLASSPATH=${bin_path}/../conf/:${CLASSPATH}

java com.someth.www.App

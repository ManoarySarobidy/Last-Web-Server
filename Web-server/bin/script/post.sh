#!/bin/bash

# to run it we need to tell that 
# $1 is the method
# $2 is the file requested
# $3 is the data-post
# $4 is the data-get

# Script to run a php post file via the cgi

GATEWAY_INTERFACE=CGI/1.1
REQUEST_METHOD=$1
REDIRECT_STATUS=true
content=$3
CONTENT_LENGTH=${#content}
QUERY_STRING=$4
CUR_DIR=$PWD
PAR_DIR=$(dirname "$CUR_DIR")
SRC_DIR="$PAR_DIR/src/exception/"
EX_DIR="$PAR_DIR"
SCRIPT_FILENAME=$EX_DIR/$2
SCRIPT_NAME=$0

if [[ $REQUEST_METHOD == "POST" ]]; then
	CONTENT_TYPE=application/x-www-form-urlencoded; charset=UTF-8;
else
	CONTENT_TYPE=text/html; charset=UTF-8;
fi

export REDIRECT_STATUS
export GATEWAY_INTERFACE
export REQUEST_METHOD
export SCRIPT_FILENAME
export SCRIPT_NAME
export CONTENT_LENGTH
export CONTENT_TYPE
export QUERY_STRING
export SRC_DIR
export EX_DIR
echo $EX_DIR
echo $content | php-cgi 2> "$SRC_DIR/phpError.html"

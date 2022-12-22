#!/bin/bash

# to run it we need to tell that 
# $1 is the method
# $2 is the file requested
# $3 is the data-post
# $4 is the data-get

# Used just to throw error

# Script to run a php post file via the cgi and see the result

GATEWAY_INTERFACE=CGI/1.1
REQUEST_METHOD=$1
REDIRECT_STATUS=true
SCRIPT_FILENAME=$2
content=$3
CONTENT_LENGTH=${#content}
QUERY_STRING=$4
SRC_DIR="../"

if [[ $REQUEST_METHOD == "POST" ]]; then

	CONTENT_TYPE=application/x-www-form-urlencoded; charset=UTF-8;

else

	CONTENT_TYPE=text/html; charset=UTF-8;

fi

export REDIRECT_STATUS
export GATEWAY_INTERFACE
export REQUEST_METHOD
export SCRIPT_FILENAME
export CONTENT_LENGTH
export CONTENT_TYPE
export QUERY_STRING
export SRC_DIR

echo $content | php-cgi 2> "$SRC_DIR/src/exception/phpError.html"

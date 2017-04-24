#!/bin/sh

if [ "$1" = "--help" ] || [ $# -eq 1 ] || [ $# -gt 2 ] ; then
    echo "usage: startManCenter.sh"
    echo "usage: startManCenter.sh [port] [path]"
    exit;
fi

mkdir -p "`dirname $0`/work"

if [ $# -eq 2 ] ; then
	java -jar mancenter-3.7.5.war "$1" "$2"
else 
	java -jar mancenter-3.7.5.war
fi
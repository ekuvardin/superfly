#!/bin/bash

. ../functions.sh

runScript all-proc.sql

#mkdir -p target
#echo "Installing MySQL procedures:"
#mysql -b -vv -u $MYSQL_USERNAME -p$MYSQL_PASSWORD $MYSQL_DATABASE --show-warnings < all-proc.sql  > target/all-#proc.log 
#echo "Current warnings:"
#grep " warning" target/all-proc.log 
#grep "Note" target/all-proc.log

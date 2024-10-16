#!/bin/bash

# This script converts a project from Intellij IDEA to Netbeans

if [ ! "$1" ]; then
    printf "Usage: convert2netbeans.sh <project_name>\n"
    exit 1
fi

PROJECT_NAME=$1
DIFF_PROJECT_NAME=$1_

echo "$PROJECT_NAME" | mkt spawn -n nb-project -o "$DIFF_PROJECT_NAME"

# Copy the project files
cp -r "$PROJECT_NAME/src" "$DIFF_PROJECT_NAME"

#Compresse the project
zip -r "$PROJECT_NAME.zip" "$DIFF_PROJECT_NAME"

rm -r "$DIFF_PROJECT_NAME"
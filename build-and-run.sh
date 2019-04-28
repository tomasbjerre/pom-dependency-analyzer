#!/bin/bash

#
# I use this as a cron job to periodically analyze dependencies.
#

POM_FOLDER=~/.m2/repository/se/bjurr

./gradlew clean build \
 && find $POM_FOLDER -type f -name "*.pom" \
  | xargs -I % sh -c '([ ! -e %.dot ] || grep -Fq "\-SNAPSHOT" %) \
  && mvn dependency:tree -DoutputType=dot -Doutput=%.dot -f % \
  && ./gradlew run --args="-d %.dot" \
  && echo $(find ~/.m2 -name "*.pom.dot" | wc -l)/$(find ~/.m2 -name "*.pom" | wc -l)\
  || echo Skipping: %'

# Pom Dependency Analyzer
[![Build Status](https://travis-ci.org/tomasbjerre/pom-dependency-analyzer.svg?branch=master)](https://travis-ci.org/tomasbjerre/pom-dependency-analyzer)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/se.bjurr.pomdependencyanalyzer/pom-dependency-analyzer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/se.bjurr.pomdependencyanalyzer/pom-dependency-analyzer)
[![Bintray](https://api.bintray.com/packages/tomasbjerre/tomasbjerre/se.bjurr.pomdependencyanalyzer%3Apom-dependency-analyzer/images/download.svg) ](https://bintray.com/tomasbjerre/tomasbjerre/se.bjurr.pomdependencyanalyzer%3Apom-dependency-analyzer/_latestVersion)

This is a command line tool that parses the `dot` output of `mvn dependency:tree` and manages a set of `json`-files with its findings. The output can be used with [Pom Dependency Analyzer Web](https://github.com/tomasbjerre/pom-dependency-analyzer-web) to create a browsable webpage.

Intended to be used to identify, not only **dependencies** of an artifact, but also its **dependants**. Something that is often a problem when automating build processes.

You may try this on a public repository but be careful! Is is very likely a violation of its terms of service. See: https://central.sonatype.org/terms.html

Example (change to whatever pom-file you want to examine):

```shell
POM_FILE=~/.m2/repository/se/bjurr/violations/violations-maven-plugin/1.19/violations-maven-plugin-1.19.pom \
 && mvn dependency:tree -DoutputType=dot -Doutput=$POM_FILE.dot -f $POM_FILE \
 && ./pom-dependency-analyzer -d $POM_FILE.dot
```

Or all `pom`:s in a specific folder:
```shell
find ~/.m2/repository/se/bjurr -type f -name "*.pom" \
 | xargs -I % sh -c '([ ! -e %.dot ] || grep -Fq "\-SNAPSHOT" %) \
 && mvn dependency:tree -DoutputType=dot -Doutput=%.dot -f % \
 && ./pom-dependency-analyzer -d %.dot \
 && echo $(find ~/.m2 -name "*.pom.dot" | wc -l)/$(find ~/.m2 -name "*.pom" | wc -l)\
 || echo Skipping: %'
```

You may use [Pom Downloader](https://github.com/tomasbjerre/pom-downloader) to download pom-files.

# Usage

```shell
-ci, --create-image <boolean>              Create an image showing the result 
                                           of analysis.
                                           <boolean>: true or false
                                           Default: false
-d, --dot <path>                           This is the output file from 'mvn 
                                           dependency:tree -Doutput=file.dot -
                                           DoutputType=dot'
                                           <path>: a file path
                                           Default: /home/bjerre/workspace/pom-dependency-analyzer/.
-h, --help <argument-to-print-help-for>    <argument-to-print-help-for>: an argument to print help for
                                           Default: If no specific parameter is given the whole usage text is given
-md, --metadata <string>                   These key/values will be stored 
                                           together with the artifact. Can be used to 
                                           record things like artifacts git repo or 
                                           artifacts Jenkins job URL. [Supports Multiple occurrences]
                                           <string>: any string
                                           Default: Empty list
-sf, --storage-folder <string>             This is where it will store files.
                                           <string>: any string
                                           Default: <user home>/.m2
```

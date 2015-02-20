@echo OFF
set XML_FILE=dblp_filtered.xml
set DEBUG=false

set PARSER=%1

if "%PARSER%"=="" set PARSER=non-xml-parser

ECHO Parser used:%PARSER%

set JAVA_OPTS=-Xmx1G -XX:+UseConcMarkSweepGC -DentityExpansionLimit=10000000

java -jar %JAVA_OPTS% "%CD%/dblp-parser/target/dblp-parser-1.0.0.jar" "%XML_FILE%" %PARSER%



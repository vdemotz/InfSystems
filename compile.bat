@echo off

rd /s /q "%USERPROFILE%\.m2\repository\ch\ethz"

mvn clean package
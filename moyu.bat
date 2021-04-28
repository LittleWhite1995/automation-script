@echo off
title 接口自动化测试 %DATE:~0,4%-%DATE:~5,2%-%DATE:~8,2% %TIME:~0,2%:%TIME:~3,2%:%TIME:~6,2%
setLocal EnableDelayedExpansion
set JAVA_HOME=D:\Java\jdk1.8.0_202
set PATH=%JAVA_HOME%/bin;%JAVA_HOME%/jre/bin
set CLASSPATH="D:\ProjectStudy\script\automation-script"
for /R D:\ProjectStudy\script\automation-script\lib %%a in (*.jar) do (
set CLASSPATH=!CLASSPATH!;%%a
)
java -Xms128m -Xmx512m -cp %CLASSPATH% FictionScript
pause
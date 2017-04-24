@ECHO OFF

set argC=0
for %%x in (%*) do Set /A argC+=1
set help=false

if %argC% equ 1 set help=true
if %argC% gtr 2 set help=true
if %argC% equ 1 ( echo "" if %1% equ "--help" help=true )

if %help% == true (
    echo usage: startManCenter.bat
    echo usage: startManCenter.bat [port] [path]
)

if not exist %0\..\work mkdir %0\..\work

if %argC% == 2 (
    java -jar mancenter-3.7.5.war %1 %2
)

if %argC% == 0 (
    java -jar mancenter-3.7.5.war
)

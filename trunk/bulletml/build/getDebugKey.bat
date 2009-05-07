setlocal

if not "%JAVA_HOME%" == "" goto gotJavaHome

set JAVA_HOME=C:\Program Files\Java\jdk\jdk1.6.0_12

set PATH=%PATH%;%JAVA_HOME%\bin


rem ----------------------------------------------------------------------
rem Set defns
rem ----------------------------------------------------------------------
call definitions.bat

rem ----------------------------------------------------------------------
rem get debug MD5 fingerprint  
rem ----------------------------------------------------------------------

keytool -list -alias androiddebugkey -keystore "%DEBUG_KEYSTORE%" -storepass android -keypass android

endlocal
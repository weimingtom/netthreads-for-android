setlocal

if not "%JAVA_HOME%" == "" goto gotJavaHome

set JAVA_HOME=C:\Program Files\Java\jdk\jdk1.6.0_12

set PATH=%PATH%;%JAVA_HOME%\bin

rem ----------------------------------------------------------------------
rem Set defns
rem ----------------------------------------------------------------------
call definitions.bat
call C:\Development\java\android\sdk\keystore\bulletml\keystore-properties.bat

rem ----------------------------------------------------------------------
rem get release  MD5 fingerprint  
rem ----------------------------------------------------------------------

echo %KEYPASS%
echo %STOREPASS%
keytool -list -alias bulletml -keystore "%RELEASE_KEYSTORE%" -storepass %SIGNPASS% -keypass %KEYPASS%

endlocal
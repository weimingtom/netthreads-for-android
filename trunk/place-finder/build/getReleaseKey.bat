rem ----------------------------------------------------------------------
rem Set defns
rem ----------------------------------------------------------------------
call definitions.bat
call C:\Development\java\android\sdk\keystore\place-finder\keystore-properties.bat

rem ----------------------------------------------------------------------
rem get release  MD5 fingerprint  
rem ----------------------------------------------------------------------

echo %KEYPASS%
echo %STOREPASS%
keytool -list -alias placefinder -keystore "%RELEASE_KEYSTORE%" -storepass %SIGNPASS% -keypass %KEYPASS%

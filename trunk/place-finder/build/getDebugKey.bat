rem ----------------------------------------------------------------------
rem Set defns
rem ----------------------------------------------------------------------
call definitions.bat

rem ----------------------------------------------------------------------
rem get debug MD5 fingerprint  
rem ----------------------------------------------------------------------

keytool -list -alias androiddebugkey -keystore "%DEBUG_KEYSTORE%" -storepass android -keypass android
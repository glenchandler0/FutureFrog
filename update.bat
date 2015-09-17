For %%F IN (app\build\intermediates\res\debug\drawable\*.png) DO (
	del /F /Q %%F
)
FOR %%G IN (app\src\main\res\drawable\*.png) DO (
	xcopy %%G "app\build\intermediates\res\debug\drawable" /E
)
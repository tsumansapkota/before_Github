Dim WinScriptHost
Set WinScriptHost = CreateObject("WScript.Shell")
'Do while(1)

WinScriptHost.Run Chr(34) & "::{450d8fba-ad25-11d0-98a8-0800361b1103}\Rainmeter\Skins\Suman's\loadshedding\loads.exe" & Chr(34), 0
'Wscript.Sleep(60000)
'Loop
'WinScriptHost.Run "taskkill/f/im loads.exe",,True
Set WinScriptHost = Nothing

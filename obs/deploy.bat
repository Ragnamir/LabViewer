@echo off
PsExec64.exe \\%1 -u Administrator -p AIClab! -h -i msiexec.exe -i "\\192.168.37.105\shared\BusyBeacon.msi" -qn
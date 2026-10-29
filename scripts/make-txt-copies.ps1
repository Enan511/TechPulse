# Makes the Brightspace submission files: every .java file copied as both .java and .txt
# into the submission/ folder (the course does not accept zip files).
# Run from the project root in the VS Code terminal:   .\scripts\make-txt-copies.ps1
# If Windows blocks the script, run this first:         Set-ExecutionPolicy -Scope Process Bypass

$root = Split-Path -Parent $PSScriptRoot
$out = Join-Path $root "submission"
New-Item -ItemType Directory -Force $out | Out-Null

Get-ChildItem -Path (Join-Path $root "src") -Filter *.java -Recurse | ForEach-Object {
    Copy-Item $_.FullName (Join-Path $out $_.Name) -Force
    Copy-Item $_.FullName (Join-Path $out ($_.BaseName + ".txt")) -Force
}

Write-Host "Done. Files are in $out"

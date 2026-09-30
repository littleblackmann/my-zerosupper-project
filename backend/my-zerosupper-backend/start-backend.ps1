$ErrorActionPreference = 'Stop'

$backendRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$localEnvironment = Join-Path $backendRoot '.env.local.ps1'

if (-not (Test-Path -LiteralPath $localEnvironment)) {
    $randomBytes = New-Object byte[] 18
    [Security.Cryptography.RandomNumberGenerator]::Fill($randomBytes)
    $generatedPassword = [Convert]::ToBase64String($randomBytes).Replace('+', '-').Replace('/', '_').TrimEnd('=')
    $environmentContent = @(
        "`$env:APP_ADMIN_EMAIL = 'admin@zerosupper.local'"
        "`$env:APP_ADMIN_PASSWORD = '$generatedPassword'"
    )
    Set-Content -LiteralPath $localEnvironment -Value $environmentContent -Encoding utf8
    Write-Host '已建立本機管理員帳號：'
    Write-Host '  帳號: admin@zerosupper.local'
    Write-Host "  Password: $generatedPassword"
    Write-Host "密碼只保存在 $localEnvironment（已被 Git 忽略）。"
}

. $localEnvironment
Set-Location -LiteralPath $backendRoot
& .\gradlew.bat bootRun

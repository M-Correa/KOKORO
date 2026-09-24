$base = 'https://pc4gr3cx-5123.brs.devtunnels.ms'
$headers = @{'X-Tunnel-Skip-Anti-Abuse-Page'='true'}

$endpoints = @('/api/Dashboard', '/api/Wardrobe', '/api/Personality')
foreach ($ep in $endpoints) {
  try {
    $res = Invoke-RestMethod -Uri "$base$ep" -Headers $headers -TimeoutSec 10
    Write-Host "=== GET $ep SUCCESS ==="
    Write-Host ($res | ConvertTo-Json -Depth 4)
  } catch {
    Write-Host "=== GET $ep FAILED ==="
    Write-Host $_.Exception.Message
  }
}

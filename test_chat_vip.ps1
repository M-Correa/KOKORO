$base = 'https://pc4gr3cx-5123.brs.devtunnels.ms'
$headers = @{
  'X-Tunnel-Skip-Anti-Abuse-Page' = 'true'
  'Content-Type' = 'application/json'
}

# 1. Test Chat
try {
  $chatBody = '{"message":"Hola Kokoro, como estas?"}'
  $chatRes = Invoke-RestMethod -Uri "$base/api/Chat" -Method POST -Headers $headers -Body $chatBody -TimeoutSec 15
  Write-Host "=== POST /api/Chat SUCCESS ==="
  Write-Host ($chatRes | ConvertTo-Json -Depth 3)
} catch {
  Write-Host "=== POST /api/Chat FAILED: $($_.Exception.Message)"
}

# 2. Test VIP Trial Subscribe
try {
  $vipRes = Invoke-RestMethod -Uri "$base/api/Wardrobe/subscribe-trial" -Method POST -Headers $headers -TimeoutSec 15
  Write-Host "=== POST /api/Wardrobe/subscribe-trial SUCCESS ==="
  Write-Host ($vipRes | ConvertTo-Json -Depth 3)
} catch {
  Write-Host "=== POST /api/Wardrobe/subscribe-trial FAILED: $($_.Exception.Message)"
}

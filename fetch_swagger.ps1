$urls = @(
  'https://k9br9dc7-5123.brs.devtunnels.ms/index.js',
  'https://k9br9dc7-5123.brs.devtunnels.ms/swagger/v1/swagger.json',
  'https://k9br9dc7-5123.brs.devtunnels.ms/v1/swagger.json',
  'https://k9br9dc7-5123.brs.devtunnels.ms/swagger.json',
  'https://k9br9dc7-5123.brs.devtunnels.ms/openapi.json'
)

$headers = @{
  'X-Tunnel-Skip-Anti-Abuse-Page' = 'true'
}

foreach ($u in $urls) {
  try {
    $res = Invoke-WebRequest -Uri $u -UseBasicParsing -TimeoutSec 10 -Headers $headers
    Write-Host "SUCCESS: $u, StatusCode: $($res.StatusCode), Size: $($res.Content.Length)"
    if ($u.EndsWith(".json")) {
      [System.IO.File]::WriteAllText('C:\Users\Matias\.gemini\antigravity\scratch\swagger_spec.json', $res.Content)
      Write-Host "Saved swagger_spec.json successfully!"
      break
    }
    if ($u.EndsWith("index.js")) {
      Write-Host "index.js preview:"
      Write-Host $res.Content
    }
  } catch {
    Write-Host "FAILED: $u -> $($_.Exception.Message)"
  }
}

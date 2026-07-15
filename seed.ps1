# AgriLink - POST sample data to every endpoint (one row per entity).
#
# Usage:
#   .\seed.ps1              # posts directly to each service on its own port (8081-8088)
#   .\seed.ps1 -Gateway     # posts everything through the API gateway instead
#   .\seed.ps1 -Gateway -GatewayPort 9091
#
# Prerequisite: MySQL is running, and the target service(s) are started.
# PKs are omitted on purpose - they are auto-generated (IDENTITY).

param(
    [switch]$Gateway,
    [int]$GatewayPort = 9091
)

function Post($port, $path, $body) {
    $hostPort = if ($Gateway) { $GatewayPort } else { $port }
    $url = "http://localhost:$hostPort$path"
    try {
        $json = $body | ConvertTo-Json -Compress
        $res = Invoke-RestMethod -Uri $url -Method Post -ContentType "application/json" -Body $json
        Write-Host ("OK    {0}  ->  {1}" -f $path, $res.message) -ForegroundColor Green
    }
    catch {
        Write-Host ("FAIL  {0}  ->  {1}" -f $path, $_.Exception.Message) -ForegroundColor Red
    }
}

Write-Host "Seeding AgriLink (mode: $(if ($Gateway) {'GATEWAY :' + $GatewayPort} else {'DIRECT ports'}))`n"

# ---- IAM service (8081) ----
Post 8081 "/agrilink/iam/roles"            @{ roleName = "Farmer"; description = "Farmer role"; status = "Active" }
Post 8081 "/agrilink/iam/permissions"      @{ permissionName = "ViewCrop"; module = "Crop"; action = "READ"; description = "View crop data" }
Post 8081 "/agrilink/iam/role-permissions" @{ roleId = 1; permissionId = 1 }
Post 8081 "/agrilink/iam/users"            @{ name = "Asha"; roleId = 1; email = "asha@example.com"; phone = "9876543210"; regionId = 1; passwordHash = "hash123"; status = "Active" }
Post 8081 "/agrilink/iam/audit-logs"       @{ userId = 1; action = "LOGIN"; module = "IAM"; timestamp = "2026-06-15T10:30:00"; ipAddress = "192.168.0.1" }

# ---- Farmer service (8082) ----
Post 8082 "/agrilink/farmer/farmer-profiles" @{ userId = 1; name = "Asha"; dateOfBirth = "1990-05-20"; gender = "Female"; nationalIdNumber = "ID12345"; village = "Hosur"; district = "Krishnagiri"; state = "TN"; phone = "9876543210"; bankAccountNumber = "ACC999"; status = "Active" }
Post 8082 "/agrilink/farmer/land-holdings"   @{ farmerId = 1; surveyNumber = "SVY-101"; areaAcres = 3.5; soilType = "Loam"; irrigationSource = "Borewell"; ownershipType = "Owned"; status = "Active" }

# ---- Crop service (8083) ----
Post 8083 "/agrilink/crop/crop-catalogs"      @{ cropName = "Paddy"; category = "Cereal"; season = "Kharif"; typicalDurationDays = 120; expectedYieldPerAcre = 25.5; status = "Active" }
Post 8083 "/agrilink/crop/crop-plans"         @{ farmerId = 1; holdingId = 1; cropId = 1; season = "Kharif"; year = 2026; sowingDate = "2026-06-15"; expectedHarvestDate = "2026-10-15"; areaPlanted = 3.0; status = "Planned" }
Post 8083 "/agrilink/crop/growth-observations" @{ planId = 1; officerId = 1; observationDate = "2026-07-01"; stage = "Germination"; pestOrDiseaseFlag = $false; remarks = "Healthy" }

# ---- Input service (8084) ----
Post 8084 "/agrilink/input/catalogs" @{ name = "Urea"; category = "Fertiliser"; unit = "Bag"; pricePerUnit = 300.0; subsidisedPrice = 200.0; availableStock = 500; status = "Available" }
Post 8084 "/agrilink/input/requests" @{ farmerId = 1; inputId = 1; quantityRequested = 10; requestDate = "2026-06-15"; assignedCentreId = 1; actualPrice = 2000.0; status = "Requested" }

# ---- Subsidy service (8085) ----
Post 8085 "/agrilink/subsidy/scheme-catalogs"      @{ schemeName = "PM-KISAN"; category = "WelfareSupport"; eligibilityCriteria = "Small farmer"; benefitAmount = 6000.0; fundingSource = "Central"; startDate = "2026-01-01"; endDate = "2026-12-31"; status = "Active" }
Post 8085 "/agrilink/subsidy/subsidy-applications" @{ farmerId = 1; schemeId = 1; applicationDate = "2026-06-15"; eligibilityScore = 85.0; reviewedBy = 1; disbursedAmount = 6000.0; disbursedDate = "2026-06-20"; status = "Approved" }

# ---- Produce service (8086) ----
Post 8086 "/agrilink/produce/produce-listings" @{ farmerId = 1; cropId = 1; harvestDate = "2026-06-01"; quantityKg = 1000.0; qualityGrade = "A"; askingPricePerKg = 22.5; status = "Available" }
Post 8086 "/agrilink/produce/produce-sales"    @{ listingId = 1; buyerId = 2; quantitySoldKg = 500.0; agreedPricePerKg = 22.0; totalAmount = 11000.0; saleDate = "2026-06-10"; paymentStatus = "Pending" }

# ---- Report service (8087) ----
Post 8087 "/agrilink/report/agri-reports" @{ generatedBy = 1; scope = "District"; metrics = "RegisteredFarmers=120"; generatedDate = "2026-06-15" }

# ---- Notification service (8088) ----
Post 8088 "/agrilink/notification/notifications" @{ userId = 1; message = "Sowing reminder"; category = "CropAdvisory"; status = "Unread"; createdDate = "2026-06-15" }

Write-Host "`nDone. Use GET (e.g. http://localhost:8081/agrilink/iam/users) or reverse-engineer the schemas in MySQL Workbench."

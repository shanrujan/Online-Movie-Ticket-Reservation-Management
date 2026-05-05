
$baseUrl = "http://localhost:8085/api"

function Seed-Movies {
    try {
        # 1. Register Admin
        $regData = @{
            name = "Admin User"
            email = "admin@cinemax.com"
            password = "password123"
            role = "ADMIN"
        }
        Write-Host "Registering admin..."
        try {
            Invoke-RestMethod -Uri "$baseUrl/users/register" -Method Post -ContentType "application/json" -Body (ConvertTo-Json $regData)
        } catch {
            Write-Host "Admin might already exist, continuing..."
        }

        # 2. Login
        Write-Host "Logging in..."
        $loginData = @{
            email = "admin@cinemax.com"
            password = "password123"
        }
        $resp = Invoke-RestMethod -Uri "$baseUrl/users/login" -Method Post -ContentType "application/json" -Body (ConvertTo-Json $loginData)
        $token = $resp.token
        $headers = @{
            "Authorization" = "Bearer $token"
            "Content-Type" = "application/json"
        }

        # 3. Add Movies
        $movies = @(
            @{
                title = "Interstellar"
                genre = "Sci-Fi, Adventure"
                language = "English"
                duration = 169
                description = "Explorer wormhole space survival."
                posterUrl = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFjOGEtYGY0ZDRkOTg4NGYyXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
                status = "NOW_SHOWING"
                releaseDate = "2014-11-07"
            },
            @{
                title = "Inception"
                genre = "Sci-Fi, Action"
                language = "English"
                duration = 148
                description = "Dream heist."
                posterUrl = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg"
                status = "NOW_SHOWING"
                releaseDate = "2010-07-16"
            },
            @{
                title = "The Dark Knight"
                genre = "Action, Crime"
                language = "English"
                duration = 152
                description = "Batman and Joker."
                posterUrl = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg"
                status = "NOW_SHOWING"
                releaseDate = "2008-07-18"
            }
        )

        foreach ($m in $movies) {
            Write-Host "Adding $($m.title)..."
            $r = Invoke-RestMethod -Uri "$baseUrl/movies" -Method Post -Headers $headers -Body (ConvertTo-Json $m)
            Write-Host "Added!"
        }
    } catch {
        Write-Error "Failed to seed: $($_.Exception.Message)"
    }
}

Seed-Movies

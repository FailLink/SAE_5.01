Param(
    $cheminProjet,
    $cheminDocker,
    $cheminPostgresBin,
    [Boolean] $wantDump,
    $username,
    $database,
    $hostSQL,
    $password
)


# Naviguer vers le répertoire du projet
Set-Location -Path $cheminProjet

# Exécuter la compilation Maven
Write-Output "Compilation de l'application Spring Boot..."
./mvnw clean package -DskipTests

# Vérifier si le JAR a été généré
$cheminFichierJar = Join-Path -Path $cheminProjet -ChildPath "target\SAE501Serveur-0.0.1-SNAPSHOT.jar"
if (Test-Path $cheminFichierJar) {
    # Copier le JAR dans le répertoire du Dockerfile
    Copy-Item -Path $cheminFichierJar -Destination $cheminDocker -Force -Verbose
} else {
    Write-Output "Erreur : le fichier JAR n'a pas été trouvé."
    exit 1
}

if($wantDump){
    Write-Output "Création du dump de la bdd"
    Set-Location -Path $cheminPostgresBin
    $env:PGPASSWORD=$password
    .\pg_dump -U $username -h $hostSQL -d $database -f "C:\Users\Utilisateur\OneDrive\Documents\IUT 3\SAE 5.A.01\imageBDD.sql"
    $env:PGPASSWORD=$null
}


# Lancer la construction de l'image Docker
Set-Location -Path $cheminDocker
docker build -t sae_501_serveur:latest -f dockerFileSpringSecurity .
docker build -t sae_501_bdd:latest -f dockerFileBDD .
docker-compose up
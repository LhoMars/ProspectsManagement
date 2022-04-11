# Serveur web à distance

Le serveur web permet d'effectuer des requête d'api (GET, POST...) depuis application mobile.
Il permet de récupérer les prospects et d'insérer les nouveaux en base de données mySql.
Trois pages permettent les requêtes api : `connexionBase.php`, `getAllProspects.php` et `insetProspects.php`.

### Connexion au serveur
Cette page permet de vérifier l'accès à la base de données depuis le serveur web.

```php
<?php
/**
 * connexion à la base de données prospectsmanagement
 * @return PDO|null
 */
function connexionBase()
{
    try {
        $connexion = new PDO($hote, $utilisateur, $mot_passe);
        $connexion->exec("set names utf8");
        return $connexion;
    } catch (PDOException $e) {
        echo 'Erreur : ' . $e->getMessage() . '<br />';
        echo 'N° : ' . $e->getCode();
        return null;
    }
}
```

### GET : Récupérer les Prospects
Le type de contenu est défini pour le format `json` avec comme encodage `UTF-8`.
Le résultat des données en bdd est encodée et écris dans la page web.

```php
<?php
header('Content-type: application/json; charset=utf-8');
require_once('fonctionsDAL.inc');
$status = 200;
$data = null;

$data = getAllProspects();
header("HTTP/1.1 " . $status);

$response['status'] = $status;
if ($data) {
    $response['data'] = $data;
}
$json_response = json_encode($response);
echo $json_response;
?>
```

Le format des données reçu est sous ce format :
```json
{
  "status": 200,
  "data": [
    {
      "id": "28",
      "nom": "Armand",
      "prenom": "Enzo",
      "tel": "6877687",
      "mail": "htrhtr",
      "note": "5",
      "siret": "53008580200037",
      "raisonsocial": "facebook"
    },
    {
      "id": "29",
      "nom": "Baptiste",
      "prenom": "Enzo",
      "tel": "357786678",
      "mail": "rgrr",
      "note": "6",
      "siret": "48170829500037",
      "raisonsocial": "apple"
    },
    {
      "id": "30",
      "nom": "Baptiste",
      "prenom": "michael",
      "tel": "55416",
      "mail": "egegr",
      "note": "5",
      "siret": "51353675500012",
      "raisonsocial": "amazon"
    }
  ]
}
```

### POST : envoyer les prospects
Toujours au format json, les données peuvent être récupérées pour alimenter la bdd mySql;  
La sélection se fait par rapport à la précédente date de synchronisation
```php
<?php
header('Content-type: application/json; charset=utf-8');

$date = (isset($_GET['date']))? $_GET['date']: null;
$dataPost = file_get_contents('php://input');
setProspects($dataPost, $date);
?>
```

La fonction `setProspects` parcourt les données et vérifie s'il le prospect est déjà présent dans la bdd.

```php
<?php
/**
 * Permet d'insérer des prospects en bdd
 * @param $json : un json contenant les prospects
 * @return void
 */
function setProspects($json, $date)
{
    $res = json_decode($json);

    // insertion ou mise à jour des données dans la table prospect
    //$res = $obj->prospects;
    foreach ($res as $prospect) {
        $nb = nbProspect($prospect->{'nom'}, $prospect->{'prenom'}, $prospect->{'raisonsocial'});
        if ($nb == 0) {
            insertprospect($prospect->{'id'}, $prospect->{'nom'}, $prospect->{'prenom'}, $prospect->{'tel'}, $prospect->{'mail'}, $prospect->{'note'}, $prospect->{'siret'}, $prospect->{'raisonsocial'}, $date);
        }
    }
}
?>
```

Le format des données json doit respecter le format exemple si dessous : 
```json
{
  "id": "default",
  "nom": "Armand",
  "prenom": "Enzo",
  "mail": "htrhtr",
  "tel": "6877687",
  "note": 5,
  "siret": 53008580200037,
  "raisonsocial": "facebook"
}, {
  "id": "default",
  "nom": "Baptiste",
  "prenom": "Enzo",
  "mail": "rgrr",
  "tel": "357786678",
  "note": 6,
  "siret": 48170829500037,
  "raisonsocial": "apple"
}, {
  "id": "default",
  "nom": "Baptiste",
  "prenom": "michael",
  "mail": "egegr",
  "tel": "55416",
  "note": 5,
  "siret": 51353675500012,
  "raisonsocial": "amazon"
}
```
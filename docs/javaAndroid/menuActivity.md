# Menu Activity

Le menu permet d'avoir l'aperçu des prospects, de synchroniser les données avec le serveur.

### La synchronisation avec le serveur externe
Ce diagramme de séquence représente la récupération des prospects depuis l'api au format json et de l'ajout en base de données local.
Et dans un second temps la récupération des prospects en base de données SQLite puis du formatage en json pour l'envoi au serveur.

``` mermaid
sequenceDiagram
actor Commercial

  Commercial ->>+ Activity Menu: bouton synchroniser  
  Activity Menu ->>+ APIBdd: callWebService(getAllProspects)  
  APIBdd -->>- Activity Menu: JSONArray json
  loop updateBddProspects(json)
      Activity Menu -)+ Prospect: create with json data
      Prospect -->>- Activity Menu: object
      Activity Menu ->>+ DAO: add Prospect
      DAO -->>- Activity Menu: resultat insertion
  end
  Activity Menu ->>+ DAO: getProspect
  DAO -->>- Activity Menu: ArrayList<Prospect>
  Activity Menu ->>+ APIBdd: createJsonProspects(lesProspects)
  APIBdd -->>- Activity Menu: JSONArray jsonProspects
  Activity Menu ->> APIBdd: postJsonProspect("InsertProspect", jsonProspects)  
  Activity Menu -->>- Commercial: boiteMessage(resultApi)
```

L'évènement du bouton est déclaré dans `eventBtnSynchroniser`.  
La date de mise à jour permet de récupérer les prospects créer à une date ultérieure à celle de la mise à jour et donc limiter le nombres d'informations transmis en bande passante.  
Pour le POST : application envoie seulement les prospects pour lesquelles la valeur `isupdate` de la colonne est à false, il s'agit d'une valeur boolean pour savoir s'il le prospect est déjà synchronisé avec le serveur externe.  

```java
public View.OnClickListener eventBtnSynchroniser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        loading.startLoadingDialog();

        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        String dateMiseAjour = formatter.format(date);

        ApiBdd api = new ApiBdd();
        api.callWebService("getAllProspects.php?date="+lEmployee.getDateMiseAjour().replace(":","!"));
        JSONArray json = api.getJsonData();
        updateBddProspects(json);

        ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspects(null, null, null, false);//getAllProspects();

        if(lesProspects != null){
        JSONArray jsonProspects = api.createJsonProspects(lesProspects);
        api.postJsonProspect("insertProspect.php?date="+lEmployee.getDateMiseAjour().replace(":","!"), jsonProspects.toString());
        }
        lEmployee.setDateMiseAjour(dateMiseAjour);
        dataBase.getEmployeeBdd().update(lEmployee);

        loading.dismissDialog();
        boiteMessage(api.getResult());
        setRecyclerView(dataBase.getProspectBdd().getProspects(null,null,null, true));
        }
    };
```

Pour obtenir le bon format des données en json et mettre à jour le fichier SQLite la méthode `updateBddProspects` décompose l'objet et insert en bdd les prospects.  
Pour créer le format de données à envoyer on utilise la méthode `createJsonProspects(ArrayList<Prospect> lesProspects)`.

```java
/**
     * Formate une liste de prospect en json
     *
     * @param lesProspects ArrayList : la liste de prospects
     * @return JsonArray : les prospects au format json
     */
    public JSONArray createJsonProspects(ArrayList<Prospect> lesProspects) {
        //Creating a JSONObject object
        JSONArray jsonArray = new JSONArray();

        try {
            for (Prospect prospect : lesProspects) {
                JSONObject jsonObject = new JSONObject();
                //Inserting key-value pairs into the json object
                jsonObject.put("id", "default");
                jsonObject.put("nom", prospect.getNom());
                jsonObject.put("prenom", prospect.getPrenom());
                jsonObject.put("mail", prospect.getMail());
                jsonObject.put("tel", prospect.getTel());
                jsonObject.put("note", prospect.getNotes());
                jsonObject.put("siret", prospect.getSiret());
                jsonObject.put("raisonsocial", prospect.getRaisonSocial());

                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
```

Le bouton `Ajouter` change l'activité vers `AjoutProspectActivity`
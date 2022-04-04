# Menu Activity

Le menu permet d'avoir l'aperçu des prospects, de synchroniser les données avec le serveur.

### La synchronisation avec le serveur externe
Ce diagramme de séquence représente la récupération des prospects depuis l'api au format json et de l'ajout en base de données local.
Et dans un second temps la récupération des prospects en base de données SQLite puis du formatage en json pour l'envoi au serveur.

``` mermaid
sequenceDiagram
actor Commercial

  Commercial ->>+ Activity Menu: bouton synchroniser  
  Activity Menu ->> APIBdd: callWebService(getAllProspects)  
  APIBdd -->> Activity Menu: JSONArray json
  loop updateBddProspects(json)
      Activity Menu -) Prospect: create with json data
      Prospect -->> Activity Menu: object
      Activity Menu ->> DAO: add Prospect
      DAO -->> Activity Menu: resultat insertion
  end
  Activity Menu ->> DAO: getProspect
  DAO -->> Activity Menu: ArrayList<Prospect>
  Activity Menu ->> APIBdd: createJsonProspects(lesProspects)
  APIBdd -->> Activity Menu: JSONArray jsonProspects
  Activity Menu ->> APIBdd: postJsonProspect("InsertProspect", jsonProspects)  
  Activity Menu -->> Commercial: boiteMessage(resultApi)
```

L'évènement du bouton est déclaré dans `eventBtnSynchroniser`
```java
public View.OnClickListener eventBtnSynchroniser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loading.startLoadingDialog();

            ApiBdd api = new ApiBdd();
            api.callWebService("getAllProspects");
            JSONArray json = api.getJsonData();
            updateBddProspects(json);

            ArrayList<Prospect> lesProspects = dataBase.getProspectBdd().getProspect(null, null, null);//getAllProspects();
            JSONArray jsonProspects = api.createJsonProspects(lesProspects);
            api.postJsonProspect("InsertProspect", jsonProspects.toString());

            loading.dismissDialog();
            boiteMessage(api.getResultApi());
            setRecyclerView(dataBase.getProspectBdd().getProspect(null,null,null));
        }
    };
```

Le bouton `Ajouter` change l'activité vers `AjoutProspectActivity`
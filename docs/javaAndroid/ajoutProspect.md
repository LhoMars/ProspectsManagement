# Ajouter un Prospect
``
## Introduction
La classe `AjoutProspectActivity` hérite de `AppCompatActivity`. Sont but est de permettre la création d'un nouveau prospect.
Elle utilise la page layout `activity_addprospect` en tant que FrontEnd.

![ajoutProspect](../assets/ajoutProspect.png)

## Méthodes

### Événements Boutons
Pour ce qui est des événenements en rapport avec les boutons nous en avons 3.
Le Premier intitulé `eventBtnRechercherEntreprise` est lié au bouton Rechercher.
```java
	public View.OnClickListener eventBtnRechercherEntreprise = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ApiGouv apiGouv = new ApiGouv();

			long siren = apiGouv.getSirenWithName(raisonSociale.getText().toString());
			String sirenString = Long.toString(siren);
			siretText.setText(sirenString, TextView.BufferType.EDITABLE);

			boiteMessage(apiGouv.getResult());
		}
	};
```
Il permet quand on clique dessus de rechercher grâce à l'API du gouvernement sur les entreprises, le numéro Siren de la raison sociale qu'on a écrit dans
le EditText au dessus du bouton.

![rechercheSiren](../assets/rechercheSiren.png)

Pour ce qui est du deuxième, il est intitulé `eventBtnEnregistrer` et est lié au bouton Enregistrer.
La méthode fais 115 lignes. (en contant les sauts de lignes et les commentaires)

```java
    public View.OnClickListener eventBtnEnregistrer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /* Récupération de ce qu'a écrit l'utilisateur dans les EditText
             puis on les transforment en chaine de caractère qu'on stockent dans de nouvelles
             variables */

            EditText raisonSocialeProspectTxt = (EditText) findViewById(R.id.RaisonSociale);
            String raisonSocialeProspect = raisonSocialeProspectTxt.getText().toString();

            EditText sirenProspectTxt = (EditText) findViewById(R.id.Siren);
            String sirenProspect = sirenProspectTxt.getText().toString();

            EditText nomProspectTxt = (EditText) findViewById(R.id.Nom);
            String nomProspect = nomProspectTxt.getText().toString();

            EditText prenomProspectTxt = (EditText) findViewById(R.id.Prenom);
            String prenomProspect = prenomProspectTxt.getText().toString();

            EditText mailProspectTxt = (EditText) findViewById(R.id.Email);
            String mailProspect = mailProspectTxt.getText().toString();

            EditText telProspectTxt = (EditText) findViewById(R.id.Telephone);
            String telProspect = telProspectTxt.getText().toString();

            EditText noteProspectTxt = (EditText) findViewById(R.id.Notes);
            String noteProspect = noteProspectTxt.getText().toString();

            /* Initialisation des Regex pour faire des tests par rapport aux entrées de l'utilisateur */

            String regexRaisonSociale = "[A-Za-z0-9\\é\\è\\ê\\ï\\@\\/\\*\\-]+$";
            String regexSiren = "[0-9]{9}|0";
            String regexNomPrenom = "[A-Za-z\\é\\è\\ê\\ï\\-]+$";
            String regexEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String regexPhoneNumber = "(33|0033|0)[1-9][0-9]{8}$";
            String regexNote = "[0-9]|1[0-9]|20";
            String regexSpace = "(\\s+)$";

            long sirenProspectTest = 0;
            String telProspectTest = "";
            int noteProspectTest = 0;
            String errorMessage = "";

            /* Création des tests sur les entrées de l'utilisateur */

            if (nomProspectTxt.length() != 0 && prenomProspectTxt.length() != 0 && sirenProspectTxt.length() != 0
                    && raisonSocialeProspectTxt.length() != 0 && mailProspectTxt.length() != 0) {
                Prospect newProspect = new Prospect();

                if (raisonSocialeProspect.matches(regexRaisonSociale) && !raisonSocialeProspect.matches(regexSpace)) {
                    newProspect.setRaisonSocial(raisonSocialeProspect);
                }else{
                    errorMessage += "- Raison Sociale \n";
                }

                if (sirenProspect.matches(regexSiren) && !sirenProspect.matches(regexSpace)) {
                    sirenProspectTest = Long.parseLong(sirenProspect);
                    newProspect.setSiret(sirenProspectTest);
                }else{
                    errorMessage += "- Siren \n";
                }

                if (prenomProspect.matches(regexNomPrenom) && !prenomProspect.matches(regexSpace)) {
                    newProspect.setPrenom(prenomProspect);
                }else{
                    errorMessage += "- Prénom \n";
                }

                if (nomProspect.matches(regexNomPrenom) && !nomProspect.matches(regexSpace)) {
                    newProspect.setNom(nomProspect);
                }else{
                    errorMessage += "- Nom \n";
                }

                if (mailProspect.matches(regexEmail) && !mailProspect.matches(regexSpace)) {
                    newProspect.setMail(mailProspect);
                }else{
                    errorMessage += "- Mail \n";
                }

                if (telProspect.length() == 0 || telProspect.matches(regexPhoneNumber)) {
                    telProspectTest = (telProspect.length() == 0) ? "0" : telProspect;
                    newProspect.setTel(telProspectTest);
                }else{
                    errorMessage += "- Téléphone \n";
                }

                if (noteProspect.length() == 0 || (noteProspect.matches(regexNote))) {
                    noteProspectTest = (noteProspect.length() == 0) ? 0 : Integer.parseInt(noteProspect);
                    newProspect.setNotes(noteProspectTest);
                }else{
                    errorMessage += "- Note \n";
                }

                newProspect.setIsUpdate(false);

                if(errorMessage.length()==0){
                    dataBase.getProspectBdd().add(newProspect);

                    Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
                    retourMenu.putExtra("employee",lEmployee);
                    startActivity(retourMenu);
                }else{
                    boiteMessage("Sont invalides :\n"+errorMessage);
                }
            } else {
                boiteMessage("L'un des champs suivants est vide.\n" +
                        "\n" +
                        "Sont obligatoires : \n" +
                        "La Raison Sociale de l'entreprise\n" +
                        "Le Siret\n" +
                        "Le Nom\n" +
                        "Le Prénom\n" +
                        "Le Mail");
            }
        }

    };
```
Il y a 3 parties coupées par des commentaires.
La première qui permet d'obtenir sous la forme d'une chaine de caractère ce que l'utilisateur a écrit dans chacun des EditText.
La seconde qui met en place les Regex pour les prochains tests.
Et finalement les tests sur ce que l'usager à mis comparés aux Regex.


Pour la troisième méthode, nous sommes sur le bouton annuler.
```java
    public View.OnClickListener eventBtnAnnuler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent retourMenu = new Intent(AjoutProspectActivity.this, MenuActivity.class);
            retourMenu.putExtra("employee", lEmployee);
            startActivity(retourMenu);
        }
    };
```
Elle nous permet de revenir sur la page précédente et donc d'annuler toute tentative d'ajout de prospect.

### Aide aux entrées
La méthode d'aide aux entrées va permettre d'informer l'utilisateur sur ce qu'il doit écrire dans chacune des zones de texte.
Pour cela, l'utilisateur doit double cliquer sur un des EditText.

```java
    public void infosEntrees(View v){
        LinearLayout preInfosAjoutProspect = findViewById(R.id.preInfosAjoutProspect);
        preInfosAjoutProspect.setVisibility(View.GONE);
        LinearLayout InfosAjoutProspect = findViewById(R.id.InfosAjoutProspect);
        InfosAjoutProspect.setVisibility(View.VISIBLE);

        noteInfos = (TextView) findViewById(R.id.noteInformations);

        switch(v.getId()){
            case R.id.RaisonSociale :
                noteInfos.setText("La Raison sociale est le nom d'une société.\nSa taille maximale est de 50.\nSont autorisés :\n- Les chiffres et les lettres.\n- Les signes '/', '@', '*'.");
                break;
            case R.id.Siren :
                noteInfos.setText("Le Siren est le numéro d'identification d'une entreprise.\nSa taille maximale est de 9.\nSont autorisés :\n- Les chiffres.");
                break;
            case R.id.Nom :
                noteInfos.setText("Le Nom a une taille maximale de 35.\nSont autorisés :\n- Les lettres.\n- Le signe '-'.");
                break;
            case R.id.Prenom :
                noteInfos.setText("Le Prénom a une taille maximale de 25.\nSont autorisés :\n- Les lettres.\n- Le signe '-'.");
                break;
            case R.id.Telephone :
                noteInfos.setText("Le Numéro de téléphone a une taille maximale de 14.\nIl peut commencer par '0033','33','0' et est suivi de 8 chiffres\nSont autorisés :\n- Les chiffres.");
                break;
            case R.id.Email :
                noteInfos.setText("L'Adresse e-mail a une taille maximale de 100.\nElle doit suivre l'exemple suivant : ABC@example.com\nSont autorisés :\n- Les chiffres et les lettres.\n- Les signes '.','-','_'");
                break;
            case R.id.Notes :
                noteInfos.setText("La Note a une taille maximale de 2.\nElle doit être comprise entre 0 et 20.\nSont autorisés :\n- Les chiffres");
                break;
        }
    };
```

Le rectangle indiquant qu'il doit double cliquer disparait pour laisser apparaitre le vrai rectangle d'une taille plus grande contenant les bonnes
informations.

Exemple : L'utilisateur appuie sur la raison sociale.
![testRaisonSociale](../assets/testRaisonSociale.png)
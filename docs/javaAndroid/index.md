# Connexion du commercial

### Diagramme de séquence

Ce diagramme présente la procédure d'enregistrement d'un prospect dans la base de données SQLite

``` mermaid
sequenceDiagram
actor Commercial

  Commercial ->>+ Activity Login: saisit de l'identifiant / mot de passe  
  Activity Login ->>+ DAO: getEmployeeWithIdentifiant(lIdentifiant)  
  DAO -)+ Employee: create new Employee(identifiant, motDePasse)  
  Employee -->>- DAO: lEmployee
  DAO -->>- Activity Login: lEmploye
  Activity Login ->>+ Employee: checkPassword(motDePasse)
  Employee -->>- Activity Login: true
  Activity Login ->> Activity Login : new Intent
  Activity Login -->>- Commercial: startActivity(MenuActivity)
```

### Détaille des méthodes

Le layout de connexion inclut les champs de saisies de l'identifiant, mot de passe et le bouton

```xml
<EditText
        android:id="@+id/Identifiant"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="68dp"
        android:layout_marginBottom="26dp"
        android:hint="Identifiant"
        android:singleLine="true"
        android:textSize="22sp"/>

<EditText
android:id="@+id/MotDePasse"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginBottom="46dp"
android:hint="Mot de passe"
android:inputType="textPassword"
android:textSize="22sp"/>

<Button
android:id="@+id/btnLogin"
android:layout_width="128dp"
android:layout_height="wrap_content"
android:layout_marginBottom="26dp"
android:background="@drawable/button_style1"
android:text="Connexion"
android:textColor="#1495d7"
android:textStyle="bold"/>
```

L'activité récupère les informations saisies et vérifie l'identité de la personne avec sa présence en base de données et son mot de passe chiffré.
Si aucune erreur n'est detectée on passe à l'activité menu.

```java
if (editIdentifiant.getText().length() > 0 && editPassword.getText().length() > 0) {
        Employee lEmploye = database.getEmployeeBdd().getEmployeeWithIdentifiant(editIdentifiant.getText().toString());

        if (lEmploye != null && lEmploye.checkPassword(editPassword.getText().toString())) {
        Intent menu=new Intent(LoginActivity.this,MenuActivity.class);
        menu.putExtra("employee",lEmploye);
        startActivity(menu);
        }
}
```
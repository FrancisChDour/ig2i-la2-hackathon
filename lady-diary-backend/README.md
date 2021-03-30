# Lady Diary Backend

Ce microservice correspond au backend de l'application Lady Diary qui a été développée dans le cadre du hackathon de POO.

## Docker

Ce micro service est Dockerisé. Les images des différentes versions sont disponibles ici :
https://hub.docker.com/r/francischdour/ig2i-lady-diary-backend

## Version
v1.1.0
+ Gestion des doublons d'utilisateur
+ Gestion des mots de passe
+ Nouvelles API d'authentification
+ Nouvelle API GET records from Topic

v1.0.1
+ gestion des erreur CORS (Angular)

v1.0.0
+ Version initiale

## Installation

L'open-jdk 15 et Apache Maven 3.6.3 sont nécessaires pour garantir le bon fonctionnement du microservice.

Build :
`mvn clean install`

Lancement :
`java -Dspring.profiles.active=h2 -jar target/lady-diary-{version}.jar`

Test : `curl -X GET localhost:8080/ping`

Je conseille de lancer l'application avec sa base H2 en mémoire via le profile h2 pour le moment.
Néanmoins, le microservice peut se lancer avec un SGBD mariadb hébergé sur *localhost:5000/hackathon*

## Architecture

### Frameworks

Le microservice utilise les dépendances majeures suivantes :

+ Spring : pour l'injection de dépendances et la couche d'abstraction
+ SpringBoot : pour le lancement de l'application et du serveur tomcat intégré
+ SpringBoot Web : pour la mise en place des API REST
+ Lombok : pour la génération de code via des annoations (getters, setters, contructeurs ...)
+ Springfox : pour la génération et le lancement automatique du swagger

### Arborescence

```
.
├── LadyDiaryApplication.java # Main Spring Boot
├── configuration # Classes de configuration Spring
├── controllers # Controllers API Rest
├── domain # Entités et objets du domaine
├── repositories # Repositories d'accès aux données
└── services # Implémentation des services métiers
```

### Exemple de création d'une API

Soit le besoin suivant :

> Nous avons besoins de récupérer la lite des *Records* d'un *Topic* donné.

On va tout d'abord créer une nouvelle API dans le controller existant `RecordController` :
```java
@RestController // Pour définir cette classe comme étant un controller
@RequestMapping("/v1/records") // La première partie de l'URL de chaque API définie dans ce controller
@RequiredArgsConstructor // Annotation Lombok pour créer un constructeur avec tout les champs static final
public class RecordController {

    private final RecordService recordService; // Ici on va utiliser le service des Record pour nos opérations

    @ApiOperation(value = "Retrieve all records from a topic") // Une légende pour l'API
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No records found"), // Les codes d'erreur possibles
            @ApiResponse(code = 200, message = "Records")})
    @GetMapping("/fetchFromTopic/{idTopic}") // La deuxième partie de l'URL propre à cette API
    // Le @PathVariable correspond à la variable {idTopic} de l'URL de l'API
    public ResponseEntity<List<Record>> getRecordsFromIdTopic(@PathVariable int idTopic) throws TopicNotFoundException {
        // On va maintenant appeler le service getRecordsFromTopic du service RecordService
        List<Record> records = recordService.getRecordsFromTopic(idTopic);

        if (records.isEmpty()){
            // Si la liste est vide on renvoie un 204 (Pas de contenu)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            // Sinon on renvoie la liste des records avec un 200
            return ResponseEntity.status(HttpStatus.OK).body(records);
        }
    }
}
```
Puis créer le nouveau service `RecordService:getRecordsFromTopic`:

```java
@Service // On définit la classe comme étant un Bean de service injectable
@RequiredArgsConstructor // Idem que dans le constructeur
public class RecordService {

    // Le service utilisera un TopicRepository pour accéder aux données des Topics
    private final TopicRepository topicRepository;
    
    // On créé notre méthode qui renvoie une liste de records en prenant un idTopic en paramètre
    public List<Record> getRecordsFromTopic(int idTopic) throws TopicNotFoundException {
        // On vérifie d'abord que le topic existe, sinon on renvoie une exception
        Topic topic = topicRepository.findById(idTopic)
                .orElseThrow(() -> new TopicNotFoundException(String.format("%d", idTopic)));
        // Puis on retourne simplement la liste des Records de ce topic
        return topic.getRecords();
    }
}
```

Notre service est créé, il ne reste plus qu'à implémenter notre `TopicRepository:findById` :

```java
// On définit cette interface comme étant un Bean injectable de Repository
@Repository
// L'interface étant JpaRepository, renvoie un Topic dont la clé pirmaire est un Integer
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    // rien d'autre à faire ! La méthode findById est déjà définie par JpaRepository ;)
    // Mais pour des besoins plus spécifiques, comme retourner la liste des topcis d'un utilisateur
    // Créés après une date données, on aura simplement besoin de définir le prototype de cette méthode :
    List<Topic> findTopicsByUserAndCreationDateAfter(User user, LocalDateTime creationDate);
    // Et Spring Boot Data s'occupe du reste ;) Rien d'autre à faire, pas de requête à écrire
}
```

Par défaut, lorsqu'une exception est remonté vers les controllers, ceux-ci renvoient une erreur 500. Il faut bannir 
ce comportement qui est signe de mauvaise santé et implémentation des API. 

>**Erreur 500 = Erreur grave interne où l'utilisateur n'y peut rien**

Or dans le cas où on recherche un TOpic mais que celui-ci n'existe pas, ce n'est pas la faute du serveur mais
bien de l'utilisateur qui essaye d'accéder à une ressource inconnue !
Dans ce cas il faut renvoyer un code d'erreur 404.

Dans notre service, on renvoie un `TopicNotFoundException` qui étend `NotFoundException` qui elle même étend `Exception`.
On va donc simplement créer une nouvelle méthode pour gérer les `NotFoundException` de manière génériques pour nos API dans
la classe `ExceptionHandlerController` :

```java
@ControllerAdvice // C'est une surcouche des controllers de l'application
@Log4j2 // On instancie le logger
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    // On définit un Handler pour les exceptions de type NotFoundException
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        // On va "adoucir" les logs, pas besoin de poluer la sortie standart avec des erreurs utiliateurs
        log.info("NotFoundException thrown : " + e.getErrorMessage());
        // Et on va retourner un code d'erreur 404 avec un ErrorMessage
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getErrorMessage());
    }
}
```
Un Error message qui est une association de clé / valeur générique pour nos erreurs :

```java
@Data
@Builder
public class ErrorMessage {
    private String error; // Ex : topic.notFound
    private String message; // Ex : This topic can not be found
}
```

Personnelement j'aime commencer par implémenter les Controllers pour valider le contrat d'interface avant de s'attaquer aux
règles métiers des services puis à l'accès aux données des Repositories. Mais on peut très bien faire l'inverse et commencer
par les repositories > services > Controllers.
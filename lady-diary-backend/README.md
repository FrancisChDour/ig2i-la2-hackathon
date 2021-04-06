# Lady Diary Backend

Ce microservice correspond au backend de l'application Lady Diary qui a été développée dans le cadre du hackathon de POO.

## Docker

Ce micro service est Dockerisé. Les images des différentes versions sont disponibles ici :
https://hub.docker.com/r/francischdour/ig2i-lady-diary-backend

## Version
V1.3.0
+ Refacto et documentation des controllers
+ Refacto des JSON entrants
+ Github CI
+ Ajout de données au lancement de l'application sous H2

v1.2.O
+ Controles sur les JSON entrants et sortants
+ Méthode PUT retravaillées
+ Refacto services
+ Ajout de données de test au lancement de l'application sous H2

v1.1.1
+ Athentication : Passage des creditentials dans le body

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

## Swagger

Le swagger constitue une grande partie de la documentation du projet. Il est accessible ici après lancement :
http://localhost:8080/swagger-ui/

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

### Validations et personalisation des JSON

Dans un contrat d'interface parfait, on n'aimerait pas que quelqu'un tente de nous envoyer un id utilisateur lors de la création de celui-ci,
ni même ce token. De même c'est dommage de renvoyer le mot de passe en retour d'un GET User. De plus on s'attend à ce que le
mot de passe fournie fasse par exemple minimun 8 caractères.

Il y a une façon TRES simple de mettre ça en place sans avoir à écrire les règles nous même dans les services. 
Pour se faire il faut déjà ajouter la dépendances suivante dans le pom :

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```

Ensuite on va ajouter un nouvel handler d'exception dans notre `ExceptionHandlerController` afin de personnaliser
le message de retour et ne pas avoir un simple 400 :

```java
@ControllerAdvice
@Log4j2
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .error("badRequest")
                .message("Your request has a wrong format")
                .details(errorList)
                .build());
    }
// ...
}
```

Puis pour chaque objet du domaine qu'on s'attend à recevoir ou qu'on compte emmettre, on va ajouter des contraintes.
Soit des contraintes sur les champs avec les annotations de la bibliothèque `jakarta.validation-api` qui fait partie de
la dépendance que nous venons d'ajouter, soit via les annotations de Jackson, la bibliothèque native de Spring boot
qui mappe les Json en objet et les objets en Json.
Par exemple pour `User` :

```java
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    // Ici la propriété sera présente en sortie d'un JSON mais pas en entrée
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Column(name = "name", unique = true)
    @NotBlank // On définit le nom comme non-null et non-vide
    @Size(min = 3,max = 32) // Avec un taille comprise entre min et max
    private String name;

    @Column(name = "password")
    // A l'inverse de l'id, le password n'est reçu qu'en entrée et n'est jamais retransmis après
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank // Idem que le nom
    @Size(min = 3,max = 32) // Idem que le nom mais ici entre 8 et 32
    private String password;

    @Column(name = "token")
    // Le token n'est pjamais présent dans les JSON
    @JsonIgnore
    private String token;

    @Column(name = "token_expiration_date")
    // De même que le token
    @JsonIgnore 
    private LocalDateTime tokenExpirationDate;
}
```
Et pour terminer, on définit le body reçu dans l'appel POST : /v1/users comme devant être valide avec l'annotation `@Valid` :
```java
    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) throws WrongFormatException {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
```
Maintenant si on tente de créer un utilisateur avec les informations suivantes :
```json
{
  "name": "",
  "password": "alo"
}
```
Alors on reçoit une erreur 400 avec les informations suivantes :
```json
{
  "error": "badRequest",
  "message": "Your request has a wrong format",
  "details": [
    "name ne doit pas être vide",
    "name la taille doit être comprise entre 3 et 32"
  ]
}
```
Plutôt puissant <3
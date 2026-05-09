# TP Réseaux - Java

Ce projet contient l'implémentation complète des TP de réseaux en Java, couvrant les concepts de sockets TCP/UDP, threads, et communication réseau.

## Structure du Projet

```
d:\exo Reseaux\
├── Main.java                 # Interface principale pour découvrir les TP
├── Tp2\                      # TP2 - Communication TCP entre processus
│   ├── Tp2A1.java           # Lecture de chaînes avec Scanner
│   ├── Tp2B1.java           # Copie de fichier avec PrintStream
│   ├── Tp2ProcessA.java     # Processus A (même machine)
│   ├── Tp2ProcessB.java     # Processus B (même machine)
│   ├── Tp2ProcessA_Remote.java    # Processus A (machines différentes)
│   ├── Tp2ProcessB_Remote.java    # Processus B (machines différentes)
│   ├── Tp2ProcessA_StopFromB.java # Processus A (arrêt par B)
│   └── Tp2ProcessB_StopFromB.java # Processus B (arrêt par B)
├── Tp3\                      # TP3 - Client/Serveur TCP avec threads
│   ├── Tp3TCPServer.java    # Serveur TCP multi-clients
│   ├── Tp3TCPClient.java    # Client TCP
│   ├── Tp3UDPServer.java    # Serveur UDP
│   └── Tp3UDPClient.java    # Client UDP
└── Tp4\                      # TP4 - InetAddress, Socket et Thread
    ├── Tp4InetAddressLookup.java   # Résolution d'adresses IP
    ├── Tp4TCPActiveServer.java     # Serveur détection machines actives
    ├── Tp4TCPActiveClient.java     # Client détection machines actives
    ├── Tp4TCPPeriodicServer.java   # Serveur communication périodique
    ├── Tp4TCPPeriodicClient.java   # Client communication périodique
    ├── Tp4LocalProxyServer.java    # Serveur proxy local
    └── Tp4LocalProxyClient.java    # Client proxy local
```

## Comment utiliser

### Interface principale

Lancez l'interface interactive pour découvrir les TP :

```bash
cd "d:\exo Reseaux"
java Main
```

Cette interface vous guidera à travers tous les TP disponibles.

## TP2 - Communication TCP entre processus

### Description
Ce TP couvre la communication TCP entre deux processus A et B sur le port 1027.

### Fichiers

#### Partie 1
- **Tp2A1.java** : Lecture de chaînes au clavier avec arrêt sur "stop"
- **Tp2B1.java** : Copie de fichier utilisant PrintStream

#### Partie 2b - Même machine
- **Tp2ProcessA.java** : Processus A qui lit au clavier et envoie à B
- **Tp2ProcessB.java** : Processus B qui reçoit et affiche les messages
- **Protocole** : A envoie "stop" pour terminer, B détecte et termine

#### Partie 2c - Machines différentes
- **Tp2ProcessA_Remote.java** : Processus A pour connexion distante
- **Tp2ProcessB_Remote.java** : Processus B pour connexion distante
- **Utilisation** : `java Tp2.Tp2ProcessA_Remote <adresse_IP>`

#### Partie 2d-e - Arrêt contrôlé par B
- **Tp2ProcessA_StopFromB.java** : Processus A avec thread de réception
- **Tp2ProcessB_StopFromB.java** : Processus B avec 2 threads (réception + clavier)
- **Protocole** : B lit "stop" au clavier, envoie "STOP_FROM_B" à A

### Compilation et exécution

```bash
# Compilation
javac Tp2/*.java

# Test même machine
java Tp2.Tp2ProcessB    # Terminal 1
java Tp2.Tp2ProcessA    # Terminal 2

# Test arrêt par B
java Tp2.Tp2ProcessB_StopFromB    # Terminal 1
java Tp2.Tp2ProcessA_StopFromB    # Terminal 2
```

## TP3 - Client/Serveur TCP avec threads

### Description
Ce TP couvre les applications client/serveur TCP avec gestion multi-clients et UDP.

### Fichiers

#### TCP
- **Tp3TCPServer.java** : Serveur TCP multi-clients (port 1027)
- **Tp3TCPClient.java** : Client TCP qui se connecte et envoie des messages

#### UDP
- **Tp3UDPServer.java** : Serveur UDP (port 9876)
- **Tp3UDPClient.java** : Client UDP qui envoie des messages

### Compilation et exécution

```bash
# Compilation
javac Tp3/*.java

# Test TCP
java Tp3.Tp3TCPServer    # Terminal 1
java Tp3.Tp3TCPClient    # Terminal 2

# Test UDP
java Tp3.Tp3UDPServer    # Terminal 1
java Tp3.Tp3UDPClient    # Terminal 2
```

## TP4 - InetAddress, Socket et Thread

### Description
Ce TP couvre l'utilisation d'InetAddress, Socket et Thread pour les réseaux.

### Fichiers

#### 1. Résolution d'adresses IP
- **Tp4InetAddressLookup.java** : Affiche l'adresse locale et résout les noms d'hôtes

#### 2. Détection de machines actives (TCP)
- **Tp4TCPActiveServer.java** : Serveur qui répond aux pings (port 1027)
- **Tp4TCPActiveClient.java** : Client qui teste une ou 25 machines
- **Utilisation** : `java Tp4.Tp4TCPActiveClient` puis entrer `192.168.1.10-25`

#### 3. Communication périodique TCP
- **Tp4TCPPeriodicServer.java** : Serveur qui envoie des messages toutes les 3 secondes
- **Tp4TCPPeriodicClient.java** : Client qui reçoit et peut arrêter avec "stop"

#### 4. Architecture proxy local
- **Tp4LocalProxyServer.java** : Proxy sur port 1026 qui interroge port 1027
- **Tp4LocalProxyClient.java** : Client qui envoie `CHECK <hôte>` ou `SCAN <hôte>`

### Compilation et exécution

```bash
# Compilation
javac Tp4/*.java

# Test résolution IP
java Tp4.Tp4InetAddressLookup

# Test détection machines
java Tp4.Tp4TCPActiveServer    # Terminal 1
java Tp4.Tp4TCPActiveClient    # Terminal 2

# Test communication périodique
java Tp4.Tp4TCPPeriodicServer  # Terminal 1
java Tp4.Tp4TCPPeriodicClient  # Terminal 2

# Test proxy
java Tp4.Tp4TCPActiveServer    # Terminal 1 (serveur distant)
java Tp4.Tp4LocalProxyServer   # Terminal 2 (proxy local)
java Tp4.Tp4LocalProxyClient   # Terminal 3 (client proxy)
```

## Technologies utilisées

- **Java** : Langage de programmation principal
- **Sockets TCP/UDP** : Communication réseau
- **Threads** : Programmation concurrente
- **InetAddress** : Résolution d'adresses IP
- **PrintStream/BufferedReader** : Flux d'E/S
- **Scanner** : Lecture clavier

## Ports utilisés

- **1027** : Communications TCP (TP2, TP3, TP4)
- **9876** : Communications UDP (TP3)
- **1026** : Proxy local (TP4)

## Compilation générale

Pour compiler tous les TP :

```bash
# Depuis la racine du projet
javac Tp2/*.java Tp3/*.java Tp4/*.java Main.java
```

## Auteur

Projet créé pour les TP de réseaux en Java.

## Licence

Ce projet est destiné à des fins éducatives.
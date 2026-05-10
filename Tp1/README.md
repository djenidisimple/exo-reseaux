# TP1 - Multicast en C++

Ce dossier contient la conversion en C++ du code Java pour le Multicast (Receiver et Sender).

## Fichiers
- `MulticastReceiver.cpp` : Code source du récepteur.
- `MulticastSender.cpp` : Code source de l'émetteur.
- `MulticastReceiver.exe` : Exécutable du récepteur.
- `MulticastSender.exe` : Exécutable de l'émetteur.

## Compilation (avec MinGW/g++)
```bash
g++ MulticastReceiver.cpp -o MulticastReceiver.exe -lws2_32
g++ MulticastSender.cpp -o MulticastSender.exe -lws2_32
```

## Utilisation

### 1. Lancer le Récepteur
Ouvrez un terminal et lancez le récepteur avec une adresse de groupe Multicast (Classe D) et un port :
```bash
./MulticastReceiver.exe 228.5.6.7 6789
```

### 2. Lancer l'Émetteur
Ouvrez un autre terminal et lancez l'émetteur avec la même adresse et le même port :
```bash
./MulticastSender.exe 228.5.6.7 6789
```

Ensuite, tapez des messages dans le terminal de l'émetteur et appuyez sur Entrée. Ils devraient s'afficher dans le terminal du récepteur.

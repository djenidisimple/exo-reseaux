#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <string>

#pragma comment(lib, "ws2_32.lib")

/**
 * MulticastReceiver.cpp
 * Ported from Java MulticastReceiver
 */

int main(int argc, char* argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " <multicast_group> <port>" << std::endl;
        return 1;
    }

    const char* group = argv[1];
    int port = std::stoi(argv[2]);

    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "WSAStartup failed." << std::endl;
        return 1;
    }

    // Create a UDP socket
    SOCKET sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (sock == INVALID_SOCKET) {
        std::cerr << "Socket creation failed: " << WSAGetLastError() << std::endl;
        WSACleanup();
        return 1;
    }

    // Set reuse address option (Java: ms.setReuseAddress(true))
    BOOL reuse = TRUE;
    if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, (char*)&reuse, sizeof(reuse)) == SOCKET_ERROR) {
        std::cerr << "setsockopt(SO_REUSEADDR) failed: " << WSAGetLastError() << std::endl;
    }

    // Bind to the port
    sockaddr_in localAddr;
    localAddr.sin_family = AF_INET;
    localAddr.sin_port = htons(port);
    localAddr.sin_addr.s_addr = INADDR_ANY;

    if (bind(sock, (sockaddr*)&localAddr, sizeof(localAddr)) == SOCKET_ERROR) {
        std::cerr << "Bind failed: " << WSAGetLastError() << std::endl;
        closesocket(sock);
        WSACleanup();
        return 1;
    }

    // Join the multicast group (Java: ms.joinGroup(ia))
    struct ip_mreq mreq;
    if (inet_pton(AF_INET, group, &mreq.imr_multiaddr.s_addr) != 1) {
        std::cerr << "Invalid multicast group address." << std::endl;
        closesocket(sock);
        WSACleanup();
        return 1;
    }
    mreq.imr_interface.s_addr = INADDR_ANY;

    if (setsockopt(sock, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char*)&mreq, sizeof(mreq)) == SOCKET_ERROR) {
        std::cerr << "setsockopt(IP_ADD_MEMBERSHIP) failed: " << WSAGetLastError() << std::endl;
        closesocket(sock);
        WSACleanup();
        return 1;
    }

    std::cout << "joined multicast group " << group << " on port " << port << std::endl;

    char buffer[1024];
    sockaddr_in from;
    int fromLen = sizeof(from);

    bool done = false;
    while (!done) {
        int bytesReceived = recvfrom(sock, buffer, sizeof(buffer) - 1, 0, (sockaddr*)&from, &fromLen);
        if (bytesReceived == SOCKET_ERROR) {
            std::cerr << "recvfrom failed: " << WSAGetLastError() << std::endl;
            break;
        }

        buffer[bytesReceived] = '\0';
        char fromIP[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &from.sin_addr, fromIP, INET_ADDRSTRLEN);

        std::cout << "Received " << bytesReceived << " bytes from " << fromIP << ": " << buffer << std::endl;
    }

    // Leave group (Java: ms.leaveGroup(ia))
    setsockopt(sock, IPPROTO_IP, IP_DROP_MEMBERSHIP, (char*)&mreq, sizeof(mreq));
    closesocket(sock);
    WSACleanup();

    return 0;
}

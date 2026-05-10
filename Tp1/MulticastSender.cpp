#include <iostream>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <string>

#pragma comment(lib, "ws2_32.lib")

/**
 * MulticastSender.cpp
 * Ported from Java MulticastSender
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

    // Java code joins group even for sender
    struct ip_mreq mreq;
    if (inet_pton(AF_INET, group, &mreq.imr_multiaddr.s_addr) == 1) {
        mreq.imr_interface.s_addr = INADDR_ANY;
        if (setsockopt(sock, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char*)&mreq, sizeof(mreq)) == SOCKET_ERROR) {
            // This might fail if not bound, but we'll try to match Java behavior
            // std::cerr << "setsockopt(IP_ADD_MEMBERSHIP) failed: " << WSAGetLastError() << std::endl;
        }
    }

    sockaddr_in destAddr;
    destAddr.sin_family = AF_INET;
    destAddr.sin_port = htons(port);
    inet_pton(AF_INET, group, &destAddr.sin_addr.s_addr);

    std::string line;
    std::cout << "Begin typing (return to send, Ctrl+C to quit):" << std::endl;
    
    // Equivalent to BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    // while ((sendString = stdin.readLine()) != null)
    while (std::getline(std::cin, line)) {
        // Send packet (Java: ms.send(packet))
        int bytesSent = sendto(sock, line.c_str(), (int)line.length(), 0, (sockaddr*)&destAddr, sizeof(destAddr));
        if (bytesSent == SOCKET_ERROR) {
            std::cerr << "sendto failed: " << WSAGetLastError() << std::endl;
            break;
        }
    }

    closesocket(sock);
    WSACleanup();

    return 0;
}

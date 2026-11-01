# TP3-DAR2026 — Multi-threaded TCP Server

**Course:** Développement d'applications réparties (DAR) — Faculté des Sciences de Sfax, LSI 3
**Activity:** TP 3, Activité 3-1 — Acceptation de plusieurs clients
**Student:** Med Hassan Ben Adi
**Academic year:** 2026 – 2027

---

## 1. Objective

The client/server applications from TP1 and TP2 could only serve one client per server run. This activity turns the server into a **multi-threaded TCP server** that handles several clients at the same time, using Java sockets (`ServerSocket` / `Socket`).

## 2. Project structure

```
TP3-DAR2026/
├── README.md
└── src/
    ├── Server.java          # accepts connections, numbers clients, starts one thread per client
    ├── ClientHandler.java   # thread that serves one client
    └── Client.java          # Java client (server IP and port configurable)
```

## 3. How it works

- **`Server`** opens a `ServerSocket` on port **1234** and loops on `accept()`. Each time a client connects, it increments a counter, prints the client's order number and address (`getRemoteSocketAddress()`), and starts a new `ClientHandler` thread.
- **`ClientHandler`** (extends `Thread`) sends the client its number (`Welcome! You are client n°X`), then reads the client's messages, prints them on the server console and answers with an echo. The client disconnects by typing `exit`.
- **`Client`** connects with `InetSocketAddress(InetAddress, port)`, so the server's IP can be passed as an argument, which makes tests over a local network possible.

Because `accept()` stays in the main thread and each client's communication runs in its own thread, a slow or idle client never blocks the others.

## 4. Requirements

- JDK 8 or later
- Git (for the repository)
- Telnet client (optional, for tests — on Windows: *Control Panel → Programs → Turn Windows features on or off → Telnet Client*)

## 5. Build and run

```bash
cd src
javac *.java

# Terminal 1: start the server
java Server

# Other terminals: start clients
java Client                      # local test (localhost:1234)
java Client 192.168.1.10 1234    # LAN test (replace with the server's IP)

# Or use Telnet
telnet localhost 1234
```

## 6. Tests

### 6.1 Local tests — Telnet clients

Several `telnet localhost 1234` windows opened while the server is running.

> 📸 *Screenshot 1: server console showing client n°1, n°2, n°3 with their addresses*
> 📸 *Screenshot 2: Telnet windows each showing their welcome message with their number*

### 6.2 Local tests — Java clients

Several instances of `Client` connected to the same server.

> 📸 *Screenshot 3: server console with Java clients connected*
> 📸 *Screenshot 4: client terminals exchanging messages with the server*

### 6.3 Local network tests

The server runs on one machine (IP found with `ipconfig`), and the clients run on other machines of the same network with `java Client <server-ip> 1234`.

> 📸 *Screenshot 5: server machine (console + `ipconfig` output)*
> 📸 *Screenshot 6: client machines connected to the server's IP*

## 7. Synthesis

*(Adjust this section to match what you actually observed.)*

In this activity, I modified the single-client server from TP1/TP2 into a multi-threaded server. The main thread only waits for incoming connections with `accept()`. For every accepted socket, a dedicated `ClientHandler` thread is created, so each client is served independently and several clients can be connected at the same time.

The tests confirmed that the server assigns an increasing order number to each client and displays its IP address and port, and that each client receives its own number on connection. Telnet clients and Java clients could be connected simultaneously without blocking each other. In the local network test, the client only needed the server's IP address (through `InetAddress` and `InetSocketAddress`) to connect to the server running on another machine.

**Difficulties / remarks:**
- Telnet is not enabled by default on Windows and must be activated manually.
- For tests across machines, the server's firewall must allow incoming connections on port 1234, and all machines must be on the same network.
- Each thread is created per client without a limit; for a large number of clients, a thread pool (`ExecutorService`) would be a better design.

## 8. Author

Med Hassan Ben Adi — 3rd year Computer Science (GLSI), Faculté des Sciences de Sfax

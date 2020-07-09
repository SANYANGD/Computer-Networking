
# 
# http://localhost:1111/test.txt
# http://127.0.0.1:1111/test.py

import socket
import sys

HOST = ''
PORT = 1111
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('socket created')
try:
    serverSocket.bind((HOST, PORT))
    print('socket bind complete')
except socket.error as msg:
    print('bind failed. error code : ' + str(msg[0]) + ' message ' + msg[1])
    sys.exit()
serverSocket.listen(5)
print('socket now listening')

while True:
    print('ready to serve...')
    connectionSocket, addr = serverSocket.accept()
    print('Connected with ' + addr[0] + ':' + str(addr[1]))
    try:
        message = connectionSocket.recv(1024)
        filename = message.split()[1]
        f = open(filename[1:],'rb')
        outputdata = f.read()
        header = 'HTTP/1.1 200 OK\r\n' \
                 'Connection: close\r\n' \
                 'Connent-Type: text/html/jpg\r\n' \
                 'Connent-Length: %d Bits\n\n' % (len(outputdata))
        connectionSocket.send(header.encode())
        connectionSocket.sendall(outputdata)
        connectionSocket.close()
    except:
        header = 'HTTP/1.1 404 Not Found'
        connectionSocket.send(header.encode())
        connectionSocket.close()

serverSocket.close()

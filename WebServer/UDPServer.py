
import time
from socket import *

serverSocket = socket(AF_INET, SOCK_DGRAM)
serverPort = 820
serverSocket.bind(('', serverPort))
print ('The server is ready to receive')
while True:
    message, clientAddress = serverSocket.recvfrom(2048)
    print ('Receiving message....')
    #modifiedMessage = message.decode().upper()
    #try:
    f = open(message[1:], 'rb')
    outputdata = f.read()
    for i in range(0,len(outputdata)):
        serverSocket.sendto(outputdata[i], clientAddress)
    #except:
    #    header = 'Not Found'
    #    serverSocket.sendto(header.encode(), clientAddress)

    time.sleep(10)
    #serverSocket.sendto(modifiedMessage.encode(), clientAddress)

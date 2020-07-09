
import time
from socket import *

serverSocket = socket(AF_INET,SOCK_STREAM)
serverPort = 12000
serverSocket.bind(('',serverPort))
serverSocket.listen(1)
#listen()执行之后，客户端才可能和服务器建立connect
print('The server is ready to receive')
while True:
    #尝试把 下面这行代码放到while（）前执行
    connectionSocket, addr = serverSocket.accept()
    print('one client is connecting...')

    try:
        filename = connectionSocket.recv(1024)
        f = open(filename[1:],'rb')
        outputdata = f.read()
        connectionSocket.sendall(outputdata)
        connectionSocket.close()
    except:
        header = 'Not Found'
        connectionSocket.send(header.encode())
        connectionSocket.close()

    time.sleep(10)
    connectionSocket.close()

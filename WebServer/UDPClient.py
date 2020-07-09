from socket import *
import time
clientSocket = socket(AF_INET, SOCK_DGRAM)

#message = input("Input lowercase sentence:")
serverName = 'LAPTOP-KO4I7BEJ'
serverPort = 820
#nRet = clientSocket.sendto(message.encode(), (serverName, serverPort))

filename = '/text.jpg'
clientSocket.sendto(filename.encode(), (serverName, serverPort))

# 下面这行代码是否一直在等待数据？
modifiedMessage,serverAddress = clientSocket.recvfrom(2048)
print(modifiedMessage.decode())
time.sleep(20)
clientSocket.close()

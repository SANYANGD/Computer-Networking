
from socket import *

serverName = 'LAPTOP-KO4I7BEJ'
serverPort = 12000
clientSocket = socket(AF_INET, SOCK_STREAM)
# 服务器在运行时，且listen(1)，多个client是否能执行下面这行代码
clientSocket.connect((serverName,serverPort))

filename = '/text.txt'
clientSocket.send(filename.encode())

modifiedSentence = clientSocket.recv(1024)
print ('From Server:', modifiedSentence)
clientSocket.close()

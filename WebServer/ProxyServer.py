
# 
# http://gaia.cs.umass.edu/wireshark-labs/INTRO-wireshark-file1.html

#coding:utf-8
import threading
from socket import *
from time import sleep


def proxyServer(tcpCliSock, addr, message):
    while True:
        # print(message)
        print('Received a connection from: ', addr)
        # Extract the filename from the given message
        filename = message.split()[1].partition('//'.encode())[2].replace('/'.encode(), '_'.encode())
        # print(filename)
        fileExist = 'false'
        # filetouse = '/'.encode() + filename
        # print(filetouse)
        try:
            # Check wether the file exist in the cache
            f = open(filename, 'rb')
            outputdata = f.read()
            fileExist = 'true'
            print('File Exists!')
            # ProxyServer finds a cache hit and generates a response message
            # tcpCliSock.send('HTTP/1.0 200 OK\r\n'.encode())
            sleep(1)
            tcpCliSock.sendall(outputdata)
            sleep(1)
            print('Read from cache')
        # Error handling for file not found in cache
        except IOError:
            print('File Exist: ', fileExist)
            if fileExist == 'false':
                # Create a socket on the proxyserver
                print('Creating socket on proxyserver')
                c = socket(AF_INET, SOCK_STREAM)
                hostn = message.split()[1].partition('//'.encode())[2].partition('/'.encode())[0]
                print('Host Name: ', hostn)
                try:
                    # Connect to the socket to port 80
                    c.connect((hostn, 80))
                    print('Socket connected to port 80 of the host')
                    c.sendall(message)
                    # Read the response into buffer
                    buff = c.recv(40960)
                    sleep(1)
                    tcpCliSock.sendall(buff)
                    sleep(1)
                    # for i in range(0, len(buff)):
                    #    tcpCliSock.send(buff[i])
                    # Create a new file in the cache for the requested file.
                    # Also send the response in the buffer to client socket and the corresponding file in the cache
                    tmpFile = open(filename, 'w')
                    tmpFile.writelines(buff.decode().replace('\r\n', '\n'))
                    tmpFile.close()
                except:
                    print('Illegal request')
            else:
                # HTTP response message for file not found
                # Do stuff here
                print('File Not Found...')
                # tcpCliSock.send('HTTP/1.0 404\r\n'.encode())
        # Close the client and the server sockets
        tcpCliSock.close()

def main():
    # Create a server socket, bind it to a port and start listening
    tcpSerPort = 1234
    tcpSerSock = socket(AF_INET, SOCK_STREAM)
    # Prepare a server socket
    tcpSerSock.bind(('', tcpSerPort))
    tcpSerSock.listen(10)
    while True:
        # Strat receiving data from the client
        tcpCliSock, addr = tcpSerSock.accept()
        message = tcpCliSock.recv(4096)
        temp = message.split()[0]
        if temp == b'GET':
            print('\nReady to serve...')
            client = threading.Thread(target=proxyServer, args=(tcpCliSock, addr, message))
            client.setDaemon(True)
            client.start()

if __name__ == '__main__':
    main()

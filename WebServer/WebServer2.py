import socket
import threading
import sys
import os
# http://localhost:1111/test.txt

# 自定义线程函数
def Server(connectionSocket, addr):
    while True:
        BUFSIZE = 1024
        print('Waiting for the connection：', addr)
        data = connectionSocket.recv(BUFSIZE).decode()
        filename = data.split()[1]
        filename = filename[1:]

        # 当网络质量差没有收到浏览器的访问数据时执行
        if filename == "":
            connectionSocket.close()
            print("请输入要访问的文件")
        base_dir = os.getcwd()
        file_dir = os.path.join(base_dir, filename)
        if os.path.exists(file_dir):
            try:
                f = open(file_dir,encoding = 'utf-8')
                outputdata = f.read()
                header = 'HTTP/1.1 200 OK\r\n' \
                         'Connection: close\r\n' \
                         'Connent-Type: text/html/jpg\r\n' \
                         'Connent-Length: %d Bits\n\n' % (len(outputdata)) + outputdata
                connectionSocket.sendall(header.encode())
                connectionSocket.close()
            except:
                header = 'HTTP/1.1 404 Not Found'
                connectionSocket.send(header.encode())
                connectionSocket.close()

# 程序主入口
def main():
    # 创建TCP套接字
    ADDR = ("", 1111)
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 设置端口复用
    serverSocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, True)
    # 绑定端口
    print('socket created')
    try:
        serverSocket.bind(ADDR)
        print('socket bind complete')
    except socket.error as msg:
        print('bind failed. error code : ' + str(msg[0]) + ' message ' + msg[1])
        sys.exit()
    serverSocket.listen(50)
    print("waiting for connection......\n")
    while True:
        # 等待客户端连接
        connectionSocket, addr = serverSocket.accept()
        print("[new client]:", addr, "connected")
        # 有客户端连接后，创建一个线程将客户端套接字，IP端口传入recv函数，
        t = threading.Thread(target=Server, args=(connectionSocket, addr))
        # 设置线程守护
        t.setDaemon(True)
        # 启动线程
        t.start()

if __name__ == '__main__':
    main()

# JavaFTP
![](./sample.gif)
JavaFTP is a simple FTP server written in Java to understand architecture of server-client application and data transmission using sockets. This application does not use any third-party package, only default Java environment is required. Unlike other server-client application, user must run server program in order to use this application.


# Requirement
* either windows/macos/linux PC
* Java Environment


# Installation
Just clone this repository by
```
git clone https://github.com/KazuMatsuHack9981/JavaFTP.git
```
you don't have to do anything else.


# Structure
server and client will both run on the same directory. Server side files are in `datas/` and list of user is in `userdatas/`. client files are in `JavaFTP/` directory. User can be created by `signup` command but you cannot delete user by command. Each user has their own directory in `datas/` and if you login, the files are uploaded/downloaded within this directory.


# Usage

## Server/Client setup
First, launch the server program by
```
java ServerController
```

after you get "[*] JavaFTP Server started..." as an output, open another terminal and launch the client program application by
```
java CommandLine
```
if both server and client program runned correctly, you will see "JavaFTP" banner popup on client screen and "[*] creating thread1..." log showing up at server log.


## Commands
JavaFTP supports below.  
please DO NOT exit the program by CTRL-C.
```
exit : exit client program.
put [filename] : upload file to ftp server.
get [filename] : download file from ftp server.
delete [filename] : delete file from ftp server.
signup [username] [password] : create ftp user. you have to login after signup.
login [username] [password] : login as [username].
```


## WARNING
This application is for educational purpose only and IS NOT expected for using as a "real ftp program". I will not take any responsibility for the problem caused by this application.

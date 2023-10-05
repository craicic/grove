# Deployment of Grove

Here's a doc describing step to follow.

Command line given in this document are for linux. On windows you can easily launch a WSL terminal. And if you want you can download the (not so) new [Windows Terminal](https://github.com/microsoft/terminal) (it is able to run WSL terminal)

Forward the external port 25892 to internal 22

### Before you start :

If you're working on WSL, you may want to use chmod on your local machine. [Issue here](https://github.com/Microsoft/WSL/issues/81#issuecomment-796798258) 

To do so, create a `/etc/wsl.conf` folder.

Then add the following code :
```
[automount]
enabled = true
options = "metadata"
mountFsTab = false
```

Close all WSL windows!

Open PowerShell.

List your WSL distributions :

`wsl --list`

Pick the one you want to fix. We select "Debian" as an example.
Terminate the Debian WSL instance:

`wsl --terminate Ubuntu`

Open a new WSL terminal and its done.

You can use chmod but remmember that windows permissions override WSL ones.

## Step 1 : Configuring the raspberry Pi

### Static IP address

Let the router allocate a static address via DHCP.

Mac address can be found : `ethtool -P eth0`

### SSH connection

Find the device local address with the following command `ping raspberrypi.local`

Connect to pi via ssh : `sudo ssh user@<IP>`

### raspberry pi base configuration

You can access the config interface via `sudo raspi-config`

### install java via sdkman

instruction can ben found on [https://sdkman.io/install](https://sdkman.io/install)

[the list of available JDKs and the code to install them](https://api.sdkman.io/2/candidates/java/linux/versions/list?installed=)

### install Postgresql

`sudo apt install postgresql-13`

Create the user `CREATE USER postgres WITH PASSWORD 'your-password';`

Create the database `CREATE DATABASE db WITH ONWER postgres;`

You can now connect PSQL with : `sudo psql -U postgres`

### configure DynDNS

Check your router public IPv4.

Forward the 443 to your application's port in your router's configuration.

Make the DNS point to your IP.

[https://infomaniak.com/nic/update](https://infomaniak.com/nic/update)

Install ddclient via `sudo apt-get install ddclient`.

Edit `/etc/ddclient.conf` and add the following :

`
protocol=dyndns2
ssl=yes
use=web
server=infomaniak.com
login=dyndnsLogin
password=dyndnsPassword
example.com
`

### create a folder and set permissions.

Go in /opt and create a dir : `sudo mkdir ./grove`

Give permissions on folder : `sudo chmod a+rwx ./grove`

Ssh copy. Form the ssh client, copy the Jar and put it in the grove folder `sudo scp /mnt/e/code/multi/grove/grove-delivery/target/grove-service-1.1.2.jar  craicic@192.168.1.140:/opt/grove`

Ssh copy again, now the ng dist folder `sudo scp -r /mnt/e/code/multi/grove/grove-delivery/dist  craicic@192.168.1.140:/usr/share/nginx/html`

Test if app starts normally. `java -Dspring.profiles.active=staging -jar Grove-1.1.2.jar`

Detach app from the console : add `nohup` before the command line.
    
You can check the app running using `ps aux | grep java`

And if you want to kill the process : `kill -9 <PID>`


sudo mv /opt/grove/dist /srv/grove/dist
### install certibot

Install on raspi : `sudo apt install certbot`

Then generate the certificate : `sudo certbot certonly --standalone -d example.com`

Certificate are in folder `/etc/letsencrypt/live/example.com/`

### Make the springboot app use the certificate

Add those lines in staging or production properties

```properties
server.ssl.enabled=true
server.ssl.certificate=/etc/letsencrypt/live/example.com/fullchain.pem
server.ssl.certificate-private-key=/etc/letsencrypt/live/example.com/privkey.pem
```

Or course the app must read those folders so make sure it has the permission.

### prepare the firewall

Install UFW `sudo apt install ufw`

Limit port 22 : `sudo ufw limit 22`

Allow Port 8443 : `sudo ufw allow 8443`

Start the firewall : `sudo ufw enable`

Display status : `sudo ufw status`

Alsmot done !

### Backup the raspberry.

Connect an USB drive.

Check the associated partition name with `sudo lsblk`. For example sda1. Mount the partition in media folder with `sudo mount /dev/sda1 /media`

Backup the mmcblk0 folder on your drive : `sudo dd bs=4M if=/dev/mmcblk0 of=/media/sda1`.

### Monitor raspberry pi

RAM usage : `free -m`

CPU infos : `lscpu`

DISK infos : `lsblk`

USB infos : `lsusb`
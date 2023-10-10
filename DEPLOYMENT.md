# Deployment of Grove

***

Here's a doc describing step to follow.

Command line given in this document are for linux. On windows you can easily launch a WSL terminal. And if you want you
can download the (not so) new [Windows Terminal](https://github.com/microsoft/terminal) (it is able to run WSL terminal)

Forward the external port 25892 to internal 22

### Before you start :

If you're working on WSL, you may want to use chmod on your local
machine. [Issue here](https://github.com/Microsoft/WSL/issues/81#issuecomment-796798258)

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

Open a new WSL terminal and it's done.

You can use chmod but remember that windows permissions override WSL ones.

## Step 1 : Configuring the raspberry Pi

***

### Static IP address

Let the router allocate a static address via DHCP.

> Mac address can be found via : `ethtool -P eth0`

### SSH connection

Find the device local address with the following command `ping raspberrypi.local`

Connect to pi via ssh : `sudo ssh user@<IP>`

### Raspberry pi base configuration

You can access the config interface via `sudo raspi-config`

### Install java via sdkman

instruction can ben found on [https://sdkman.io/install](https://sdkman.io/install)

### Install Postgresql

`sudo apt install postgresql-13`

Create the user `CREATE USER postgres WITH PASSWORD 'your-password';`

Create the database `CREATE DATABASE db WITH ONWER postgres;`

You can now connect PSQL with : `sudo psql -U postgres`

### Configure DynDNS

Check your router public IPv4.

Forward the 443 to your application's port in your router's configuration.

Make the DNS point to your IP.

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

### Install certibot

Install it on raspi : `sudo apt install certbot`

Then generate the certificate : `sudo certbot certonly --standalone -d example.com`

Certificate are in folder `/etc/letsencrypt/live/example.com/`


> Make the springboot app use the certificate by adding those lines in staging or production properties
>
>```properties
>server.ssl.enabled=true
>server.ssl.certificate=/etc/letsencrypt/live/example.com/fullchain.pem
>server.ssl.certificate-private-key=/etc/letsencrypt/live/example.com/privkey.pem
>```
>
>Of course the app must read those folders so make sure it has the permission.

### Set up the firewall

Install UFW `sudo apt install ufw`

Limit port 22 : `sudo ufw limit 22`

Allow port 8443 : `sudo ufw allow 8443`.
Allow port 443 as well.

Start the firewall : `sudo ufw enable`

Display status : `sudo ufw status`

## Step 2 : Deploy the backend

***

### Create a folder and set permissions.

Go in /opt and create a dir : `sudo mkdir ./grove`

Give permissions on folder : `sudo chmod a+rwx ./grove`

### Deploy Grove API

Ssh copy. Form the ssh client **not from the ssh host**, copy the Jar and put it in the grove
folder `sudo scp /mnt/e/code/multi/grove/grove-delivery/target/grove-service-1.2.0.jar  user@<ip>:/opt/grove`

Test if app starts normally. `java -Dspring.profiles.active=staging -jar Grove-1.2.0.jar`

Detach app from the console : add `nohup` before the command line.

You can check the app running using `ps aux | grep java`

And if you want to kill the process : `kill -9 <PID>`

## Step 3 : Deploy the frontend

***

### Install Nginx

Via following command line

```bash
sudo apt install nginx
```

Create a folder and make the user owner.

```bash
sudo mkdir -p /var/www/example.com/html
sudo chown -R $USER:$USER /var/www/example.com/html
# if needed
sudo chmod -R 755 /var/www/example.com
```

### Deploy Grove Webapp

Ssh copy again, now the ng dist folder `sudo scp -r /mnt/e/code/multi/grove/grove-delivery/dist/*
user@<ip>:/var/www/example.com/html`

> in case of *access denied* copy it to a new folder you move the content into
> ```bash
> sudo mkdir -p /usr/example.com/html
> sudo mv -r /usr/example.com/html/* /var/example.com/html
> ```

Create a server config file
`sudo nano /etc/nginx/sites-available/example.com`

And paste the following lines :

```
server {
        root /var/www/example.com/html;
        index index.html index.htm index.nginx-debian.html;

        server_name example.com;

        location / {
                try_files $uri $uri/    /index.html?$args;
        }
        listen                  7443 ssl http2;
        listen                  [::]:7443 ssl http2;
        ssl_certificate         /etc/letsencrypt/live/example.com/fullchain.pem;
        ssl_certificate_key     /etc/letsencrypt/live/example.com/privkey.pem;
        ssl_ciphers             EECDH+AESGCM:EECDH+CHACHA20:EECDH+AES;
}
```

Save and exit : *Control + O, hit Enter, Control + X*

Link the file the enable sites that are loaded on nginx start.

`sudo ln -s /etc/nginx/sites-available/example.com /etc/nginx/sites-enabled/`

Test and restart nginx

```bash
sudo nginx -t
sudo systemctl restart nginx
```

It's done !

## Finally

***

### Backup the raspberry pi.

Connect an USB drive.

Check the associated partition name with `sudo lsblk`. For example sda1. Mount the partition in media folder
with `sudo mount /dev/sda1 /media`

Backup the mmcblk0 folder on your drive : `sudo dd bs=4M if=/dev/mmcblk0 of=/media/sda1`.

### Monitor this raspberry pi

RAM usage : `free -m`

CPU infos : `lscpu`

DISK infos : `lsblk`

USB infos : `lsusb`
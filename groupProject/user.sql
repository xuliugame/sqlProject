CREATE USER 'csm'@'localhost' IDENTIFIED BY 'csm';
GRANT ALL PRIVILEGES ON CSM.* TO 'csm'@'localhost';
FLUSH PRIVILEGES;
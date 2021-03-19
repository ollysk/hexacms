# HexaCMS - Hexagonal Multi Module Architecture project

This is Hexa news content management system. You can register, add and search news.

## Running HexaCMS locally

It is a Spring boot multi module application. You can build it using Gradle and run with Docker.

```
git clone https://github.com/ollysk/hexacms.git
cd hexacms
./gradlew build
docker build -t ollysk/hexarepo:hexaport .
docker-compose up
```

## Prerequisites

* JDK 11
* Intellij Idea with Lombok plugin
* Docker

### Post install

* Run http://localhost:8080/reindex to init search index with "MoreLikeThis" feature
* Use admin:admin as login credentials
* If you to want register new account, use http://localhost:5080/ (local SMTP server) to receive
  confirmation mail

### March 2021 - reupload

* Updated to the newest Spring boot 2.4.4
* Removed sensitive data w/ commits history (ReCaptcha, MailJet tokens) and stripped part of fancy
  design features and classes.
* To use, insert your secret/site for ReCaptcha + username/password for Mailjet in application.yml.
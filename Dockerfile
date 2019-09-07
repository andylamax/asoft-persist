FROM ubuntu:19.10

ENV APPLICATION_USER andylamax

RUN echo y | adduser $APPLICATION_USER

RUN mkdir -p /app /media/andylamax/63C23C360914D355/Installers/cross-platform/Sdk /home/andylamax/.gradle && chown -R $APPLICATION_USER /app /media/andylamax/63C23C360914D355/Installers/cross-platform/Sdk /home/andylamax/.gradle

RUN apt-get update

RUN echo Y | apt-get install openjdk-8-jdk

RUN usermod -aG sudo $APPLICATION_USER

USER $APPLICATION_USER

WORKDIR /app

CMD "/bin/sh"
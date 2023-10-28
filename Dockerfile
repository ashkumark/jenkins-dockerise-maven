

#FROM jenkins/jenkins:latest-jdk8
#FROM jenkins/jenkins:lts
FROM jenkins/jenkins:jdk11
LABEL maintainer="ash"

WORKDIR /home/docker-jenkins-test
COPY src /home/docker-jenkins-test/src
COPY pom.xml /home/docker-jenkins-test

ENV JAVA_OPTS="-Xmx8192m"
ENV JENKINS_OPTS="--logfile=/var/log/jenkins/jenkins.log"

USER root

RUN mkdir /var/log/jenkins
RUN chown -R  jenkins:jenkins /var/log/jenkins

#Docker - https://docs.docker.com/engine/api/
ENV DOCKER_CHANNEL stable
ENV DOCKER_VERSION 23.0.6
ENV DOCKER_API_VERSION 1.42
RUN curl -fsSL "https://download.docker.com/linux/static/${DOCKER_CHANNEL}/x86_64/docker-${DOCKER_VERSION}.tgz" \
  | tar -xzC /usr/local/bin --strip=1 docker/docker

#Docker compose - https://docs.docker.com/compose/release-notes/
ENV DOCKER_COMPOSE_VERSION v2.22.0
RUN curl -fsSL "https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64" \
    -o /usr/local/bin/docker-compose

#RUN curl -fsSL "https://sourceforge.net/projects/docker-compose.mirror/files/${DOCKER_COMPOSE_VERSION}/docker-compose-linux-x86_64/download"  \
#    -o /usr/local/bin/docker-compose

RUN chmod +x /usr/local/bin/docker-compose

RUN groupadd docker
RUN usermod -aG docker jenkins
RUN usermod -aG sudo jenkins
USER jenkins
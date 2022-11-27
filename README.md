# Demo app to integrate ibm mq with Spring boot

- Tutorial: https://developer.ibm.com/tutorials/mq-jms-application-development-with-spring-boot/

- To start up local mq server in docker:
https://github.com/pks9862728888/mq-container

- Command:
docker volume create qm1data
docker run \
    --env LICENSE=accept \
    --env MQ_QMGR_NAME=QM1 \
    --env MQ_ENABLE_METRICS=true \
    --publish 1414:1414 \
    --publish 9443:9443 \
    --publish 9157:9157 \
    --detach \
    --volume qm1data:/mnt/mqm \
    icr.io/ibm-messaging/mq

- MQ web console: https://localhost:9443/ibmmq/console/#/
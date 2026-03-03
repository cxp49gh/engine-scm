#!/bin/sh
FROM kd-bd02.kuandeng.com/library/anapsix/alpine-java:8
MAINTAINER "cxp"

RUN mkdir -p /data/lib /data/logs /data/data
WORKDIR /data

COPY docker-entrypoint.sh /data/
COPY target/road-data-branch-management.jar /data/lib/

RUN chmod +x /data/docker-entrypoint.sh

EXPOSE 8888

#HEALTHCHECK --interval=5s --timeout=3s CMD curl --silent --fail localhost:8888/road-data-branch-management/health || exit 1

ENTRYPOINT ["/bin/sh", "/data/docker-entrypoint.sh"]

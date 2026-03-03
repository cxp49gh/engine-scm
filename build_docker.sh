#!/bin/zsh

echo "tag name:\n"
read tag
docker build -t road-data-branch-management:$tag .

docker tag road-data-branch-management:$tag kd-bd02.kuandeng.com/kd/road-data-branch-management:$tag
docker push kd-bd02.kuandeng.com/kd/road-data-branch-management:$tag
#!/bin/sh
sleep 10

until mongosh --eval "db.adminCommand('ping')"; do
  echo "Waiting for MongoDB to start..."
  sleep 2
done

mongosh --host mongodb /data/init-replica.js
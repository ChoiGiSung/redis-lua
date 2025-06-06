#!/bin/bash

echo "Waiting for Redis nodes to start..."
sleep 5
echo "Creating cluster..."
yes yes | redis-cli --cluster create \
  redis-node-1:6379 \
  redis-node-2:6379 \
  redis-node-3:6379 \
  redis-node-4:6379 \
  redis-node-5:6379 \
  redis-node-6:6379 \
  --cluster-replicas 1

echo "Cluster creation finished."

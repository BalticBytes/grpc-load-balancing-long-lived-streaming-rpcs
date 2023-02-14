# Improving Load Balancing of Long-lived Streaming RPCs for gRPC-enabled Inter-service Communication

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.7641639.svg)](https://doi.org/10.5281/zenodo.7641639)

# Steps to Reproduce

We provide the following utility scripts to help reproduce our results:

1. `experiment-1.sh` showcases the chain reaction of server failures
2. `experiment-2.sh` runs the experiment which shows the server's response to increasing workloads

## Requirements

- Docker Engine Version: **v20.10.21** with **buildkit** enabled
- Docker Compose Version: **v2.12.2**

## Clean up after experiments

We suggest you run the following after one experiment before you start another

```bash
# restart docker daemon
sudo systemctl daemon-reload
sudo systemctl restart docker

docker container stop $(docker container ls -aq) && docker system prune -af --volumes

docker network rm $(docker network ls -q)
docker rmi $(docker images -q)
docker images -f dangling=true
```
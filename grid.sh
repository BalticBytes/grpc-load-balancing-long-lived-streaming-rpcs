#! /bin/bash

timestamp() {
  date +"%Y-%m-%d %H:%M:%S" # current time
}

m=4
rvars="0"
mvars="500"
rivars="500"
sivars="100"
compose_flag="normal"
tag="normal"
max_conn_age_grace="0"
servers="2"
clients="100"
reqs="6"
msgs="20"
reqsec="5"
dry_run=0

for i in "$@"
do
  case $i in
    -s=*|--servers=*) 
      servers="${i#*=}"
      shift 
      ;;
    -c=*|--clients=*) 
      clients="${i#*=}"
      shift 
      ;;
    -n=*|--num-experiments=*) 
      m="${i#*=}"
      shift 
      ;;
    --num-requests=*) 
      reqs="${i#*=}"
      shift 
      ;;
    --num-messages=*) 
      msgs="${i#*=}"
      shift
      ;;
    --var-requests=*) 
      rvars="${i#*=}"
      shift 
      ;;
    --duration-req=*) 
      reqsec="${i#*=}"
      shift 
      ;;
    --var-messages=*) 
      mvars="${i#*=}"
      shift
      ;;
    --max-conn-age-grace=*)
      max_conn_age_grace="${i#*=}"
      shift
      ;;
    -ri=*|--release-interval=*) 
      rivars="${i#*=}"
      shift
      ;;
    --disconnect)
      compose_flag="disconnect"
      tag="disconnect"
      max_conn_age_grace="50"
      shift
      ;;
    --flow-control)
      compose_flag="flow-control"
      tag="flow-control"
      shift
      ;;
    --dry-run)
      dry_run=1
      shift
      ;;
    --*|-*) echo "Unknown option $1"; exit 1 ;;
    *) ;;
  esac
done

export experiment_tag=$tag

run() {
  if [[ $dry_run == 0 ]]; then 
    ./run-multiple-experiments.sh "$@"
  else
    echo "$@"
  fi
}

for i in $rvars; do
  for j in $mvars; do
    for r in $rivars; do
      for si in $sivars; do
        for s in $servers; do
          for c in $clients; do
            for nreq in $reqs; do
              for nmsg in $msgs; do
                for req_s in $reqsec; do
                  for grace in $max_conn_age_grace; do
                    run --quiet --file=$compose_flag \
                    --sensing-interval=$si --release-interval=$r \
                    --num-requests=$nreq --num-messages=$nmsg --duration-req=$req_s \
                    --var-requests=$i --var-messages=$j \
                    --max-conn-age-grace=$grace \
                    --num-experiments=$m --servers=$s --clients=$c
                  done
                done
              done
            done
          done
        done
      done
    done
  done
done

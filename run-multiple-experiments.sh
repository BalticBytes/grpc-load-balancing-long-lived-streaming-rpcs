#! /bin/bash

timestamp() {
  date +"%Y-%m-%d %H:%M:%S" # current time
}

function dotenv {
  set -a
  if [[ -f "$1.env" ]]; then 
    source "$1.env"
  else
    echo "Cant find $1.env"
    return 1
  fi
  set +a
}

# Default values
number_of_experiments=1
skip_run=0
build=0
quiet=0
only=0
only_name=""
metadata=0
docker_compose_path="docker/docker-compose.yaml"

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
      number_of_experiments="${i#*=}"
      shift 
      ;;
    --num-requests=*) 
      num_requests="${i#*=}"
      shift 
      ;;
    --num-messages=*) 
      num_messages="${i#*=}"
      shift 
      ;;
    --var-requests=*) 
      var_req="${i#*=}"
      shift 
      ;;
    --var-messages=*) 
      var_msg="${i#*=}"
      shift 
      ;;
    --duration-req=*) 
      request_time_s="${i#*=}"
      shift 
      ;;
    --release-interval=*) 
      release_interval="${i#*=}"
      shift 
      ;;
    --sensing-interval=*) 
      sensing_interval="${i#*=}"
      shift 
      ;;
    --max-conn-age-grace=*) 
      max_conn_age_grace="${i#*=}"
      shift 
      ;;
    -p=*|--lb-policy=*)
      test="${i#*=}"
      case $test in
        ""|"pick_first"|"round_robin")
          lb_policy="${i#*=}"
          shift
          ;;
        *)
          echo "Error: Unknown lb_policy \"$test\""
          exit 1
          ;;
      esac
      ;;
    -f=*|--file=*)
      test="${i#*=}"
      docker_compose_path="docker/$test.docker-compose.yaml"
      shift
      ;;
    --quiet)
      quiet=1
      shift
      ;;
    --build-and-exit)
      build=1
      shift
      ;;
    --skip-run)
      skip_run=1
      metadata=1
      shift
      ;;
    --metadata)
      metadata=0
      shift
      ;;
    --only=*)
      only=1
      only_name="${i#*=}";
      shift
      ;;
    --*|-*) echo "Unknown option $1"; exit 1 ;;
    *) ;;
  esac
done

# child processes inherit exported variables
export override_server_replicas="$servers"
export override_server_release_interval_ms="$release_interval"
export override_server_sensing_interval_ms="$sensing_interval"
export override_server_max_grace_conn_age_ms="$max_conn_age_grace"
export override_server_max_idle_time_ms="10000"
export override_client_replicas="$clients"
export override_client_lb_policy="$lb_policy"
export override_client_variance_request="$var_req"
export override_client_variance_message="$var_msg"
export override_client_request_time_s="$request_time_s"
export override_client_num_requests="$num_requests"
export override_client_num_messages="$num_messages"
export override_client_initial_delay_s="15"

function run_experiment {
  local origin
  origin=$(pwd)
  cd docker
  
  if [[ $quiet == 1 ]]; then
    docker compose -f "$docker_compose_path" build --no-cache &> /dev/null
  else
    docker compose -f "$docker_compose_path" build --no-cache
  fi
  echo "$(timestamp)   docker compose build"
  
  # It's important to bring the cluster down, otherwise the logs from previous experiments persist
  if [[ $quiet == 1 ]]; then
    docker compose -f "$docker_compose_path" down &> /dev/null
  else
    docker compose -f "$docker_compose_path" down
  fi
  echo "$(timestamp)   docker compose down"
  
  # We can't just --abort-on-container-exit --exit-code-from monitor because docker compose shutsdown when any container shutsdown.
  # The exit code for the shutdown is simply taken from monitor ...
  # Container's need to wait with shutting down until the whole experiment is over.
  if [[ $quiet == 1 ]]; then
    docker compose -f "$docker_compose_path" up --no-color &> /dev/null
  else
    docker compose -f "$docker_compose_path" up 
  fi
  echo "$(timestamp)   docker compose up"
  filename="$1.$RANDOM.log"
  echo "$(timestamp) Logging to file: $filename"
  docker compose -f "$docker_compose_path" logs server client > "$filename"
  # docker compose logs monitor > "$1-monitor.log"
  # docker compose logs envoy > "$1-envoy.log"
  cd "$origin"
  return 0
}

tag="$experiment_tag"
today=$(date +"%Y-%m-%d")
folder="experiments/$today-$tag-$RANDOM"
mkdir "$folder"

if [[ $skip_run == 0 ]]; then 
  if [[ $build == 0 ]]; then 
    echo "Starting experiments with the following parameters: 
    > number of experiments = \"$number_of_experiments\"
    > number of servers     = \"$override_server_replicas\"
    > number of clients     = \"$override_client_replicas\"
    > number of request     = \"$override_client_num_requests\"
    > number of messages    = \"$override_client_num_messages\"
    > variance of requests  = \"$override_client_variance_request\"
    > variance of messages  = \"$override_client_variance_message\"
    > release interval      = \"$override_server_release_interval_ms\"
    > sensing interval      = \"$override_server_sensing_interval_ms\"
    > maxConnectionAgeGrace = \"$override_server_max_grace_conn_age_ms\"
    "
  fi

  for i in $(seq 1 "$number_of_experiments"); do
    echo "$(timestamp) start     Experiment $i/$number_of_experiments"
    SECONDS=0
    run_experiment "$folder/experiment-$i"
    echo "$(timestamp) end ($SECONDS) Experiment $i/$number_of_experiments"
  done
fi

echo "$(timestamp) Done $folder"
#! /bin/bash

timestamp() {
  date +"%Y-%m-%d %H:%M:%S" # current time
}

# This experiment should give us results for showcasing the cascading failure scenario

s="2"
c="100"
mvar="50"
rvar="5000"
n="2"
nreq="3"
nmsg="50"
grace="5000"
rsec="20"

grid() {
  ./grid.sh --servers="$s" --clients="$c" \
  --var-requests="$rvar" --var-messages="$mvar" \
  --num-requests="$nreq" --num-messages="$nmsg" --duration-req="$rsec" \
  --num-experiments="$n" "$@"
}

echo "$(timestamp) Experiment 1: Disconnect"
grid --disconnect --max-conn-age-grace="$grace"
echo "$(timestamp) Experiment 1: Flow Control"
grid --flow-control
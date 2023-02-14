#! /bin/bash

timestamp() {
  date +"%Y-%m-%d %H:%M:%S" # current time
}

mvar="50"
rvar="5000"
n="2"
rsec="20"

grid() {
  ./grid.sh --var-requests="$rvar" --var-messages="$mvar" \
  --duration-req="$rsec" \
  --num-experiments="$n" "$@"
}


echo "$(timestamp) Disconnect Test: Number of Messages"
grid --servers="2" \
     --clients="100" \
     --num-requests="5" \
     --num-messages="50 100 150 200 250" \
     --disconnect

echo "$(timestamp) Flow Control Test: Number of Messages"
grid --servers="2" \
     --clients="100" \
     --num-requests="5" \
     --num-messages="50 100 150 200 250" \
     --flow-control

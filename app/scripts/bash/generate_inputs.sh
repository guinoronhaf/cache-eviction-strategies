#!/bin/bash

#comeco
INCREMENT=50000
FINAL_VALUE=1000000
MAX_VALUE=2000000
workload_type="$1"
initial_value=$2
current_value=$initial_value

while [ $current_value -le $FINAL_VALUE ]; do
    $(python3 ./app/scripts/python/main_${workload_type}.py ${current_value} ${MAX_VALUE} > ./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt)
    current_value=$(( $current_value + $INCREMENT))
done

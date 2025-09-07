#!/bin/bash

FINAL_VALUE=1000000
MAX_VALUE=2000000
INCREMENT=50000

cache_strategy="$1"
workload_type="$2"
workload_size=$3

current_value=$workload_size

while [ $current_value -le $FINAL_VALUE ]; do
    $(gradle runMainWorkload --quiet --args="${cache_strategy}" < ./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt >> ./app/data/output/${cache_strategy}/${workload_type}_output.data)
    current_value=$(( $current_value + $INCREMENT ))
done

#!/bin/bash


# --- Constantes numéricas ---
increment=50000 
final_value=1000000 
max_value=5000000 

# --- Parâmetros ---
workload_type="$1"
initial_value=$2

current_value=$initial_value

python_script_file="./app/scripts/python/main_${workload_type}.py"
output_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"

while [ $current_value -le $final_value ]; do
    $(python3 ${python_script_file} ${current_value} ${max_value} > ${output_file})
    current_value=$(( $current_value + $increment ))
    output_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"
done

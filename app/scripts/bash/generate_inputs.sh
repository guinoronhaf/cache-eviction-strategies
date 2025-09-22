#!/bin/bash

# esse script gera, de forma automatizada, os inputs necessários para a avaliação de cada política de cache em workloads de tipos específicos.

# --- Constantes numéricas ---
# increment: incremento de tamanho do workload a cada iteração
# final_value: tamanho do workload mais comprido
# max_value: valor máximo do numéro em cada linha do workload
increment=50000 
final_value=1000000 
max_value=5000000 

# --- Parâmetros ---
# $1: tipo de workload ("randomic", "spike", "periodic")
# $2: tamamho do workload mais curto
workload_type="$1"
initial_value=$2

# definição do comprimento inicial dos workload para posterior incremento
current_value=$initial_value

# definindo diretórios e arquivos importantes
python_script_file="./app/scripts/python/main_${workload_type}.py"
output_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"

while [ $current_value -le $final_value ]; do
    $(python3 ${python_script_file} ${current_value} ${max_value} > ${output_file})
    current_value=$(( $current_value + $increment ))
    output_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"
done

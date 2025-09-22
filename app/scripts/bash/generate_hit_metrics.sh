#!/bin/bash

final_value=1000000
max_value=2000000
increment=50000

cache_strategy="$1"
workload_type="$2"
workload_size=$3

current_value=$workload_size

# Adiciona uma mensagem inicial para o utilizador
echo "-> Iniciando geração de outputs para a estratégia '${cache_strategy}' com workload '${workload_type}'..."
output_file="./app/data/output/${cache_strategy}/hit_metric/${workload_type}_hit_metric.data"
echo "-> Os resultados serão salvos em: ${output_file}"

echo "CacheStrategy WorkloadLength Hits" > ${output_file}

while [ $current_value -le $final_value ]; do
    # Adiciona uma mensagem para cada iteração do loop
    echo "   Processando workload de tamanho ${current_value}..."
    input_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"

    
    # Linha de comando original, sem alterações na lógica
    $(gradle runMainWorkload --quiet --args="${cache_strategy}" < ${input_file} >> ${output_file})
    
    current_value=$(( $current_value + $increment ))
done

# Adiciona uma mensagem final de conclusão
echo "-> Processo concluído."


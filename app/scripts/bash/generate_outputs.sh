#!/bin/bash

FINAL_VALUE=1000000
MAX_VALUE=2000000
INCREMENT=50000

cache_strategy="$1"
workload_type="$2"
workload_size=$3

current_value=$workload_size

# Adiciona uma mensagem inicial para o utilizador
echo "-> Iniciando geração de outputs para a estratégia '${cache_strategy}' com workload '${workload_type}'..."
output_file="./app/data/output/${cache_strategy}/${workload_type}_output.data"
echo "-> Os resultados serão salvos em: ${output_file}"

while [ $current_value -le $FINAL_VALUE ]; do
    # Adiciona uma mensagem para cada iteração do loop
    echo "   Processando workload de tamanho ${current_value}..."
    
    # Linha de comando original, sem alterações na lógica
    $(gradle runMainWorkload --quiet --args="${cache_strategy}" < ./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt >> ${output_file})
    
    current_value=$(( $current_value + $INCREMENT ))
done

# Adiciona uma mensagem final de conclusão
echo "-> Processo concluído."


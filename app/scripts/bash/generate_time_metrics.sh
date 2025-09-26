#!/bin/bash

# --- Configuração ---
# Este script executa a análise de performance (tempo médio de hit/miss) para uma
# estratégia de cache específica, usando os workloads gerados anteriormente.
# Ele chama a classe Java 'MainPerformance'.

# Parâmetros:
# $1: A estratégia de cache (ex: "fifo", "lru", "lfu").
# $2: O tipo de workload (ex: "spike", "periodic").
# $3: O tamanho inicial do workload para começar a análise.

# Variáveis de controle do loop
FINAL_VALUE=1000000
INCREMENT=50000

# --- validação dos Argumentos ---
if [ "$#" -ne 3 ]; then
    echo "Uso: $0 <estrategia_cache> <tipo_workload> <tamanho_inicial>"
    echo "Exemplo: $0 fifo spike 50000"
    exit 1
fi

# --- Definição de variáveis ---
cache_strategy="$1"
workload_type="$2"
workload_size=$3

current_value=$workload_size

# --- Preparação do Diretório e Arquivo de Saída ---
output_dir="./app/data/output/${cache_strategy}/time_metric"
output_file="${output_dir}/${workload_type}_time_metric.data"

mkdir -p ${output_dir}

echo "CacheStrategy WorkloadLength CacheSize AverageHitTime AverageMissTime" > ${output_file}

echo "-> Iniciando análise de performance para a estratégia '${cache_strategy}' com workload '${workload_type}'..."
echo "-> Os resultados serão salvos em: ${output_file}"

while [ $current_value -le $FINAL_VALUE ]; do
    input_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"
    echo "   Analisando workload de tamanho ${current_value}..."
    
    gradle runMainPerformance --quiet --args="${cache_strategy}" < ${input_file} >> ${output_file}
    
    current_value=$(( $current_value + $INCREMENT ))
done

echo "-> Análise de performance concluída."

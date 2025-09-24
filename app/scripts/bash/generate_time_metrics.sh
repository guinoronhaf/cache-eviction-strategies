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

# --- Validação dos Argumentos ---
if [ "$#" -ne 3 ]; then
    echo "Uso: $0 <estrategia_cache> <tipo_workload> <tamanho_inicial>"
    echo "Exemplo: $0 fifo spike 50000"
    exit 1
fi

cache_strategy="$1"
workload_type="$2"
workload_size=$3

current_value=$workload_size

# --- Preparação do Diretório e Arquivo de Saída ---
# MODIFICADO: O caminho base agora aponta para 'app/data/output'
output_dir="./app/data/output/${cache_strategy}/time_metric"
# MODIFICADO: O nome do arquivo agora inclui '_performance' para diferenciar da análise de hits
output_file="${output_dir}/${workload_type}_time_metric.data"

# Cria o diretório de saída se ele não existir
mkdir -p ${output_dir}

# Cria o arquivo de dados e adiciona um cabeçalho para clareza
# O '>' apaga o arquivo se ele existir, garantindo um novo começo para cada execução completa do script.
echo "CacheStrategy WorkloadLength CacheSize AverageHitTime AverageMissTime" > ${output_file}

echo "-> Iniciando análise de performance para a estratégia '${cache_strategy}' com workload '${workload_type}'..."
echo "-> Os resultados serão salvos em: ${output_file}"

# --- Loop de Análise ---
while [ $current_value -le $FINAL_VALUE ]; do
    input_file="./app/data/input/${workload_type}_inputs/${workload_type}_input_${current_value}.txt"
    echo "   Analisando workload de tamanho ${current_value}..."
    
    # Executa a classe MainPerformance, passando o input e anexando (>>) o output ao arquivo de dados
    gradle runMainPerformance --quiet --args="${cache_strategy}" < ${input_file} >> ${output_file}
    
    # Incrementa o contador
    current_value=$(( $current_value + $INCREMENT ))
done

echo "-> Análise de performance concluída."


# esse arquivo funciona como main para o arquivo randomic_workload.py
# para executá-lo, você deve passar dois parâmetros:
# 1) a quantidade de elementos do workload
# 2) o máximo valor que cada elemento pode assumir
#
# Ex.:
# python3 main_randomic.py 150000 1000000
import sys
from randomic_workload import generate_random_workload

def main():
    try:
        workload_size = int(sys.argv[1])
        max_value = int(sys.argv[2])
        generate_random_workload(workload_size, max_value)
    except:
        print("ERROR!")

main()

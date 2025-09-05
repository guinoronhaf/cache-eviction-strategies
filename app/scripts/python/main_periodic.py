# esse arquivo funciona como main para o arquivo periodic_workload.py
# para executá-lo, você deve passar dois argumentos:
# 1) a quantidade de elementos do workload
# 2) o máximo valor que cada elemento pode assumir
#
# Ex.:
# python3 main_randomic.py 150000 1500 1000000
import sys
from periodic_workload import periodic_workload

def main():
    try:
        workload_size = int(sys.argv[1])
        max_value = int(sys.argv[2])
        periodic_workload(workload_size, max_value)
    except:
        print("ERRO!")

main()

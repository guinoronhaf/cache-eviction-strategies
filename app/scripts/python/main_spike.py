# esse arquivo funciona como main para o arquivo spike_workload.py
# para executá-lo, você deve passar três argumentos:
# 1) a quantidade de elementos do workload
# 3) a quantidade de elementos em cada pico
# 2) o máximo valor que cada elemento pode assumir
#
# Ex.:
# python3 main_randomic.py 150000 1500 1000000
import sys
from spike_workload import generate_spike_workload

def main():
    try:
        workload_size = int(sys.argv[1])
        max_value = int(sys.argv[2])
        workload = generate_spike_workload(workload_size, max_value)
        for line in workload:
            print(line)
    except:
        print("ERROR!")


main()

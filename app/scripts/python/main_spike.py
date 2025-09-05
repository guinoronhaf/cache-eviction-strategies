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
        spike_freq = int(sys.argv[2])
        max_value = int(sys.argv[3])
        generate_spike_workload(workload_size, spike_freq, max_value)
    except:
        print("ERROR!")


main()

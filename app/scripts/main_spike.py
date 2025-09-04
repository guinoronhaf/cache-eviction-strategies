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

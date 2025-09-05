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

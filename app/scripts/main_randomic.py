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

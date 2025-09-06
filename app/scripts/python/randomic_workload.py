import random

def generate_random_workload(workload_size, max_value):
    i = 0
    while i < workload_size:
        print(random.randint(0, max_value))
        i += 1

def generate_random_workload_list(workload_size, max_value):
    randomic_workload = list()
    i = 0
    while i < workload_size:
        randomic_workload += [random.randint(0, max_value + 1)]
        i += 1
    return randomic_workload

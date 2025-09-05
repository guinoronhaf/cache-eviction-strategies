import random

def generate_random_workload(workload_size, max_value):
    i = 0
    while i < workload_size:
        print(random.randint(0, max_value))
        i += 1

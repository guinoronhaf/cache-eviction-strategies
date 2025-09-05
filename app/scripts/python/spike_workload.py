import random
from randomic_workload import generate_random_workload

def generate_spike_map(spike_size: int, spike_freq: int, max_value: int) -> dict:
    spike_map = dict()
    number_of_elements = spike_size // spike_freq
    count = 0
    while count < number_of_elements:
        random_value = random.randint(0, max_value)
        spike_map[random_value] = spike_freq
        count += 1
    return spike_map

def generate_spike_workload(workload_size: int, spike_freq: int, max_value: int):
    spike_map = generate_spike_map(workload_size // 2, spike_freq, max_value)
    count = 0
    for value, freq in spike_map.items():
        generate_random_workload(spike_freq, max_value)
        j = 0
        while j < freq:
            print(value)
            j += 1
        count += spike_freq + freq
    if count < workload_size:
        generate_random_workload(workload_size - count, max_value)

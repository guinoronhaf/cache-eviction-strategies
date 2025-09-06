import random
from randomic_workload import generate_random_workload_list 

MIN_NUM_SPIKE = SPIKE_SIZE_FACTOR = 5
MIN_SPIKE_FREQ = 10
SPIKE_GAP = 5000

def generate_spike_workload(workload_size, max_value):
    num_spikes = max(MIN_NUM_SPIKE, workload_size // SPIKE_GAP)
    spike_freq = max(MIN_SPIKE_FREQ, workload_size // (num_spikes * SPIKE_SIZE_FACTOR))
    spike_workload = list()
    spike_workload = generate_random_workload_list(workload_size, max_value)
    spike_pos = random.sample(range(0, workload_size), num_spikes)
    for position in spike_pos:
        spike_value = random.randint(0, max_value + 1)
        right = min(position + spike_freq, workload_size)
        spike_workload[position:right] = [spike_value] * (right - position)
    return spike_workload

import random

def carga_menor(periodo, max_value):
    freq = []
    for i in range(periodo):
        freq.append(random.randint(0, max_value - 1))
    return freq

def periodic_workload(workload_size, max_value):
    periodoMaior = int(0.2 * workload_size);
    periodoMenor = int(0.05 * workload_size);
    carga_menor_lista = carga_menor(periodoMaior, max_value)
    
    cont = 0
    while cont < 4:
        for num in carga_menor_lista:
            print(num)
        cont += 1
        
        for i in range(periodoMenor):
            print(random.randint(0, max_value - 1))



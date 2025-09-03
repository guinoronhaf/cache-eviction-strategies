import random

INTERVALO = 200000

def carga_menor():
    freq = []
    for i in range(40000):
        freq.append(random.randint(0, INTERVALO - 1))
    return freq

def main():
    carga_menor_lista = carga_menor()
    
    cont = 0
    while cont < 4:
        for num in carga_menor_lista:
            print(num)
        cont += 1
        
        for i in range(10000):
            print(random.randint(0, INTERVALO - 1))

if __name__ == "__main__":
    main()

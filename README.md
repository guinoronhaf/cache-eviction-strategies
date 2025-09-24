# Estratégias de _Cache Eviction_

## Sumário

- [O que é cache?](#o-que-é-cache)

- [Cache Eviction](#cache-eviction)

- [Estratégias abordadas nesse material](#estratégias-abordadas-nesse-material)

- [Indicadores *hit* e *miss*](#indicadores-hit-e-miss)

- [Workloads](#workloads)

- [Testes de carga](#testes-de-carga)

- [Resultados](#resultados)

## O que é _cache_?
_Cache_ é um tipo de memória limitada e de acesso rápido, na qual diversos dados são armazenados visando à sua própria obtenção em momentos posteriores com alta rapidez. Em aplicações reais, podem ser utilizadas diversas **estratégias de cache** com o intuito de aumentar a eficiência do processo, otimizando espaço e mantendo em cache dados adequados segundo critérios específicos. Nesse contexto, é fundamental entender o comportamento das variadas **políticas/estratégias de cache** implementadas em sistemas computacionais, pontuando sua eficiência e otimização, seja a partir de análises quantitativas ou qualitativas.

## _Cache Eviction_
Em se tratanto de _Cache_, um conceito fundamental a ser compreendido é o de _Cache Eviction_. Por se tratar de uma memória limitada, é preciso selecionar quais elementos devem permanecer em _cache_ e quais devem sair. Nesse cenário, é possível investigar as nuances de cada estratégia/política a fim de compreender como cada uma delas seleciona dados para remoção (ou "evicção"). Critérios como frequência, recência, ordem de inserção e aleatoriedade podem ser utilizados para definição de remoções, o que viabiliza, em cenários concretos, a otimização da memória de _cache_.

## Indicadores *hit* e *miss*
Dentro do contexto de cada **estratégia de cache**, é possível analisar tanto a quantidade de buscas por um elemento já presente em _cache_ - o que implica em acesso rápido e, portanto, eficiência - quanto a ida à memória secundária, de acesso lento e de pouca eficiência. O primeiro caso é conhecido como _**hit**_, o qual é extremamente desejável em qualquer política de _cache_, já que indica boa otimização e busca veloz. Por outro lado, a situação antagônica é denominada _**miss**_, e indica uma travessia custosa rumo à memória secundária, com processos pouco eficientes. Portanto, indicadores das quantidades de _hit_ e _miss_ podem servir de base para a construção de uma análise sólida sobre a eficiência de cada uma das estratégias de _cache_ abordadas nesse projeto.

## Estratégias abordadas nesse material

  1. *FIFO (First-in First-Out)*

  2. *LFU (Least Frequently Used)*
  
  3. *LRU (Least Recently Used)*

  4. *Random Replacement*
  
  5. *Second Chance (Clock)*

## Workloads

### Randomic
### Periodic
### Spike

## Testes de carga

## Resultados

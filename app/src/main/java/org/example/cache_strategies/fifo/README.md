# _FIFO Eviction Strategy_

## Definição
 Suponha que você vá a um banco e, para ser atendido, haja uma fila de espera. A convenção do que deve acontecer nessa situação é bem conhecida: quem está há mais tempo esperando será atendido primeiro, ou seja, respeita-se uma ordem de chegada. É essa lógica que uma estrutura de dados FIFO (First In, First Out (o primeiro a chegar é o primeiro a sair)) segue. No contexto do design e da produção de _caches_, a lógica FIFO se apresenta como boa solução para certos desafios apresentados e, dessa forma, tem papel relevante no mercado de caches.

## _FIFO_ como estratégia de _cache_
  Uma vez que o objetivo de um dispositivo _cache_ é diminuir o desperdício de tempo no ato de recuperar um dado requerido, a estratégia FIFO permite que a máquina armazene, em prontidão, os dados mais recentes por ordem de chegada. No caso de se ocupar todo o espaço do cache, serão sobrescritos os valores mais antigos. 

## Desempenho da estratégia _FIFO_

### Randomic Workload

#### Hit Time
  A partir da análise dos dados computados nesse projeto, um padrão se mostra claro: a _FIFO Strategy_ leva larga desvantagem frente as demais estratégias de cache abordadas nesse material quanto ao tempo médio para um 'Hit'. O gŕafico conjunto do 'Hit time' para a carga de dados randõmica assinala a ineficiência temporal da FIFO quanto à competência dos hits.

### Periodic Workload

### Spike Workload

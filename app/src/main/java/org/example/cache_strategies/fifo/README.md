# _FIFO Eviction Strategy_

## Definição
 Suponha que você vá a um banco e, para ser atendido, haja uma fila de espera. A convenção do que deve acontecer nessa situação é bem conhecida: quem está há mais tempo esperando será atendido primeiro, ou seja, respeita-se uma ordem de chegada. É essa lógica que uma estrutura de dados FIFO (First In, First Out (o primeiro a chegar é o primeiro a sair)) segue. No contexto do design e da produção de _caches_, a lógica FIFO se apresenta como boa solução para certos desafios apresentados e, dessa forma, tem papel relevante no mercado de caches.

## _FIFO_ como estratégia de _cache_
 Uma vez que o objetivo de um dispositivo _cache_ é diminuir o desperdício de tempo no ato de recuperar um dado requerido, a estratégia FIFO permite que a máquina armazene, em prontidão, os dados mais recentes por ordem de chegada. No caso de se ocupar todo o espaço do cache, serão sobrescritos os valores mais antigos. 

## Desempenho da estratégia _FIFO_

### Randomic Workload

#### Hit Time
 Pode-se tirar a seguinte conclusão dos gráficos de 'Hit time' da _FIFO Eviction Strategy_: Temos um tempo médio de hit menor do que o de um miss - o que era, a priori, esperado, já que para confirmarmos um hit nem sempre precisamos percorrer toda a estrutura e, no _Miss_, há a necessidade disso. Comparativamente as outras políticas testadas, a FIFO Strategy teve um desempenho mediocre no 'Hit Time' para a carga de dados randômicas. 

#### Miss Time
  A partir da análise dos dados computados nesse projeto, um padrão se mostra claro: a _FIFO Strategy_ leva larga desvantagem frente as demais estratégias de cache abordadas nesse material quanto ao tempo médio para um 'Miss'. O gŕafico conjunto do 'Miss time' para a carga de dados randõmica assinala a ineficiência temporal da FIFO quanto à competência dos *misses*.

### Periodic Workload

#### Hit Time

 
#### Miss Time

### Spike Workload

#### Hit Time

 Aqui, há um padrão denotado nos gráficos que chama atenção: especificamente para a carga de dados com picos, o 'Hit Time' da política "explodiu" - sofreu uma degeneração de desempenho muito relevante. Com isso, foi, com sobras, a estratégia de cache eviction mais lerda nesse ponto. Uma pista para esse comportamento específico pode ser o próprio funcionamento da FIFO. Uma vez que um novo valor é adicionado no final da estrutura, na hipótese desse valor passar a ser muito requisitado (se tornar "quente"), tem-se que percorrer, em cada nova solicitação deste dado, toda a fila para recuperá-lo. Isso faz com que, quando há um pico de algum valor na carga Spike, o custo dessa rotina pese muito na eficiência final da política.
 
#### Miis Time

# _FIFO Eviction Strategy_

## FIFO?
 Suponha que você vá a um banco e, para ser atendido, haja uma fila de espera. A convenção do que deve acontecer nessa situação é bem conhecida: quem está há mais tempo esperando será atendido primeiro, ou seja, respeita-se uma ordem de chegada. É essa lógica que uma estrutura de dados FIFO (First In, First Out (o primeiro a chegar é o primeiro a sair)) segue. No contexto do design e da produção de _caches_, a lógica FIFO se apresenta como boa solução para certos desafios apresentados e, dessa forma, tem papel relevante no mercado de memória rápida.

## _FIFO_ como estratégia de _cache_
 Uma vez que o objetivo de um dispositivo _cache_ é diminuir o desperdício de tempo no ato de recuperar um dado requerido, a estratégia FIFO permite que a máquina armazene, em prontidão, os dados mais recentes por ordem de chegada. Como será mostrado abaixo, esse princípio próprio da lógica FIFO terá pontos fortes e pontos críticos, que se sobressaem conforme o uso escolhido para o cache.

## Implementação da _FIFO Strategy_ neste projeto
 Para o estudo da _FIFO Eviction Strategy_, foi utilizada, nesse material, a implementação de um cache baseado em uma fila circular (ou seja, quando a estrutura atinge sua capacidade máxima, a solicitação de armazenamento de novos dados implica na sobrescrição dos valores mais antigos). A circularidade dessa implementação pode ser explicitada com base no método addLast (função comum a implementações de filas):
```Java
public void addLast(T valor) {
        if(isFull()) {
            removeFirst();
        }
        if(this.size == 0) {
            this.head++;
        }
        this.tail = (this.tail + 1) % this.fila.length;
        this.fila[this.tail] = valor;
        this.size++;
    }
}
```
 Agora, a exemplificação em código de que, quando a fila está cheia, "alguém" deve dar espaço para o novo elemento, e a escolha é pela remoção do valor mais antigo

 Dessa forma, uma vez que esta implementação de memória rápida está baseada na estrutura de dados citada, será comum à ela o comportamento assintótico de uma fila. Isso quer dizer que, no caso da busca de um elemento, por exemplo, a complexidade é O(n), ou seja, deve-se percorrer até n elementos de uma fila de tamanho 'n' para finalizar o método de _search_. É interessante ter conhecimento do custo do método _search_ em específico, uma vez que a confirmação de um hit ou de um miss passa por uma busca do valor na estrutura (ou seja, esse custo será determinante para o desempenho da política FIFO).

 ```Java
 public int indexOf(T value) {
        int count = 0;
        while(count < this.size) {
            if(this.fila[(this.head + count) % this.fila.length].equals(value)) {
                return (this.head + count) % this.fila.length;
            } else {
                count++;
            }
        }
        return -1;
    }
```

Rotina de busca de um elemento materializada a partir do método indexOf que, em poucas palavras, retorna o índice do elemnento caso ele tenha sido encontrado. Implica-se então que, para um elemento não encontrado, o método retorna uma sinalização de _miss_ (-1).

## Desempenho 

### Randomic Workload

#### Hit Time
 Pode-se tirar a seguinte conclusão dos gráficos de 'Hit time' da _FIFO Eviction Strategy_: Temos um tempo médio de hit menor do que o de um miss - o que era, a priori, esperado, já que para confirmarmos um hit nem sempre precisamos percorrer toda a estrutura e, no _Miss_, há a necessidade disso. Comparativamente as outras políticas testadas, a FIFO Strategy teve um desempenho mediocre no 'Hit Time' para a carga de dados randômicas, como pode ser observado no gráfico abaixo.

 ![Tempo médio de hit da FIFO para workload randômico](../../../../../../../data/graphs/hit_time_graphs/general_randomic_time_graph.png)

 Custo de tempo da FIFO está acima de três políticas e, no entanto, bem abaixo do custo da _LFU Strategy_.

#### Miss Time
  A partir da análise dos dados computados nesse projeto, um padrão se mostra claro: a _FIFO Strategy_ leva larga desvantagem frente as demais estratégias de cache abordadas nesse material quanto ao tempo médio para um 'Miss'. O gŕafico conjunto do 'Miss time' para a carga de dados randõmica assinala a ineficiência temporal da FIFO quanto à competência dos *misses*. E, bom, isso têm fundamentação algorítmica: foi dito, neste documento, acima, que a implementação do cache acompanharia os comportamentos assintóticos de uma fila circular e, como o método _search_ necessário para confirmar um 'miss' é O(n), temos um grande custo constante na rotina de um _miss_.

  ![Tempo médio de miss da FIFO para workload randômico](../../../../../../../data/graphs/miss_time_graphs/general_randomic_time_graph.png)

  Aqui, constata-se o grande ponto fraco desta política enquanto solução para caches: o alto custo da rotina de busca. Perceba que, enquanto três das outras quatro _cache strategies_ abordadas neste material têm eficiência praticamente O(1), _FIFO Strategy_ assume uma grande desvantagem e se mostra a pior implementação neste prisma.

### Periodic Workload

#### Hit Time

 
#### Miss Time

### Spike Workload

#### Hit Time

 Aqui, há um padrão denotado nos gráficos que chama atenção: especificamente para a carga de dados com picos, o 'Hit Time' da política "explodiu" - sofreu uma degeneração de desempenho muito relevante. Com isso, foi, com sobras, a estratégia de cache eviction mais lerda nesse ponto. Uma pista para esse comportamento específico pode ser o próprio funcionamento da FIFO. Uma vez que um novo valor é adicionado no final da estrutura, na hipótese desse valor passar a ser muito requisitado (se tornar "quente"), tem-se que percorrer, em cada nova solicitação deste dado, toda a fila para recuperá-lo. Isso faz com que, quando há um pico de algum valor na carga Spike, o custo dessa rotina pese muito na eficiência final da política.

   ![Degeneração no desempenho para Hit com carga Spike](../../../../../../../data/graphs/hit_time_graphs/general_spike_time_graph.png)

  Contraste de desempenho entre a FIFO e as demais políticas para esse tipo de problema. Nota-se o custo que a implementação paga por suas características próprias de design.
 
#### Miis Time


## Conclusão
 A maior valência de um cache que segue a lógica 'First In First Out' é a simplicidade de sua implementação, o que resulta em um equipamento físico - hardware - acessível e de engenharia proporcionalmente simples. Nesse sentido, a FIFO Strategy se vê útil e sobressalente para problemas que demandem previsibilidade e praticidade e, não necessariamente, a taxa de 'hits' mais alta. Uma estratégia com essas qualidades implica em menos transistores dedicados, por exemplo, além de menor área ocupada no chip e provável menor gasto de energia. 

 ## Bônus: _FIFO Cache Strategy_ implementada no dia a dia
  Em *sistemas embarcados* (de forma simples, subcomputadores que têm a finalidade de executar tarefas específicas dentro de outro dispositivo, em virtude do caráter auxiliar do sistema e da necessidade de otimizar, ao máximo, custos de produção para que o produto seja viável industrialmente, uma das melhores soluções disponíveis a baixo custo é a implementação de uma memória rápida que utiliza FIFO para tomada de decisão de armazenamento/adição/exclusão de dados. Uma materialização dessa aplicação seria um microcontrolador, dentro de um roteador móvel doméstico: a memória do modem é limitada e, portanto, faz-se necessário escolher, quando há muitos pacotes de dados originados da internet, quais pacotes manter e enviar, de fato, ao computador. É aí que a lógica FIFO entra em cena, resolvendo de forma simples e _barata_.

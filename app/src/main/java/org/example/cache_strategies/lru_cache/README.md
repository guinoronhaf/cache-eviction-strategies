# _LRU Eviction Strategy_

 - [Definição](#definição)
 - [Aplicação da LRU](#aplicação-da-lru)
 - [Desempenho da estratégia de cache](#desempenho-da-estratégia-de-cache)
 - [_Periodic Workload_](#periodic-workload)
 - [_Randomic Workload_](#randomic-workload)
 - [_Spike Workload_](#spike-workload)
 - [Conclusão](#conclusão)
 - [Autor](#autor)
 
## Definição
A política de cache _LRU (Least Recently Used)_ é uma estratégia de substituição de _cache_ baseada no princípio da localidade temporal, onde dados acessados recentemente possuem maior probabilidade de serem reutilizados em um futuro próximo.

Como o nome sugere, quando a memória _cache_ está cheia, a _LRU_ escolhe os dados usados menos recentemenete e os remove para liberar espaço para os novos dados. A prioridade dos dados no cache muda de acordo com a necessidade desses dados, ou seja, se alguns dados forem buscados ou atualizados recentemenete, a prioridade desses dados será alterada e atribuída à prioridade mais alta, e a prioridade dos dados diminui se permanecerem sem operações de uso após um período.

### Características FUndamentais:

 - **Criterio de Substituição:** Remove sempre o elemento que permaneceu não acessado pelo maior período de tempo.

 - **Mecanismo de Decisão:** Mantém uma ordenação implícita ou explícita dos intens conforme sua recência de acesso.

## Aplicação da LRU

Para fornecer um exemplo de aplicação real da política, imaginaremos o cenário de serviços de streaming(Netflix/YouTube) onde um servidor precisa entregar os vídeos mais populares para milhões de usuários simultaneamente, mas a memória cache do servidor é limitada.

Imagine que o cache de um servidor de streaming é como o **balcão de um barista muito eficiente.** O armazenamento principal é o estoque nos fundos, e os clientes são os usuários pedindo vídeos.

 - **Problema:** O balcão(cache) é pequeno. Não dá para deixar todos os ingredientes à mão.

 - **A Solução(LRU):** O barista (algoritmo LRU) não organiza o balcão aleatoriamente. Ele mantém à mão os ingredientes para as bebidas mais pedidas _recentemente._

 - **Na Prática:**
   
   - Quando um novo café vira moda, o barista rapidamente coloca os ingredientes dele no valcão, tirando um xarope menos popular que não é pedido desde domingo.

   - Se um cliente pede um cappuccino, que já é popular, o barista usa o leite que já estava à mão e o mantém em local de fácil acesso.

 - **O Resultado:** Os clientes recebem as bebidas mais populares quase instantaneamente. O barista raramente precisa ir ao estoque(redução no tráfego para o armazenamento principal), tornando todo o café mais rápido e eficiente. Durante um evento inesperado, como uma promoção relâmpago, esse sistema evita o colapso da cafeteria.

# Implementação da estratégia de _cache_

Agora, ciente do comportamento esperado da LRU e dos tipos de problemas que ela resolve, chegou a hora de discutir sua implementação. Vale ressaltar que a implementação discutida aqui não é única, tampouco regra, há outras implementações para o algoritmo, porém, iremos abordar a que utiliza de _HashMap_ e _Lista Duplamente Encadeada_, que é dentre as implementações a mais eficiente.

Sabendo dos recursos que são utilizados na implementação, vale ressaltar também que a classe do _LRUCache_, classe do algoritmo, faz uso de uma classe auxiliar _LRUNode_, classe essa que garante a estrutura da _Lista Duplamente Encadeada_, onde cada _LRUNode_ guarda o valor do nó propriamente dito e referências para o próximo e para o anterior. A classe principal do algoritmo mantém `LRUNode head` apontando para o "primeiro" item da fila e `LRUNode tail` apontando para o "último", são utilizados para manter a ordem de recência dos elementos.

Eis aqui a implementação do _LRUNode:_

```java
    public class LRUNode<V> {
            
            V value;
            LRUNode<V> next;
            LRUNode<V> prev;

            pubilc LRUNode(V value) {
                this.value = value;         
            }

        }
```

 - **OBS:** Vale ressaltar que _V_ representa um valor _Genérics._

Como foi dito no início, o _LRU_ remove sempre o menos recentemente utilizado. Pensando nessa política, implementamos o algoritmo que funciona da seguinte maneira: quando acessamos um elemento específico, é verificado se ele consta no _cache._ Caso o elemento esteja no cache, ele é movido para o final(vale ressaltar que a escolha de colocar o mais recentemente acessado no início ou no fim fica a critério de quem esta implementando, aqui o mais recente ficará no final). Caso ele não faça parte e haja espaço, simplesmente adicionamos ele no final. Por outro lado, se ele não faz parte e o cache esta cheio, então é removido o elemento menos acessado, no caso o elemento apontado por _LRUNode head_, e depois o novo elemento é adicionado no final.

Bem simples, né? Aqui esta a implementação em _java_ dos principais métodos responsáveis por fazer essa engrenagem rodar:

```java
    
    //consulta um objeto no cache
    public V get(V key) {

        if(!cache.containsKey(key)) {
            return null;
        }

        LRUNode<V> node = cache.get(key);
        moveToTail(node);
        return node.value;
    }

    //Adiciona um elemento no final preservando a ordem de recência 
    public void addLast(V value) {

        if(isFull()) removeFirst();
        LRUNode<V> newNode = new LRUNode<>(value);

        if(isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.next = newNode;
            newNode.prev = this.tail;
            this.tail = newNode;
        }
        cache.put(newNode.value, newNode);
        this.size++;

    }

    //Remove o elemento do inicio
    public LRUNode<V> removeFirst() {

        if(isEmpty()) return null;

        LRUNode<V> toRemove = this.head;

        if(size() == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head.next.prev = null;
            this.head = this.head.next;
        }
        this.cache.remove(toRemove.value);
        this.size--;
        return toRemove;

    }

    //move um elemento para o final
    public void moveToTail(LRUNode<V> node) {

        if(node == this.tail) return;

        if(node == this.head) {
            this.head = node.next;
            this.head.prev = null;
        } else {
            if(node.next != null) node.next.prev = node.prev;
            if(node.prev != null) node.prev.next = node.next;
        }

        node.prev = this.tail;
        node.next = null;
        this.tail.next = node;
        this.tail = node;

    }

}
```

### Complexidade e eficiência

Agora que sabemos como a política de cache _LRU_ funciona por "dentro", iremos discutir o porque da escolha de um _HashMap_ e uma _Lista Duplamente encadeada_ na implementação e não outras estruturas de dados quaisquer.

Como abordado em _"O que é cache?"_, o _cache_ é um tipo de memória limitada e de acesso rápido onde ficam elementos que devem ser buscados com alta rapidez. As estruturas de dados foram escolhidas visando esse conceito, já que utilizamos o _HashMap_ para guardar os elementos do _cache_ nos permitindo acesso, remoção e adição tudo em O(1), além de mapear com maior eficiência cada chave(key) ao seu valor. E a _Lista Duplamente Encadeada_ nos permite manter os elementos em ordem do mais recentemente utilizado ao menos recentemente utilizado e também permite remover e adicionar em O(1). Logo, conseguimos operar sempre em O(1) o que torna o algoritmo super eficiente, a eficiência será melhor demonstrada nos tópicos a seguir.

## Desempenho da estratégia de cache

### Hit Time
Analisando os gráficos sobre os tempos de hit das políticas é evidente que a _LRU_ possui uma alta eficiência independente do cenário. Porém, é perceptível também, seja alinalisando individualmente ou em conjunto, que a _LRU_ possui um tempo de _hit_ alto no começo e isso tem explicação.

![Gráfico do _hit time periodico_ de todas as políticas](../../../../../../../data/graphs/hit_time_graphs/general_periodic_time_graph.png)

![Gráfico do _hit time randomico_ de todas as políticas](../../../../../../../data/graphs/hit_time_graphs/general_randomic_time_graph.png)

![Gráfico do _hit time de picos(spike)_ de todas as políticas](../../../../../../../data/graphs/hit_time_graphs/general_spike_time_graph.png)

Isso é um fenômeno comum e se deve tanto à sua implementação quanto à fase de "Warm-up"(fase de aquecimento do cache). No início da execução de um programa, a cache não tem nada para fornecer, resultando em um alto número de cache misses. Em um cenário real, isso significa que é necessário buscar os dados na memória principal, que é muito mais lenta. Isso é o Warm-up. 

E sobre sua implementação ter uma contribuição nesse fato, isso corre porque na sua implementação é utilizada uma _Lista Duplamente Encadeada._ Logo, diferente de um array que reserva espaços contíguos na memória e faz com que o acesso aos elementos seja o mais eficiente possível, em uma _Lista Duplamente Encadeada_ os elementos não são alocados de forma contígua, ou seja, eles são "espalhados" pela memória, o que faz com que o tempo de acesso tenha um maior custo inicial. Isso além da sobrecarga do gerenciamento de ponteiros, já que cada hit envolve acessar o HashMap, remover o nó e reposicionar no final, isso e o fato do HashMap ter que lidar com o crescimento de buckets(rehash).

Além disso também disso também abordaremos nos tópicos de cada _workload_ porque alguns tem um custo inicial mais caro que outros.

Resumindo, o hit time alto no início não se deve ao algoritmo _LRU_ em si(ja que suas ações são O(1)), mas sim a efeitos práticos de implementação. Depois que a estrutura e o ambiente "estabilizam", o hit time tende a ficar constante e baixo.

### Miss Time

Quanto ao miss time, também observamos um custo maior no inicio devido às mesmas causas abordadas no tópico de _hit time_, questões de implementação além dos misses compulsivos. Porém observamos também que as taxas de miss são diferentes a depender do cenário ao qual submetemos a política. Se formos ranquear os cenário a depender do desempenho temos o cenário de picos em primeiro lugar(spike), seguido do cenário aleatório(randomic) e por último o periódico(periodic). Cada _workload_ em particular tem uma explicação.

![Gráfico do _miss time periódico_ de todas as políticas](../../../../../../../data/graphs/miss_time_graphs/general_periodic_time_graph.png)

![Gráfico do _miss time randomico_ de todas as políticas](../../../../../../../data/graphs/miss_time_graphs/general_randomic_time_graph.png)

![Gráfico do _miss time de picos(spike)_ de todas as políticas](../../../../../../../data/graphs/miss_time_graphs/general_spike_time_graph.png)

### _Periodic Workload_

No cenário em que fornecemos uma carga periódica de trabalho ao _LRU_ temos um problema: se o periódo de repetição for maior que a capacidade do _cache_, quando você volta a acessar os primeiros elementos, eles já foram descartados. Ou até mesmo que a repetição não seja superior à capacidade, mas sendo igual ou muito próximo, o mesmo comportamento acontece. Temos muitos misses recorrentes porque o _cache_ não "aprende" nada que consiga reaproveitar a longo prazo. Isso é observado no inicio do gráfico onde a capacidade do _cache_ é menor. Conforme o tamanho do cache aumenta, percebe-se esse problema se diluindo, já que o tamanho do cache já é grande o suficiente para "aprender" algo reaproveitável a longo prazo. Por isso o cenário em que submetemos o algoritmo a uma carga de trabalho periodica se mostra inferior aos demais.

![Gráfico do _hit time_ periodico](../../../../../../../data/graphs/hit_time_graphs/periodic/lru_time_graph.png)

![Gráfico do _miss time_ periodico](../../../../../../../data/graphs/miss_time_graphs/periodic/lru_time_graph.png)

### _Randomic Workload_

Ao submetermos o algoritmo a uma carga de trabalho aleatória, observamos que ela é superior à carga de trabalho periódica. Isso acontece porque, como os acessos são uniformemente aleatórios, a chance de _hit_ depende só do tamanho relativo do cache ao universo de elementos. Isso significa que não há padrão forte para o _LRU_ explorar, mas também não há repetição que cause expulsão sistemática como no periodico. Portanto, o _miss time_ tende a se estabilizar em um valor intermediário. Isso mostra o porquê de ter misses supeirores ao cenário periódico e mostraremos no próximo tópico o porquê de ter misses inferiores ao cenário de pico.

![Gráfico do _hit time_ randomico](../../../../../../../data/graphs/hit_time_graphs/randomic/lru_time_graph.png)

![Gráfico do _miss time_ randomico](../../../../../../../data/graphs/miss_time_graphs/randomic/lru_time_graph.png)

### _Spike Workload_

Por fim, o cenário em que a política se mostra mais eficiente: o cenário em que ocorre um pico de acessos a um determinado elemento. Ele se mostra melhor em relação aos outros dois cenários pois, quando chega o pico(acessos repetidos a um mesmo conjunto pequeno de elementos), o _LRU_ se beneficia muito. Os elementos do _spike_ ficam sempre no final(ou no início a depender da implementação do algoritmo, no nosso caso ele fica no final) e acontecem praticamente zero misses durante o pico. Logo, passado aquele primeiro de momento misses compulsivos que é enfrentado em todos os cenários, o _miss time_ cai muito no final, porque a carga de trabalho se torna altamente previsível e o cache "aprendeu" rápido. Ou seja, o _miss time_ cai pois no pico ele ja contém o elemento, ou passa a conter, e dai em diante são utilizados apénas _hits_, já que esta sendo acesso um elemento já contido. Por isso o _spike_ tem o menor _miss time_ final.

![Gráfico do _hit time_ de picos(spike)](../../../../../../../data/graphs/hit_time_graphs/spike/lru_time_graph.png)

![Gráfico do _miss time_ de picos(spike)](../../../../../../../data/graphs/miss_time_graphs/spike/lru_time_graph.png)

## Conclusão
A avaliação experimental da política _LRU_ revelou padrões distintos de comportamento conforme a natureza da carga de trabalho:

#### Cenário Periódico:
 
 - Demonstrou **adaptabilidade cíclica**, com recuperação rápida após transições
 - **Vantagem:** Excelente aproveitamento da localidade temporal dentro de cada fase
 - **Limitação:** Sofre penalidade temporária nas mudanças de contexto, com quedas abruptas no _hit ratio_

#### Cenário Randômico:

 - **Desempenho mediano,** sem vantagens significativas sobre políticas mais simples
 - **Constatação:** A ausência de padrões temporais anula a principal vantagem do LRU
 - **Recomendação:** Neste cenário, políticas de menor _overhead_ são mais adequadas

#### Cenário de Picos(spike):

 - **Comportamento superior** na captura e manutenção de acessos concentrados
 - **Eficiência:** Rapidamente identiica e retém dados relevantes durante picos de demanda
 - **Resiliência:** Mantém desempenho estável durante fenômenos cíclicos ou eventos sazonais

### Conclusões Finais

A política _LRU_ **mostrou-se ideal para ambientes com padrões de acesso temporalmente coerentes,** onde sua capacidade de identificar e priorizar dados recentemente utilizados resulta em ganhos significativos de performance. No entanto, **sua eficácia é diretamente dependente da existência de localidade temporal** na carga de trabalho.

A principal **vantagem competitiva** do _LRU_ reside em sua capacidade de **auto-otimização contínua** sem necessidade de reconfiguração ou intervenção externa, adaptando-se dinamicamente às mudanças nos padrões de acesso.

**_Recomendação de Aplicação:_** A política _LRU_ é particularmente recomendada para sistemas onde os dados possuem ciclo de vida bem definido e a recência de acesso é um forte indicador de utilidade futura, como em aplicações web, sistemas de recomendação e serviços de conteúdo sob demanda.

## Autor

Brunno Weslley Borges Ribeiro (Github: @brunnowxl)

Este arquivo faz parte do projeto final da Disciplina de Estruturas de Dados e Algoritmos do Curso de Ciência da Computação da Universidadde Federal de Campina Grande durante o período 2025.1

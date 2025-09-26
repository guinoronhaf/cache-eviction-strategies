## _LFU Eviction Strategy_

  - [Definição](#definição)
  - [_LFU como estratégia de cache_](#LFU-como-Estratégia-de-Cache)
  - [Implementação da estratégia de _cache_](#implementação-da-estratégia-de-cache)
  - [Complexidade e eficiência](#complexidade-e-eficiência)
  - [Desempenho da estratégia de _cache_](#desempenho-da-estratégia-de-cache)
  - [_Randomic workload_](#randomic-workload)
  - [_Periodic workload_](#periodic-workload)
  - [_Spike workload_](#spike-workload)
  - [Conclusão](#conclusão)
  - [Autor](#autor)

## Definição
No campo de sistemas de gerenciamento de memória e cache, várias estratégias são usadas para decidir quais dados devem ser mantidos na memória de acesso rápido (cache) e quais devem ser removidos para liberar espaço. A implementação da política **LFU (Least Frequently Used)** se baseia no princípio de que o item acessado com menor frequência é o que tem menor probabilidade de ser acessado no futuro. Por isso, ele se torna o candidato ideal para remoção do cache quando um novo item precisa ser adicionado.A essência do LFU reside na contagem de acessos. Cada item no cache tem um contador que é incrementado sempre que o item é acessado. Quando o cache atinge sua capacidade máxima e um novo item precisa ser inserido, o algoritmo varre todos os itens e remove aquele com a contagem de acessos mais baixa. Em caso de empate, um critério secundário (como a ordem de inserção) pode ser usado para desempate.

## _LFU_ como Estratégia de _Cache_
A política LFU é extremamente eficaz em cenários onde a frequência de acesso a certos dados é um bom indicativo de sua importância futura. Ao contrário de políticas como o FIFO (First-In, First-Out), que ignora completamente a recência e frequência de uso, ou o LRU (Least Recently Used), que foca apenas na recência, a implementação fornecida considera o histórico de acessos de um item. Isso a torna particularmente útil para caches de banco de dados, proxies e sistemas de arquivos, onde dados populares tendem a ser acessados repetidamente.A eficiência da política de cache pode ser avaliada pela sua taxa de acertos e pelo tempo de resposta durante um cache miss. Uma alta taxa de acertos indica que a maioria dos dados solicitados já estava no cache, minimizando a necessidade de buscas em memórias mais lentas. O tempo de um cache miss, por sua vez, é crucial para avaliar a eficiência do algoritmo de substituição. Na implementação do LFU, esse tempo inclui a busca pelo item menos frequente e sua remoção.

## Implementação da Estratégia de _Cache_
Para implementar o LFU de forma eficiente,nessa implementação, é utilizado uma combinação de estruturas de dados. É usado dois mapas principais:
- Um HashMap (freqMap) para associar cada item à sua frequência de uso. Isso permite recuperar e atualizar a frequência de um item rapidamente.
  
- Outro HashMap (freqListMap) onde a chave é a frequência de acesso e o valor é uma LinkedList que agrupa todos os itens com a mesma frequência.

```Java
public class LfuCache<T> {

    private final static int CAPACITY_DEFAULT = 10;
    private final int capacity;
    private int minFrequency;
    private final Map<T, Integer> freqMap;
    private final Map<Integer, LinkedList<T>> freqListMap;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.freqMap = new HashMap<>();
        this.freqListMap = new HashMap<>();
  } 
 ``` 
Essa arquitetura permite uma gestão muito eficiente dos itens. Quando um item é acessado (get), sua frequência é atualizada no freqMap. Em seguida, ele é removido da LinkedList de sua frequência anterior e adicionado à frente da LinkedList de sua nova frequência (incrementada).Quando um novo item é inserido (put) e o cache já está cheio, a busca pelo item a ser removido (getNextEviction) se torna muito eficiente. O cache mantém uma variável minFrequency que aponta para a frequência mais baixa de todos os itens no cache. O item a ser removido é o último elemento da LinkedList correspondente a essa minFrequency.

## Implicações dessa estratégia de LFU
A principal vantagem dessa implementação do LFU é sua capacidade de lidar com a complexidade do algoritmo de forma otimizada. Ao usar dois mapas e LinkedLists, ela evita a necessidade de percorrer todo o cache para encontrar o item menos frequente. Em vez disso, a variável minFrequency fornece um atalho para a lista de itens "candidatos" à remoção, e a LinkedList permite remover o item menos recentemente usado dentro daquela frequência em tempo constante (removeLast).Isso resolve um problema comum em implementações mais simples do LFU, onde a busca pelo item a ser removido pode ser demorada. No entanto, é importante notar que o LFU pode ter desvantagens. Por exemplo, um item que foi acessado muitas vezes no passado, mas que não é mais necessário, pode permanecer no cache por muito tempo, bloqueando o espaço de itens mais "quentes" e recém-adicionados.

## Complexidade e Eficiência
- O método **get(item)** tem complexidade de tempo **O(1)** na maioria dos casos. A busca, atualização de frequência e manipulação das listas ligadas são operações de tempo constante graças ao uso de mapas e listas.

```Java
    public T get(T item) {

        if (!this.freqMap.containsKey(item)) {
            return null;
        }

        int currentFreq = this.freqMap.get(item);
        LinkedList<T> currentList = this.freqListMap.get(currentFreq);
        currentList.remove(item);

        if (currentList.isEmpty() && currentFreq == minFrequency) {
            minFrequency++;
        }

        int newFreq = currentFreq + 1;
        this.freqMap.put(item, newFreq);
        this.freqListMap.putIfAbsent(newFreq, new LinkedList<>());
        this.freqListMap.get(newFreq).addFirst(item);

        return item;
    }
```
  
- O método **put(item)** também tem complexidade de tempo **O(1)** na maioria das vezes. A verificação de capacidade, a busca pelo item a ser removido (que já está na lista da minFrequency) e a inserção do novo item são operações de tempo constante.

```Java
    public void put(T item) {

        if (capacity == 0) {
            return;
        }

        if (this.freqMap.containsKey(item)) {
            get(item);
            return;
        }

        if (this.freqMap.size() >= capacity) {
            LinkedList<T> minFreqList = this.freqListMap.get(minFrequency);
            T itemToRemove = minFreqList.removeLast();
            this.freqMap.remove(itemToRemove);
        }

        this.freqMap.put(item, 1);
        this.freqListMap.putIfAbsent(1, new LinkedList<>());
        this.freqListMap.get(1).addFirst(item);
        minFrequency = 1;
        
    }
```  
  
- O método **getNextEviction()** também é eficiente, pois o item a ser removido está sempre no final da LinkedList associada à minFrequency, tornando a operação **O(1)**.

```Java
    public T getNextEviction(){

        if (this.freqMap.isEmpty()) {
            return null; 
        }
        LinkedList<T> minFrenquencyList = this.freqListMap.get(minFrequency);
        if (minFrenquencyList != null && !minFrenquencyList.isEmpty()) {
            return minFrenquencyList.getLast();
        }
        return null;

    }
```  

## Desempenho da estratégia de cache:
### Periodic Workload
#### Quantidade de hits:
![gráfico_da_quantidade_de_hits_ para _workloads_ periódicos](../../../../../../../data/graphs/hit_graphs/periodic/lfu_hit_graph.png)

O gráfico mostra a quantidade de hits acumulados à medida que o workload aumenta. Observa-se uma tendência linear de crescimento: quanto maior o número de requisições processadas, maior o número absoluto de acertos. Essa linearidade é um indicativo de que o LFU está conseguindo capturar a periodicidade dos acessos, mantendo em cache os itens que retornam ciclicamente ao longo da execução.Isso valida a adequação teórica da política ao cenário em questão: como o workload é periódico, os itens "quentes" reaparecem com frequência previsível e o LFU tende a preservá-los, reduzindo misses desnecessários. A operação de get no cache LFU tem uma complexidade de tempo de O(1) em média. Isso significa que, independentemente do tamanho do workload , a busca por um item no cache leva um tempo constante. A quantidade de hits aumenta linearmente porque cada requisição adicional tem uma chance constante de ser um hit, já que o tempo de busca não se degrada. A eficiência da operação de get com tempo constante (O(1)) é a base para o crescimento linear e previsível do número de hits.

Abaixo um gráfico comparando todas as políticas presentes nesse projeto
![gráfico_de_número_de_hits_ para _workloads_ periódicos_todas_as_políticas](../../../../../../../data/graphs/hit_graphs/general_periodic_hit_graph.png)

Nos estágios iniciais, a LRU (Least Recently Used) se destaca com um pico de acertos muito alto, mostrando sua eficácia imediata em lidar com itens que acabaram de ser usados. No entanto, a LFU demonstra um comportamento mais estratégico: ela começa devagar porque precisa de tempo para "aprender" a frequência de uso dos itens e após isso consegue realizar suas operações de forma bem mais eficiênte. A LFU  exibe um crescimento estável e contínuo. Em contraste, as políticas FIFO, Second Chance e Random Replacement ficam consistentemente abaixo no gráfico, devemos destacar que após uma certa carga de dados a quantidade de hits da LRU fica parelho com a dessas outras políticas, provando serem as menos eficientes nesse contexto do que a LFU.



#### Tempo médio  de miss:
![gráfico_da_média_de_misses_ para _workloads_ periódicos](../../../../../../../data/graphs/miss_time_graphs/periodic/lfu_time_graph.png)

Nota-se que o custo permanece praticamente estável, variando em uma faixa estreita (350ns a 150ns ), mesmo com o crescimento do workload.Isso significa que o processo de inserção e substituição no cache não degrada em função do tamanho da carga de trabalho. Ou seja, a operação de escolher o item a ser removido, baseada na frequência mínima, permanece eficiente. Esse comportamento está alinhado ao objetivo do LFU, já que as estruturas de suporte (mapas de frequência e listas de elementos) mantêm acesso direto ao conjunto de candidatos à remoção, portanto, os misses não representam gargalos de desempenho nesta implementação. A operação de put, que é executada em um miss, tem uma complexidade de tempo de O(1) em média. Quando o cache atinge sua capacidade máxima, a remoção do item menos frequente (para abrir espaço para o novo) é uma operação de tempo constante. A implementação encontra este item de forma eficiente, pois ele é sempre o último elemento da LinkedList associada à frequência mínima. Como todas as etapas do put (verificação, remoção, e inserção) são de tempo constante, o tempo médio para um miss não aumenta com o tamanho do workload, confirmando a complexidade assintótica de O(1).

Abaixo um gráfico que compara todas as politicas
![gráfico_de_média_de_tempo_de_misses_ para _workloads_ periódicos_todas_as_políticas](../../../../../../../data/graphs/miss_time_graphs/general_periodic_time_graph.png)

O gráfico revela que a velocidade de processamento de uma falta de cache (Miss) é a maior distinção entre as políticas.
A LFU pertence ao grupo das implementações mais rápidas, junto com LRU e Second Chance. O custo de tempo da LFU é mínimo e estável (próximo de 0 ns) ao longo de toda a carga de trabalho.
Em total contraste, as políticas FIFO e Random Replacement são drasticamente mais lentas. O FIFO é o pior, com um custo de Miss que é cerca de 30 a 40 vezes maior que o da LFU, sugerindo que o processo de substituição nessas implementações é altamente ineficiente em tempo.
Em suma, a LFU não só é a mais eficaz em acertos a longo prazo, mas também é uma das mais rápidas no processamento do erro, não adicionando sobrecarga significativa ao sistema.



#### Gráfico de tempo médio de Hits:
![gráfico_da_media_de tempo_de_hits_para_workLoad_periódico](../../../../../../../data/graphs/hit_time_graphs/periodic/lfu_time_graph.png)

O tempo médio de hits diminui com o aumento do workload e se mantém constante. Inicialmente, o custo médio por acesso é de cerca de 20.000ns, mas esse valor cai de forma acentuada para menos de 10.000ns quando o número de requisições aumenta .conforme o workload cresce, o cache se torna mais inteligente e eficiente em lidar com os acessos.Em um cenário inicial, com poucas requisições, o cache está em uma fase de "aprendizagem"; os itens populares ainda não foram acessados o suficiente para alcançar suas posições de maior frequência nas estruturas de dados.No entanto, com a repetição dos padrões de acesso, a lógica do LFU entra em plena ação. Os itens que são acessados com mais frequência têm seus contadores atualizados e são promovidos para as listas ligadas de maior frequência. Essa estabilização faz com que os elementos mais populares se concentrem nas estruturas de dados que representam as frequências mais altas. A  implementação é inteligente porque, em um hit, ela precisa remover o item da LinkedList de sua frequência antiga e adicioná-lo à nova lista, como os itens populares tendem a estar no início da lista (pela lógica de adição addFirst), a remoção se torna mais rápida na prática, mesmo que teoricamente seja O(n). A operação de get tem uma complexidade teórica de tempo de O(1) em média. A queda inicial no tempo médio de hit não contradiz essa complexidade, mas sim reflete o comportamento prático e a otimização das estruturas de dados. Nos estágios iniciais, o cache está se "aquecendo", e os itens mais acessados podem estar em posições menos favoráveis para remoção rápida. No entanto, com a repetição do workload periódico, a lógica do LFU promove os itens quentes, agrupando-os nas listas de maior frequência. A remoção de um item de uma LinkedList em Java é O(n) se a busca for linear, mas a implementação, ao usar o addFirst, faz com que os itens mais frequentemente acessados estejam no topo da lista. Isso significa que, na prática, a busca e remoção são próximas de O(1) para os itens que geram hits. O tempo se estabiliza quando a distribuição de itens nas listas de frequência atinge um estado ótimo para o workload periódico, mantendo a operação dentro da sua eficiência assintótica, porém em comparação com o tempo médio para o miss, o hit é mais demorado pois chega um momento que acontece uma sobrecarga de ter que atualizar a frequência e a posição dos itens na lista ligada.

Gráfico comparando os tempos médio de hit de  todas as políticas
![gráfico_de_média_de_tempo_de_hits_ para _workloads_ periódicos_todas_as_políticas](../../../../../../../data/graphs/hit_time_graphs/general_periodic_time_graph.png)

Apesar de ter uma lógica que faz com que ao decorrer da adição de dados a LFU fique mais eficiênte, nesse cenário de média, ela acaba tendo uma média de tempo pior do que as demais e boa parte do gráfico. Isso ocorre devido à sobrecarga de ter que atualizar a frequência e a posição do item nas listas ligadas.Em contraste, LRU e Second Chance são as políticas mais rápidas, com tempos de acerto próximos de zero. 

### Spike Workload:
#### Quantidade de hits:
![gráfico_de_quantidade_de_hits_em_workload_de_picos](../../../../../../../data/graphs/hit_graphs/spike/lfu_hit_graph.png)

O gráfico mostra um aumento consistente e quase linear no número total de acertos para o workload de pico. Esse comportamento demonstra que a política LFU está operando com eficácia, mesmo em um cenário onde a demanda por itens pode variar abruptamente. A progressão linear do crescimento sugere que a cache consegue identificar e reter os itens acessados com maior frequência, mesmo quando esses acessos ocorrem em picos. Para um workload de 1.000.000 de requisições, a cache LFU atingiu mais de 150.000 acertos, validando a capacidade da implementação de gerenciar padrões de acesso dinâmicos.A operação de get no cache LFU tem uma complexidade de tempo de O(1) em média. Isso significa que, independentemente do tamanho do workload , a busca por um item no cache leva um tempo constante. A quantidade de hits aumenta linearmente porque cada requisição adicional tem uma chance constante de ser um hit, já que o tempo de busca não se degrada. Isso prova que o desempenho da  implementação não se degrada com o aumento do volume de requisições, mesmo em um cenário de picos.

#### Tempo médio  de miss:
![gráfico_de_quantidade_media_de_misses_em_workload_de_picos](../../../../../../../data/graphs/miss_time_graphs/spike/lfu_time_graph.png)

O gráfico revela que o tempo médio para uma falta (miss) diminui significativamente à medida que o tamanho do workload aumenta. O custo inicial, que é mais alto, cai drasticamente. Essa queda sugere que, embora a operação de put (que ocorre após um miss) envolva a possível remoção de um item, o processo não se torna um gargalo de desempenho.O comportamento de queda e estabilização do tempo médio de miss reflete o "aquecimento" da estrutura de dados, que  com o tempo, a implementação identifica os itens mais populares e os move para posições ótimas dentro das LinkedList. Esse comportamento estável subsequente confirma a complexidade assintótica de O(1) da operação put.Essa eficiência é mantida porque essa  implementação tem acesso direto à lista de itens menos frequentes (minFrequency), o que permite a remoção rápida do candidato. Em um cenário de pico, essa eficiência é crucial, pois um grande número de novos itens pode ser inserido no cache em um curto período, e a LFU lida bem com esse volume.


#### Tempo médio de Hits:
![gráfico_de_quantidade_media_de_hits_em_workload_de_picos](../../../../../../../data/graphs/hit_time_graphs/spike/lfu_time_graph.png)

O gráfico mostra uma queda acentuada no tempo médio de acerto (hit) no início do workload, estabilizando-se em um valor baixo e consistente à medida que o número de requisições aumenta. Inicialmente, o tempo médio de acerto é de aproximadamente 450 ns, mas rapidamente cai para cerca de 300 ns ou menos. Essa curva de aprendizado inicial é esperada, pois o cache ainda está sendo "populado" com os itens mais populares. A operação de get têm uma complexidade teórica de tempo de O(1) em média. A queda acentuada no tempo médio de hit é um comportamento empírico que ilustra a eficiência prática da implementação, que vai além da simples análise assintótica.  

Conforme a cache se ajusta ao padrão de acesso do workload de pico, os itens que são mais solicitados são promovidos para as listas de maior frequência (addFirst),  tornando a operação de remove mais rápida na prática, mesmo que sua complexidade teórica seja O(n). Essa otimização prática, que se alinha com o comportamento de um Spike Workload, é o que permite que o tempo médio de hit se estabilize em um valor tão baixo, demonstrando como essa política é ideal para cenários onde a frequência de acesso a certos itens aumenta drasticamente. 

Abaixo gráfico que mostra a comparação do tempo médio de hits no workLoad de picos
![gráfico_de_media_de_tempo_de_hit_em_workload_de_picos](../../../../../../../data/graphs/hit_time_graphs/general_spike_time_graph.png)

A LFU é classificada como rápida, com seu tempo de acerto se estabilizando em um valor muito baixo (próximo de zero). No entanto, ela é consistentemente mais lenta que as políticas LRU e Second Chance, que conseguem tempos de acerto quase nulos. Essa pequena diferença é o custo que o LFU paga para manter a sua complexa estrutura de dados atualizada a cada acerto (o custo de mover o item para uma frequência maior).
Por outro lado, o LFU se mostra drasticamente mais rápido que o FIFO, que registra o pior tempo de Hit. Em suma, a LFU alcança um ótimo equilíbrio: ela não impõe uma latência alta ao sistema (é muito rápida), ao mesmo tempo em que garante o máximo de acertos no longo prazo. Diferente do que acontece com a media de tempo de hits no workLoad periódico uma vez que nesse cenário de picos a o acesso concentrado em rajadas mantém a estrutura interna mais organizada e eficiênte, uma vez que o grande custo da LFU é manter seus dados organizados pela frequência.

### Randomic WorkLoad:
#### Quantidade de hits:
![gráfico_de_quantidade_de_hits_em_workload_randomico](../../../../../../../data/graphs/hit_graphs/randomic/lfu_hit_graph.png)

O gráfico mostra o número total de acertos à medida que o tamanho do workload aleatório aumenta. Observa-se que a quantidade de hits cresce de forma constante, atingindo cerca de 2000 acertos para um workload de 1.000.000 de requisições. Esse crescimento linear indica que a cache está conseguindo reter alguns itens que, por coincidência, são acessados mais de uma vez, apesar da natureza aleatória do workload. No entanto, o número absoluto de acertos é significativamente menor do que seria em um workload com um padrão de acesso mais repetitivo, o que é esperado para uma política LFU em um cenário puramente aleatório.

Gráfico que compara a quantidade de hits das políticas de cache
![gráfico_de_quantidade_de_hits_em_workload_randomico_para_todas_as_políticas](../../../../../../../data/graphs/hit_graphs/general_randomic_hit_graph.png)

Esse gráfico mostra que sub carga de trabalho randômica a LFU é eficaz, mas perde a grande vantagem que possuía nos cenários previsíveis. Isso ocorre porque a ausência de um padrão de acesso impede que a sofisticação da lógica LFU seja totalmente recompensada. Em um cenário randômico, contar a frequência de um item não oferece uma vantagem estatística sobre simplesmente escolher um item aleatoriamente.


#### Tempo médio  de miss:
![gráfico_de_quantidade_média_de_misses_em_workload_randomico](../../../../../../../data/graphs/miss_time_graphs/randomic/lfu_time_graph.png)

O gráfico demonstra que o tempo médio para uma falta (miss) diminui drasticamente à medida que o workload aumenta. O tempo médio de miss começa alto (acima de 350 ns), mas cai acentuadamente para cerca de 150 ns e se mantém estável. Isso significa que o processo de inserção e possível remoção de itens na cache não se degrada com o aumento do volume de requisições. A eficiência da implementação é mantida porque a lógica para escolher e remover o item menos frequente é rápida e não representa um gargalo de desempenho.

Gráfico que compara o tempo médio de miss de todas as politicas
![gráfico_de_quantidade_média_de_misses_em_workload_randomico_de_todas_as_políticas](../../../../../../../data/graphs/miss_time_graphs/general_randomic_time_graph.png)

A LFU  prova ser extremamente eficiente neste cenário. Seu tempo de Miss é mínimo e estável (praticamente zero) em toda a carga, colocando-a no grupo das políticas mais rápidas para lidar com uma falta de cache. Ela é drasticamente mais rápida que o FIFO e o Random Replacement.
Portanto, mesmo em um ambiente totalmente imprevisível, o LFU garante que o custo de tempo para corrigir um erro de cache é negligenciável

#### Tempo médio de hits:
![gráfico_de_quantidade_média_de_hits_em_workload_randomico](../../../../../../../data/graphs/hit_time_graphs/randomic/lfu_time_graph.png)

O gráfico mostra uma queda abrupta no tempo médio de acerto (hit) à medida que o workload cresce. Inicialmente, o tempo médio de acerto é muito alto, acima de 40.000 ns, mas cai rapidamente para valores abaixo de 20.000 ns e se estabiliza. Essa curva de "aprendizagem" reflete o momento inicial em que o cache está se enchendo e os itens "quentes" estão sendo promovidos para as estruturas de dados de maior frequência. Mesmo em um workload aleatório, os poucos itens que são acessados mais de uma vez se movem para as listas de maior frequência, tornando as buscas subsequentes mais rápidas na prática. A estabilização do tempo médio de hit comprova a robustez da implementação, garantindo um desempenho consistente mesmo sem um padrão de acesso favorável. 

Gráfico que compara o tempo médio de hits de todas as políticas
![gráfico_de_quantidade_média_de_hits_em_workload_randomico_de_todas_as_políticas](../../../../../../../data/graphs/hit_time_graphs/general_randomic_time_graph.png)

O tempo de acerto (Hit Time) da LFU se torna inaceitavelmente lento no cenário Randômico (estabilizando entre 16.000 ns e 20.000 ns). O gargalo reside na manipulação das listas ligadas (LinkedList) dentro do método get(item). A cada acerto, o algoritmo precisa remover o item de sua lista de frequência antiga. Como a implementação usa a função LinkedList.remove(Object o), o sistema é forçado a percorrer sequencialmente toda a lista para encontrar o item. Em listas longas, essa operação tem uma complexidade de tempo de O(n), o que a torna extremamente lenta. Essa sobrecarga é o que eleva o Hit Time da LFU a níveis tão altos, fazendo com que ela seja muitas vezes mais lenta que o FIFO e incomparavelmente mais lenta que as implementações mais otimizadas como LRU.

## Conclusão
Conclui-se, portanto, que a implementação otimizada da política **LFU (Least Frequently Used)** constitui uma estratégia de cache de alto desempenho, notável por sua capacidade de considerar o **histórico de frequência** de acesso de um item, o que a torna superior a políticas baseadas apenas em recência (LRU) ou ordem de chegada (FIFO) em diversos cenários. A arquitetura baseada em dois **HashMaps** (freqMap, freqListMap) e **LinkedLists**, mantendo o rastreamento da minFrequency, garante que as operações críticas de **busca (get)** e **substituição (put)** sejam resolvidas com uma complexidade de tempo **O(1)** na maioria dos casos, validando a alta **eficiência assintótica** da estrutura.

É válido destacar que, para **Workloads Periódicos** e de **Pico (Spike)**, a LFU se mostra excepcionalmente eficaz em termos de **assertividade**. Em cenários periódicos, ela valida sua adequação teórica ao capturar a recorrência dos acessos, resultando em um crescimento **linear de Hits (O(N))** e um **Tempo Médio de Miss** estável em **O(1)**. Em picos de acesso, a política demonstra uma "curva de aprendizado" rápida, onde os itens mais solicitados são promovidos nas estruturas de frequência, provando ser ideal para gerenciar padrões de demanda dinâmicos e atingindo um alto volume de acertos.
No entanto, a análise comparativa revela uma dicotomia crucial entre a eficiência da política e a latência de suas operações.

-Para o **Tempo Médio de Miss**, a LFU é consistentemente uma das políticas mais rápidas em todos os cenários, devido à sua remoção O(1) direta e eficiente.

-Para o **Tempo Médio de Hit**, a LFU, embora pareça rápida em seus gráficos isolados (convergindo para um tempo constante), é significativamente mais lenta do que as políticas LRU e Second Chance na maioria dos cenários (especialmente no Randomic e Períodico).

Este gargalo reside na manipulação das LinkedLists. A cada Hit, o método get precisa remover o item da sua lista antiga, e o uso de LinkedList.remove(Object o) resulta em uma **complexidade prática de O(n)**, onde n é o comprimento da lista de frequência. Essa sobrecarga de tempo para **atualizar a frequência e a posição do item** é o que eleva o Tempo Médio de Hit da LFU, tornando-a, paradoxalmente, muito eficiente na assertividade e na substituição, mas menos performática na latência de acerto, sobretudo em Workloads Randômicos onde o custo O(n) se torna mais evidente.

## Autor
Artur de Lima Wanderley (Github: [@ArturALW](https://github.com/ArturALW))

Este arquivo faz parte do projeto final da Disciplina de Estruturas de Dados e Algoritmos e Laboratório de Estruturas de Dados e Algoritmos do Curso de Ciência da Computação da Universidade Federal de Campina Grande durante o período 2025.1.



               

# Estratégias de _Cache Eviction_

## Sumário

- [Estrutura de diretórios](#estrutura-de-diretórios)

- [O que é _cache_?](#o-que-é-cache)

- [_Cache Eviction_](#cache-eviction)

- [Estratégias abordadas nesse material](#estratégias-abordadas-nesse-material)

- [Indicadores *hit* e *miss*](#indicadores-hit-e-miss)

- [Indicadores de tempo médio de _hit_ e _miss_](#indicadores-de-tempo-médio-de-hit-e-miss)

- [_Workloads_](#workloads)

- [Resultados](#resultados)

## Estrutura de diretórios
```diff
  .
  |─── app
  |    |── bin
  |    |── build
  |    |── data
  |    |── scripts
  |    └── src
```
#### Diretório _data_
```diff
  |─── data
  |    |── graphs
  |         |── hit_graphs
  |         |── hit_time_graphs
  |         └── miss_ime_graphs
  |    |── input
  |        |── periodic_inputs
  |        |── randomic_inputs
  |        └── spike_inputs
  |    └── output
  |        |── fifo
  |        |── lfu
  |        |── lru
  |        |── random_replacement
  |        └── second_chance
```

O diretório _data_ contém os dados fundamentais para o projeto de análise das estratégias de cache abordadas mais à frente neste material. Em primeiro lugar, tem-se os gráficos associados a cada **política de cache**, assim como a cada _**workload**_. Tem-se, ainda, os arquivos _.txt_ que armazenam as cargas de texto que servem como _inputs_ para as políticas de cache. Por fim, o subdiretório _**output**_ contém os resultados das análises de desempenho de cada estratégia de _cache_, na forma de aqruivos _.data_.

#### Diretório _scripts_
```diff
  |─── scripts
  |    |── R
  |    |── bash
  |    └── python
```

Tem-se aqui os _scripts_ responsáveis pela geração de gráficos de desempenho, pela geração dos inputs que representam os _workloads_ e suas variações e pela automatização de processos de geração de dados por parte das políticas de _cache_.

#### Diretório src
```diff
  |─── src
  |    |── bin
  |    |── main
  |    └── test
```
No diretório _source_ (src), estão localizadas as implementações, em Java, das estratégias de cache abordadas neste material, além do diretório _test_, que contém as classes de testes unitários. Por fim, o diretório _bin_ contém os binários dos códigos Java.

## O que é _cache_?
_Cache_ é um tipo de memória limitada e de acesso rápido, na qual diversos dados são armazenados visando à sua própria obtenção em momentos posteriores com alta rapidez. Em aplicações reais, podem ser utilizadas diversas **estratégias de cache** com o intuito de aumentar a eficiência do processo, otimizando espaço e mantendo em cache dados adequados segundo critérios específicos. Nesse contexto, é fundamental entender o comportamento das variadas **políticas/estratégias de cache** implementadas em sistemas computacionais, pontuando sua eficiência e otimização, seja a partir de análises quantitativas ou qualitativas.

## _Cache Eviction_
Em se tratanto de _Cache_, um conceito fundamental a ser compreendido é o de _Cache Eviction_. Por se tratar de uma memória limitada, é preciso selecionar quais elementos devem permanecer em _cache_ e quais devem sair. Nesse cenário, é possível investigar as nuances de cada estratégia/política a fim de compreender como cada uma delas seleciona dados para remoção (ou "evicção"). Critérios como frequência, recência, ordem de inserção e aleatoriedade podem ser utilizados para definição de remoções, o que viabiliza, em cenários concretos, a otimização da memória de _cache_.

## Indicadores *hit* e *miss*
Dentro do contexto de cada **estratégia de cache**, é possível analisar tanto a quantidade de buscas por um elemento já presente em _cache_ - o que implica em acesso rápido e, portanto, eficiência - quanto a ida à memória secundária, de acesso lento e de pouca eficiência. O primeiro caso é conhecido como _**hit**_, o qual é extremamente desejável em qualquer política de _cache_, já que indica boa otimização e busca veloz. Por outro lado, a situação antagônica é denominada _**miss**_, e indica uma travessia custosa rumo à memória secundária, com processos pouco eficientes. Portanto, indicadores das quantidades de _hit_ e _miss_ podem servir de base para a construção de uma análise sólida sobre a eficiência de cada uma das estratégias de _cache_ abordadas nesse projeto.

## Indicadores de tempo médio de _hit_ e _miss_

## Estratégias abordadas nesse material

  1. [*FIFO (First-in First-Out)*](./app/src/main/java/org/example/cache_strategies/fifo/README.md)

  2. [*LFU (Least Frequently Used)*](./app/src/main/java/org/example/cache_strategies/lfu/README.md)
  
  3. [*LRU (Least Recently Used)*](./app/src/main/java/org/example/cache_strategies/lru_cache/README.md)

  4. [*Random Replacement*](./app/src/main/java/org/example/cache_strategies/random_replace/README.md)
  
  5. [*Second Chance (Clock)*](./app/src/main/java/org/example/cache_strategies/second_chance/README.md)

## _Workloads_
É importante destacar que as análises quantitativa e qualitativa passam sobretudo pela geração de cargas de elementos que reflitam o _stress_ gerado por dados reais em aplicações complexas. Dessa forma, visando simular cargas reais de dados para as estratégias de _cache_, é possível gerar _workloads_ específicos que reflitam cenários distintos, expondo as políticas de _cache_ a cargas desafiadoras.

### _Randomic_
O _workload_ do tipo _randomic_ é formado apenas por dados pseudoaleatórios e buscam simular o tipo de carga mais básica para o _cache_: aquela que não segue nenhum padrão de frequência ou recência de acesso e busca.

```bash
frequência | valor
        .
        .
        .
      2 2412406
      2 244812
      2 2454431
      2 2465189
      2 246761
      2 2490788
      2 2504447
      2 2517241
      2 2549572
      2 256414
      2 2600540
      2 26128
      2 2630081
      2 2649730
      2 26587
      2 2674648
      2 268182
      2 2720522
      2 2750956
      2 276214
      2 2763405
      2 2767086
        .
        .
        .
```

Um _workload_ randômico pode representar situações reais em que o padrão de acesso não segue uma lógica previsível ou periódica, mas sim ocorre de forma dispersa e variável ao longo do tempo. Esse tipo de comportamento é comum em sistemas com usuários humanos, por exemplo em plataformas de streaming, redes sociais ou servidores web, onde cada acesso depende de escolhas individuais e imprevisíveis. Também aparece em cenários de segurança da informação, como ataques de negação de serviço distribuídos (DDoS), nos quais os acessos são aleatórios e massivos. Assim, _workloads_ randômicos são úteis para modelar ambientes com alta incerteza e forte influência de eventos externos.


### _Periodic_

O _workload_ periódico é aquele em que a carga de trabalho segue um padrão repetitivo e ocorre em intervalos regulares, sem depender de eventos inesperados. Esse comportamento torna sua execução previsível, permitindo planejar recursos e otimizar desempenho com mais precisão.

```bash
frequência | valor
        .
        .
        .
      4 997838
      4 997880
      4 998963
      4 999332
      5 1193465
      5 1320124
      5 1442460
      5 1739573
      5 1860583
      5 1972078
      5 1979674
      5 2627901
      5 3464136
      5 3710358
      5 3890633
      5 4362226
      5 4512541
      5 4612229
      5 4615214
      5 4932987
      5 684559
      8 2581445
      8 2628161
      8 3629462
      8 4002401
      8 4422233
      8 4752377
      8 510234
      8 957601
        .
        .
        .
```

Um exemplo cotidiano desse tipo de _workload_ é a sincronização automática de e-mails, que ocorre em intervalos definidos. Também podemos citar notificações de aplicativos que consultam os servidores a cada poucos minutos para verificar novas mensagens, ou as atualizações de antivírus que seguem um cronograma fixo para buscar novas assinaturas. Esses casos evidenciam cargas que seguem um agendamento técnico pré-estabelecido, ao invés de surgirem de forma aleatória.

### _Spike_

_Workloads_ do tipo _spike_ são aqueles em que a demanda é geralmente baixa ou moderada, mas ocasionalmente dispara em picos muito altos por curtos períodos de tempo. Esse comportamento é imprevisível e exige que o sistema consiga lidar com sobrecargas momentâneas sem degradar o desempenho.

```bash
frequência | valor
        .
        .
        .
      2 627088
      2 683118
      2 731591
      2 732164
      2 733871
      2 793000
      2 811198
      2 832779
      2 87867
      2 92479
      2 962955
      2 973280
    411 4462714
   1000 1080341
   1000 2565361
   1000 3238247
   1000 3261057
   1000 3946842
   1000 4492629
   1000 4499312
   1000 4529391
   1000 4855382
        .
        .
        .
```
Exemplos comuns incluem do _spike_ são acessos a sites de notícias durante acontecimentos importantes, consultas em e-commerces quando um produto entra em promoção relâmpago, ou o aumento repentino de buscas em redes sociais por um assunto viral. Nesses casos, o _workload_ não é constante nem periódico, mas gera picos intensos que podem estressar servidores e sistemas de armazenamento.

## Resultados

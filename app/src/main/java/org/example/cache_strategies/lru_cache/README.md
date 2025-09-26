# PANORAMA GERAL

O algoritmo LRU (Least Recently Used) é uma política de substituição de cache que remove o item não utilizado há mais tempo. Ba-
seado no princípio da localidade temporal, onde dados acessados recentemente tendem a ser reutilizados, o LRU demonstra alta eficiên-
cia em cenários reais.
Sua aplicação mais significativa ocorre no gerenciamento de memória virtual dos sistemas operacionais, onde a RAM física atua como
cache para o armazenamento secundário. Quando a memória atinge sua capacidade, o LRU identifica as páginas de memória menos recentemente
acessadas para liberação, priorizando a manutençãode dados ativos.
A principal vantagem do LRU é sua capacidade de adaptação dinâmica aos padrões de acesso, minimizando cache misses em comparação
com políticas como FIFO. No entanto, sua complexidade de implementação e custo hardware justificam o uso de alternativas como políticas
aleatórias em caches de alto desempenho.
Essac combinação de eficácia teórica e aplicabilidade prática consolida o LRU como referência entre as políticas de substituição em
sistemas computacionasi modernos.

# IMPLEMENTAÇÃO

O algoritmo LRU tem como política de funcionamento o comportamento de retirar/descartar do cache o elemento com mais tempo sem
acesso. Diante disso, a implementação da lógica dessa política consiste na ideia de que dado um elemento que necessite ser acessado,
caso ele esteja presente no cache, ele é movido para o final, caso ele não esteja presente no cache ele é simplesmente adicionado no
final. Claro que o caso da adição so ocorre dessa maneira caso haja espaço no cache, caso o cache esteja no seu limite de elementos,
é removido o elemento do inicio, ou seja, o que esta a mais tempo sem ser acessado.
Dado o funcionamento do algoritmo, foi utilizado um HashMap e a ideia de nós ligados, semelhante a uma LinkedList, porém
não é utilizada de fato a sua instância, ao invés disso foi utilizado o auxilio da classe LRUNode também implementada pela nossa
equipe. O HashMap é responsável por mapear com maior eficiência cada chave(key) ao nó(valor) correspondente. Foi escolhido o HashMap
para guardar oselementos do cache já que ele viabiliza adiçao, consulta e remoção em O(1), que são ações utilizadas o tempo todo pelo
algoritmo e requer a maior eficiência possível. É utilizado também nós ligados porque garante que a gente mantenha os elementos na or-
dem de uso. Quando um elemento é acessado consiguimos movê-lo para o final em O(1) e também remover na mesma complexidade caso a capa-
cidade seja atingida atráves dos apontadores head e tail.

# RESULTADO

## HIT TIME

Analisando os gráficos sobre os tempos de hit das políticas é evidente que a LRU possui o melhor tempo de execução sem sombra de dúvidas, independente do cenário. Porém, é perceptivél também, seja alinalisando individualmente ou em conjunto, que a LRU possui um tempo de hit alto no começo e isso tem explicação.
Isso é um fenômeno comum e se deve tanto à sua implementação quanto à fase de "Warm-up"(fase de aquecimento do cache). No inicio da execução de um programa, a cache não tem nada para fornecer, resultando em um alto número de cache misses. Em um cenário real isso significa que é necessário buscar os dados na memória principal, que é muito mais lenta, isso é o Warm-up. E sobre sua implementação ter uma contribuição nesse fato, isso corre porque na sua implementação é utilizado uma lista duplamente encadeada, logo, diferente de um array que reserva espaços contíguos na memória e faz com que o acesso aos elementos seja o mais eficiente possível, em uma lista duplamente encadeada os elementos não são alocados de forma contígua, ou seja, eles são "espalhados" pela memória, o que faz com que o tempo de acesso tenha um maior custo inicial. Isso além da sobrecarga do gerenciamento de ponteiros, ja que cada hit envolve acessar o HashMap, remover o nó e reposicionar no final, e o fato do HashMap ter que lidar com o crescimento de buckets(rehash).
Resumindo, o hit time alto no início não se deve ao algoritmo LRU em si(ja que suas ações são O(1)), mas sim a efeitos práticos de implementação, depois que a estrutura e o ambiente "estabilizam", o hit time tende a ficar constante e baixo.

## MISS TIME

Quanto ao miss time também observamos um custo maior no inicio devido as mesmas causas abordadas no tópico de HIT TIME, questões de implementação além dos misses compulsivos. Porém observamos também que as taxas de miss são diferentes a depender do cenário ao qual submetemos a política. Se formos rankiar os cenário a depender do desempenho temos o cenário de picos em primeiro lugar(spike), seguido do cenario aleatório(randomic) e por último o periodico(periodic), vamos abordar o porque desses acontecimentos.

### PERIODIC

No cenário em que fornecemos uma carga periódica de trabalho ao LRU temos um problema, se o periodo de repetição for maior que a capacidade do cache, quando você volta a acessar os primeiros elementos, eles ja foram descartados, ou até mesmo que a repetição não seja superior a capacidade, mas sendo igual ou muito próximo, o mesmo comportamento acontece, temos muitos misses recorrentes porque o cache não "aprende" nada que consiga reaproveitar a longo prazo. Isso é observado no inicio do gráfico onde a capacidade do cache é menor, conforme o tamanho do cache aumenta percebe-se o esse problema se diluindo, ja que o tamanho do cache já é grande o suficiente para "aprender" algo reaproveitavél a longo prazo. Por isso o cenário em que submetemos o algoritmo a uma carga de trabalho periodica se mostra um inferior aos demais.

### RANDOMIC

Ao submetermos o algoritmo a uma carga de trabalho aleatória observamos que ela é superior à carga de trabalho periodica. Isso acontece porque como os acessos são uniformemente aleatórios, a chance de hit depende só do tamanho relativo do cache ao universo de elementos. Isso significa que não há padrão forte para o LRU explorar, mas também não há repetição que cause expulsão sistematica como no periodico. Portanto o miss time tende a se estabilizar em um valor intermediario, isso mostra o porque de ter misses supeirores ao cenário periodico e mostraremos no próximo tópico o porque de ter misses inferiores ao cenário de pico.

### SPIKE

Por fim o cenário em que a política se mostra mais eficiente, o cenário em que ocorre um pico de acesso a um determinado elemento. Ele se mostra melhor em relação aos outros dois cenários pois quando chega o pico(acessos repetidos a um mesmo conjunto pequeno de elementos) o LRU se beneficia muito, os elementos do spike ficam sempre no final(ou no inicio a depender da implementação do algoritmo, no nosso caso ele fica no final) e acontecem praticamente zero misses durante o pico. Logo, passado aquele primeiro momento misses compulsivos que é enfrentado em todos os cenários, o miss time cai muito no final, porque a carga de trabalho se torna altamente previsível e o cache "aprendeu" rápido, ou seja, o miss time cai pois no pico ele ja contém o elemento, ou passa a conter, e dai em diante são utilizados apénas hites, ja que esta sendo acesso um elemento ja contido. Por isso o spike tem o menor miss time final.

# Projeto Concorrência e Paralelismo

Desenvolvimento do projeto da disciplina de Programação Paralela.  
Professor: Rafael Nunes de Lima  
Aluna: Ana Beatriz Agostinho Astle  

### Objetivo do projeto

O projeto utilizará o processamento de uma grande matriz de números.
Para cada elemento da matriz deverá ser executada uma operação matematicamente custosa.
O projeto sequencial será fornecido pelo professor utilizando o repositório da disciplina.
```java
private static double calcular(double valor) {
    double resultado = valor;
    for (int i = 0; i < 1000; i++) {
        resultado +=
            Math.sin(valor + i)
            * Math.cos(valor - i)
            * Math.sqrt(Math.abs(valor) + 1);
    }
    return resultado;
}
```
O objetivo será processar todos os elementos da matriz e produzir um resultado final.
___
### Como rodar

- JDK 26
___
### Estrutura do Projeto

```
src/
├── Main.java                    # ponto de entrada — roda as 4 versões e imprime os tempos
├── v1/
│   └── Sequencial.java          # calcular() + processar() — baseline, sem paralelismo
├── v2/
│   └── NaoEstruturado.java      # calcular() + processarParalelo() com ExecutorService/threads manuais
├── v3/
│   └── Estruturado.java         # calcular() + processarEstruturado() com StructuredTaskScope
└── v4/
    └── EstadoCompartilhado.java # calcular() + duas variantes: AtomicInteger e ConcurrentLinkedQueue

resultados/
└── experimentos.csv             # tempo médio, speedup e nº de tarefas de cada rodada
```

- **`Main.java`** — orquestra a execução: chama cada versão, mede o tempo com `System.nanoTime()` e grava os resultados.
- **`v1` a `v4`** — cada pacote isola uma estratégia de paralelismo, mas compartilham a mesma `calcular()` sem alterações (é o que torna o speedup comparável entre elas).
- **`resultados/experimentos.csv`** — dados brutos dos experimentos (E1–E4), usados pra montar a tabela final e os gráficos, se você fizer algum.


___
### Diagrama de Arquitetura

___
### Resultados






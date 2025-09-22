# gera gráficos de colunas
library('ggplot2')

args <- commandArgs()

data = read.table(args[length(args)], header = T)

ggplot(data, aes(x = WorkloadLength, y = Hits, fill = CacheStrategy)) +
    geom_bar(stat = "identity", position = "dodge") +
    labs(title = "Hits per Cache Strategy",
         x = "Workload Length",
         y = "Hits")

library('ggplot2')

args <- commandArgs()

data = read.table(args[length(args)], header = T)

# plota normal
# ggplot(data, aes(x = WorkloadLength, y = Hits, colour = CacheStrategy)) + geom_line()

# plota em barras
ggplot(data, aes(x = WorkloadLength, y = Hits, fill = CacheStrategy)) +
    geom_bar(stat = "identity", position = "dodge") +
    labs(title = "Gráfico de barras",
         x = "Tamamho do Workload",
         y = "Hits")

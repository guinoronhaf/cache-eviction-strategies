library('ggplot2')

args <- commandArgs()

data = read.table(args[length(args)], header = T)

ggplot(data, aes(x = WorkloadLength, y = Hits, fill = CacheStrategy)) +
    geom_bar(stat = "identity", position = "dodge") +
    labs(title = "Gráfico de barras",
         x = "Tamamho do Workload",
         y = "Hits")

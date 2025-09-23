# gera graáficos de linhas
library('ggplot2') 

args <- commandArgs()

data = read.table(args[length(args)], header = T)

ggplot(data, aes(x = WorkloadLength, y = AverageHitTime, colour = CacheStrategy)) + 
    geom_line() +
    labs(title = "Average Hit time per Cache Strategy",
           x = "Workload Length",
           y = "Average Hit time (ns)")

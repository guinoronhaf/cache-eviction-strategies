# gera graáficos de linhas
library('ggplot2') 

args <- commandArgs()

data = read.table(args[length(args)], header = T)

ggplot(data, aes(x = WorkloadLength, y = AverageMissTime, colour = CacheStrategy)) + geom_line()

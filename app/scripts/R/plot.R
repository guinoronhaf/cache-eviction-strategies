library('ggplot2')

args <- commandArgs()

data = read.table(args[length(args)], header = T)
ggplot(data, aes(x = WorkloadLength, y = Hits, colour = CacheStrategy)) + geom_line()

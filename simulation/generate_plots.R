library(ggplot2)

tps=seq(700,1200,100)
aborts=c(7.76,9.84,12.46,15.56,18.04,22.12)
data=data.frame(tps,aborts)
data
p <- ggplot(data = data,aes(x=tps, y=aborts)) + 
  geom_line() +
  geom_point() + 
  ylim(0, 25) + xlab("transactions/s") + ylab("abort/s")
p

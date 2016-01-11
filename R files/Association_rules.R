#relationship between actors,directors,producers,writers
install.packages("arules")
library(arules)
data_ar <- read.csv(file.choose(), header = TRUE)
head(data_ar)
data_ar = data_ar[,c(6,7,8,9,16)]
data_ar <- subset(data_ar, cast0!="NA" & director!="NA" & producer!="NA" & writer!="NA")
rules <- apriori(data_ar, (parameter = list(minlen=4, supp=0.0004, conf=0.5)))
rules.sorted <- sort(rules, by="lift")
inspect(rules.sorted)
subset_matrix <- is.subset(rules.sorted, rules.sorted)
subset_matrix[lower.tri(subset_matrix, diag=T)] <- NA
redundant <- colSums(subset_matrix, na.rm=T) >= 1
which(redundant)
rules.pruned <- rules.sorted[!redundant]
inspect(rules.pruned)

#relationship between cast and genres

library(arules)
data_ar <- read.csv(file.choose(), header = TRUE)
head(data_ar)
data_ar = data_ar[,c(6,3,7,8)]
data_ar <- subset(data_ar, cast0!="NA" & cast1!="NA" & genre0!="NA")
rules <- apriori(data_ar, (parameter = list(minlen=4, supp=0.0003, conf=0.7)))
rules.sorted <- sort(rules, by="lift")
inspect(rules.sorted)
subset_matrix <- is.subset(rules.sorted, rules.sorted)
subset_matrix[lower.tri(subset_matrix, diag=T)] <- NA
redundant <- colSums(subset_matrix, na.rm=T) >= 1
which(redundant)
rules.pruned <- rules.sorted[!redundant]
inspect(rules.pruned)



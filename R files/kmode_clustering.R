myData <- read.csv(file.choose(), header = TRUE)
head(myData)
myData <- subset(myData, genre0!="NA" & cast0!="NA" & cast1!="NA" & director!="NA" & producer!="NA" & writer!="NA")

install.packages("klaR")
library(klaR)
act <- myData[,c(6,7,8,9,16)]
act1 <- myData[,c(3,6,7,8,9,16)]
head(act1)

head(myData)
set.seed(7626)
gp <- runif(nrow(act))
gp
act <- act[order(gp),]

head(act)
unique(myData$countries)

cl <- kmodes(act, 23, iter.max = 3, weighted = FALSE)
cl$cluster
matrixOfKM <- table(act1$genre0,cl$cluster)
matrixOfKM
### for printing the number of movies in different genres to find accuracy #########
for(i in dataCol){
  print(i)
  temp = subset(data_ar, genre0 == i)
  count = nrow(temp)
  print(count)
}









install.packages("RWeka")
install.packages("partykit")
install.packages("caret")
install.packages("e1071")
install.packages("class")
library(caret)
library(RWeka)
library(datasets)
library(e1071)

data_raw <- read.csv(file.choose(), header = TRUE)
data= data_raw[,c(1,2,4,8)]
length(table(data$genre))
table(data$genre)
set.seed(9242) #Ufid: 92820942
gp<-runif(nrow(data))
data <- data[order(gp),]
head(data)
train_set <- data[1:12000,]
test_set <- data[12000:16567,]

classifier <- naiveBayes(data[,3:4], data[,2])

classifier <- naiveBayes(train_set[,3:4], train_set[,2])
predictions <- predict(classifier, test_set[,3:4])
confusionMatrix(predictions, test_set[,2])
plot(predictions)
#table(predict(classifier, test_set[,-1]), test_set[,1])
test_set[,5]<- predictions

hit <- 0
for (i in 1:length(test_set[, 5])){
    for (j in 1:length(data[which(data$title == test_set[i, 1]), 2])) {
    if ((test_set[i, 5] == data[which(data$title == test_set[i, 1]), 2][j]))
      hit <- hit+1

  }  

}
accuracy <- hit/i
print(accuracy)


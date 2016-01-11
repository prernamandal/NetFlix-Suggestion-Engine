install.packages("e1071")
library(e1071)
install.packages("caret",dependencies = c("Depends", "Suggests"))
library(caret)
data_raw <- read.csv(file.choose(), header = TRUE)
set.seed(9242) #Ufid: 92820942
head(data_raw)
gp<-runif(nrow(data_raw))
data <- data_raw[order(gp),]
head(data)
train_set <- data[1:7950,]
head(train_set)
test_set <- data[7951:9939,]
head(test_set)
data1_train <- train_set[1:7950, c("genre1","cast0","writer","director","genre0")]
head(data1_train)
data1_test <- data[7951:9939, c("genre1","cast0","writer","director","genre0")]
head(data1_test)
classifier <- naiveBayes(data1_train[,c("genre1","cast0","writer","director")], data1_train[,c("genre0")])
classifier
predictions <- predict(classifier, data1_test[,c("genre1","cast0","writer","director")])
plot(predictions)
confusionMatrix(predictions, data1_test[,c("genre0")])
table(predict(classifier, data1_test[,-5]), data1_test[,5])

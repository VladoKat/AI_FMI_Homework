import pandas as pd
import numpy as np
import math
import operator

def ED(x1, x2): #it is used for calculating euclidean distance
    distance = 0
    length = min(x1.shape[1], x2.shape[0])
    for x in range(length):
        distance += np.square(x1[x] - x2[x])
    return np.sqrt(distance)

def knn(trainingSet, testInstance, k): #here we are defining our model

    distances = {}

    # length = testInstance.shape[1]

    for x in range(len(trainingSet)):
        dist = ED(testInstance, trainingSet.iloc[x])
        distances[x] = dist[0]
    sortdist = sorted(distances.items(), key=operator.itemgetter(1))
    neighbors = []
    for x in range(k):
        neighbors.append(sortdist[x][0])
    Votes = {} #to get most frequent class of rows
    for x in range(len(neighbors)):
        response = trainingSet.iloc[neighbors[x]][-1]
        if response in Votes:
            Votes[response] += 1
        else:
            Votes[response] = 1
    sortvotes = sorted(Votes.items(), key=operator.itemgetter(1), reverse=True)
    return(sortvotes[0][0], neighbors)

data = pd.read_csv("iris.csv")
print(data.head())

testSet = [[6.8, 3.4, 4.8, 2.4]]

test = pd.DataFrame(testSet)
k = 1
k1 = 3
result,neigh = knn(data, test, k)
result1,neigh1 = knn(data, test, k1)
print(result)
print(neigh)
print(result1)
print(neigh1)

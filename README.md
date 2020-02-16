# A Design and Evaluation of an Edge Concurrency Control Protocol for Distributed Graph Databases #

The repository currently contains the paper, slide deck and simulation code for "A Design and Evaluation of an Edge Concurrency Control Protocol for Distributed Graph Databases" that was been accepted at [EPEW 2019](https://www.epew19.deib.polimi.it).

## Background ##

Distributed graph databases can be implemented in several ways. One approach is to add graph processing layer on top of an existing distributed database. These databases are often called *non-native* graph databases. Typically in this approach guarantees regarding data consistency are inherited from the underlying database.

*[Eventual Consistency](https://queue.acm.org/detail.cfm?id=2462076):* if no additional updates are made to a given data item, all reads to that item will eventually return the same value. A key feature is eventual consistency is (a pretty weak) guarantee about a single operation on a single object. Databases traditional provide multi-object guarantees using the abstraction of transactions, the highest of which is *Serializability*.

Our concern is that databases operating with eventual consistency semantics are not sufficiently dependable to store graph data. This hypothesis was explored at length in a [EPEW 2018 paper](https://doi.org/10.1007/978-3-030-02227-3_1).

The obvious solution is to use existing concurrency control mechanism such as 2PL, but this is believed to significantly harm performance. This lead to the development of a concurrency control algorithm that provides semantics between eventual consistency and serializability on the spectrum of consistency.

## Edge Concurrency Control Algorithm ##

The algorithm focuses solely on updates to distributed edges, which are edges that span partitions. There are two sub-protocols within the algorithm that provide different guarantees:
+ Collision Detection: ensures reciprocal consistency a given update to a distributed edge.
+ Order Arbiteration: ensures edge-order consistency between transactions that overlap in the set of distributed edges they update.

## Evaluation ##

In order to assess the performance of the protocol an approximate model was developed, which was then compared against simulations in order to measure accuracy.

See the paper and slide deck for more details.

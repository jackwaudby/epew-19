# Summary 

The program consist of 4 events:

+ transaction arrival `ArrivalEvent`
+ transaction update 1 `Update1Event`
+ transaction update 2 `Update2Event`
+ arbiter service `ArbiterEvent` 

Each event has an associated action. Simulation parameters are defined in `simulation.properties` and accessed through 
the `SimulationConfiguration` class. Random numbers are generated in `SimulationRandom` class. System metrics are collected in the 
`SystemMetrics` class. 

## System State

Global simulation state:
+ simulation clock, `GlobalClockSingleton`
+ an array list containing all distributed edges in the database, `GlobalEventListSingleton`
+ an array list containing event, `GlobalEdgeListSingleton`
+ an single arbiter instance containing a hit list and queue, `ArbiterSingleton`
+ an array list of all active transactions, used to clear a completed transaction from in-flight transaction's predecessor lists, `GlobalActiveTransactionList`

Simulation objects: 
+ transaction containing ID, updates, updates completed, list of predecessors, collision detection information, start time and end time, `Transaction`
+ provisional write containing the ID of the writing transaction and a write label (1 or 2), `ProvisionalWrite`
+ distributed edge containing an ID, an array list for provisional writes to it's left side and an array list for provisional writes to it's right side, `DistributedEdge`


# Assumptions 
+ transaction can update the same edge multiple times  
+ when aborts a transaction can remove itself from in-flight transactions
+ first update starts as soon as a transaction arrives 
+ next update 1 starts instantaneously as update 2 completes 

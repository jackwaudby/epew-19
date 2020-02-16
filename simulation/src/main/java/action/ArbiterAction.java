package action;

import event.ArbiterEvent;
import event.EventType;
import org.apache.log4j.Logger;
import state.*;
import utils.*;


public class ArbiterAction {

    private final static Logger LOGGER = Logger.getLogger(ArbiterAction.class.getName());


    public void action(ArbiterEvent arbiterEvent) {

        Transaction transaction; // to be processed


        if (!ArbiterSingleton.getInstance().getQueue().isEmpty()) {         // if queue not empty
            transaction = ArbiterSingleton.getInstance().getQueue().poll(); // get head of queue

            if (ArbiterSingleton.getInstance().getHitList().contains(transaction.getId())){ // if hit list contains id
                LOGGER.debug("Transaction ID: " + transaction.getId() );
                LOGGER.debug("In Hit List");
                LOGGER.debug("Transaction Aborted");
                SystemMetrics.getInstance().incrementAborts();                                  // increment aborts
                SystemMetrics.getInstance().incrementArbitration();                             //  increment aborts by arbiter
                transaction.setEndTime(GlobalClockSingleton.getInstance().getGlobalClock());    // set transaction end time
                SystemMetrics.getInstance().addLifetime(transaction.getLifetime());             // get life time and add to cumulative
                GlobalEdgeListSingleton.getInstance().clearProvisionalWrites(transaction.getId()); // clear prov writes from edges

                // remove from active list + everyone pred lists
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());


            } else {
                LOGGER.debug("Transaction ID: " + transaction.getId() );
                LOGGER.debug("Not in Hit List");
                LOGGER.debug("Transaction Succeeds");
                LOGGER.debug("Add Predecessor to Hit List");
                ArbiterSingleton.getInstance().getHitList().addAll(transaction.getPredecessors()); // add predecessors to hit list
                LOGGER.debug("Clear Provisional Writes");
                GlobalEdgeListSingleton.getInstance().clearProvisionalWrites(transaction.getId()); // clear prov writes from edges
                SystemMetrics.getInstance().incrementCommitted();                                   // increment commit
                transaction.setEndTime(GlobalClockSingleton.getInstance().getGlobalClock());        // set end time
                SystemMetrics.getInstance().addLifetime(transaction.getLifetime());                 // add to cumulative

                // remove from active list + everyone pred lists
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());

            }
        } else {
            LOGGER.debug("Arbiter Queue Empty");
        }
        // next arbiter event
        ArbiterEvent nextArbiterEvent = new ArbiterEvent(SimulationRandom.getInstance().generateServiceDelay(), EventType.ARBITER);
        GlobalEventListSingleton.getInstance().addEvent(nextArbiterEvent);


    }


}

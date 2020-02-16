import event.ArbiterEvent;
import event.ArrivalEvent;
import event.EventType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import state.DistributedEdge;
import state.GlobalEdgeListSingleton;
import state.GlobalEventListSingleton;
import utils.SimulationConfiguration;
import utils.SimulationRandom;
import utils.SystemMetrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO: this is a hack of a test
@DisplayName("Arbiter Simulation Test")
class ArbiterSimulationTest {

    private final int EXP_TPS = 1100;
    private final int EXP_ARR = 1686;
    private final int EXP_COMP = 1100;
    private final int EXP_ABORTS = 938;
    private final int EXP_COLL = 920;
    private final int EXP_ARB = 18;
    private final int EXP_SEND = 722;



    @BeforeAll
    static void init() {
        System.out.println("Set Config.");
        SimulationConfiguration.getInstance().setSeed("true");
        SimulationConfiguration.getInstance().setSeedValue("2");
        SimulationConfiguration.getInstance().setAverageArbiterServiceTime("0.005");
        SimulationConfiguration.getInstance().setTPS("1100");
        SimulationConfiguration.getInstance().setAverageArbiterServiceTime("0.01");
        SimulationConfiguration.getInstance().setSimulateTime("false");
        SimulationConfiguration.getInstance().setTxnLimit("0");
        SimulationConfiguration.getInstance().setSimulationPeriod("0");
        SimulationConfiguration.getInstance().setScaleFactor("1");
        SimulationConfiguration.getInstance().setDatabaseSize("100");
        SimulationConfiguration.getInstance().setStartValue("0.2");
        SimulationConfiguration.getInstance().setCommonRatio("0.8");
        SimulationConfiguration.getInstance().setLength("50");

        int databaseSize = SimulationConfiguration.getInstance().getDatabaseSize();

        // init. database
        for (int i = 0; i < databaseSize; i++ ) {
            DistributedEdge e = new DistributedEdge(i);
            GlobalEdgeListSingleton.getInstance().addEdge(e);
        }

        // init. first arrival event
        double initEventTime = SimulationRandom.getInstance().generateNextArrival();
        GlobalEventListSingleton.getInstance().addEvent(new ArrivalEvent(initEventTime, EventType.ARRIVAL));

        // init. first arbiter event
        double initArbiter = SimulationRandom.getInstance().generateServiceDelay();
        GlobalEventListSingleton.getInstance().addEvent(new ArbiterEvent(initArbiter, EventType.ARBITER));
    }


    @Test
    @DisplayName("Run Algorithm test")
    void runSimulation() {

        ArbiterSimulation.runSimulation();

        assertEquals(EXP_TPS,SystemMetrics.getInstance().getTps());
        assertEquals(EXP_ARR,SystemMetrics.getInstance().getArrivals());
        assertEquals(EXP_ABORTS,SystemMetrics.getInstance().getAborts());
        assertEquals(EXP_COMP,SystemMetrics.getInstance().getCompleted());
        assertEquals(EXP_COLL,SystemMetrics.getInstance().getCollisions());
        assertEquals(EXP_ARB,SystemMetrics.getInstance().getArbitration());
        assertEquals(EXP_SEND,SystemMetrics.getInstance().getSentToArbiter());

    }

}

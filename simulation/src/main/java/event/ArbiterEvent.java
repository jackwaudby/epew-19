package event;

public class ArbiterEvent extends AbstractEvent{

    public ArbiterEvent(double eventTime, EventType eventTypeEnum) {
        super(eventTime, eventTypeEnum);
    }

    @Override
    public String toString() {
        return "{" +
                "time=" + String.format("%.5f",getEventTime()) +
                ", " + "type=" + getEventType() +
                "}";
    }
}

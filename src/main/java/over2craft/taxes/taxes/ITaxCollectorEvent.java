package over2craft.taxes.taxes;

public interface ITaxCollectorEvent {

    public int getPriority();

    public void onWithDraw(CollectorEvent e);
}

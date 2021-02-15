package over2craft.taxes.taxes.listeners;

import over2craft.taxes.configuration.TaxingType;
import over2craft.taxes.taxes.CollectorEvent;
import over2craft.taxes.taxes.ITaxCollectorEvent;

public class WithDrawPlayer implements ITaxCollectorEvent {

    @Override
    public int getPriority() {
        return 125;
    }

    @Override
    public void onWithDraw(CollectorEvent e) {
        if (e.getTaxConfigSection().getTaxingType() == TaxingType.ABSOLUTE) {
            e.setPlayerNewBalance(e.getPlayerOldBalance() - e.getTaxConfigSection().getValue());
        }

        if (e.getTaxConfigSection().getTaxingType() == TaxingType.PERCENT) {
            e.setPlayerNewBalance(e.getPlayerOldBalance() - e.getPlayerOldBalance() * e.getTaxConfigSection().getValue() / 100);
        }
    }
}

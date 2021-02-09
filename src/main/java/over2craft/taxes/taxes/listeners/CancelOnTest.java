package over2craft.taxes.taxes.listeners;

import org.bukkit.Bukkit;
import over2craft.taxes.Utils.StringsUtils;
import over2craft.taxes.taxes.CollectorEvent;
import over2craft.taxes.taxes.ITaxCollectorEvent;

public class CancelOnTest implements ITaxCollectorEvent {

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public void onWithDraw(CollectorEvent e) {
        if (e.isFake()) {
            e.setCanceled(true);
            Bukkit.getLogger().info(
                    StringsUtils.format("Collecting is running in test mode," +
                            " players withdraw will be canceled for testing purpose")
            );
        }
    }
}

package over2craft.taxes.taxes.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import over2craft.taxes.Utils.StringsUtils;
import over2craft.taxes.taxes.CollectorEvent;
import over2craft.taxes.taxes.ITaxCollectorEvent;

public class cancelNotInRange implements ITaxCollectorEvent {


    @Override
    public int getPriority() {
        return 115;
    }

    @Override
    public void onWithDraw(CollectorEvent e) {
        if (!e.isInRange()) {
            e.setCanceled(true);

            String logMsg = String.format(
                    "Skipped player %s for taxes range [%s, %s[",
                    e.getOfflinePlayer().getName(),
                    StringsUtils.formatNumber(e.getTaxConfigSection().getMinMoney()),
                    StringsUtils.formatNumber(e.getTaxConfigSection().getMaxMoneyStrict())
            );

            if (e.getSender() != null && e.getSender() instanceof Player) {
                e.getSender().sendMessage(logMsg);
                Bukkit.getLogger().info(logMsg);
                return;
            }

            Bukkit.getLogger().info(logMsg);
        }
    }
}

package over2craft.taxes.taxes.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import over2craft.taxes.Utils.StringsUtils;
import over2craft.taxes.taxes.CollectorEvent;
import over2craft.taxes.taxes.ITaxCollectorEvent;

public class NotifyAdmin implements ITaxCollectorEvent {

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    public void onWithDraw(CollectorEvent e) {

        if (!e.isInRange()) {
            Bukkit.getLogger().info(
                    StringsUtils.format("Player %s %s skipped, no interval matching",
                            e.getOfflinePlayer().getName(),
                            e.getOfflinePlayer().getUniqueId()
                    ));
            return;
        }

        String message = StringsUtils.format("Collected %s from player %s %s",
                e.getPlayerNewBalance(),
                e.getOfflinePlayer().getName(),
                e.getOfflinePlayer().getUniqueId());

        if (e.getSender() != null && e.getSender() instanceof Player) {
            e.getSender().sendMessage(message);
            Bukkit.getLogger().info(message);
        } else if (e.getSender() != null) {
            Bukkit.getLogger().info(message);
        }

    }
}

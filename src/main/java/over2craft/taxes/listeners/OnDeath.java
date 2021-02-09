package over2craft.taxes.listeners;

import over2craft.taxes.configuration.EventSection;
import over2craft.taxes.configuration.EventType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import over2craft.taxes.taxes.CollectTaxes;

public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            (new CollectTaxes(new EventSection(EventType.onDeath))).startCollecting(event.getEntity().getPlayer());
        }
    }

}

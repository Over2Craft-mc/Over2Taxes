package over2craft.taxes.taxes;

import org.bukkit.command.CommandSender;
import over2craft.taxes.configuration.EventSection;
import over2craft.taxes.configuration.TaxingSection;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import over2craft.taxes.Taxes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class CollectTaxes {

    private CommandSender sender;
    private final EventSection eventTypeConfigSection;
    private boolean fake;
    private static final ArrayList<ITaxCollectorEvent> events = new ArrayList<>();

    public static void registerListener(ITaxCollectorEvent e) {
        events.add(e);
    }

    public CollectTaxes(EventSection es ) {
        this.eventTypeConfigSection = es;
    }

    public void startCollecting() {

        Arrays.stream(Bukkit.getOfflinePlayers()).distinct().forEach((
                offlinePlayer -> eventTypeConfigSection.getTaxes().forEach(
                    (taxingSection -> collect(offlinePlayer, taxingSection, getEconomy().getBalance(offlinePlayer)))
        )));
    }

    public void startCollecting(OfflinePlayer player) {
        eventTypeConfigSection.getTaxes().forEach(
                taxingSection -> collect(player, taxingSection, getEconomy().getBalance(player))
        );
    }

    public CollectTaxes fake(boolean fake) {
        this.fake = fake;
        return this;
    }

    public CollectTaxes enableVerbose(CommandSender sender) {
        this.sender = sender;
        return this;
    }

    private void collect(OfflinePlayer player, TaxingSection taxConfigSection, double money) {

        CollectorEvent e = new CollectorEvent();
        e.setFake(fake);
        e.setPlayerOldBalance(money);
        e.setTaxConfigSection(taxConfigSection);
        e.setOfflinePlayer(player);

        if (sender != null) {
            e.setSender(sender);
        }

        events.stream()
                .sorted((Comparator.comparingDouble(ITaxCollectorEvent::getPriority).reversed()))
                .forEach((event) -> {
                    if (!e.isCancelled()) {
                        event.onWithDraw(e);
                    }
                });

        if (e.isCancelled()) {
            return;
        }

        getEconomy().withdrawPlayer(player, e.getPlayerNewBalance());

    }

    private Economy getEconomy() {
        return Taxes.plugin.getEconomy();
    }



}

package over2craft.taxes.services;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import over2craft.taxes.configuration.EventSection;
import over2craft.taxes.configuration.TaxingSection;
import over2craft.taxes.configuration.TaxingType;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import over2craft.taxes.Taxes;


public class CollectTaxes {


    private CommandSender sender;
    private final EventSection es;
    private boolean fake;

    public CollectTaxes(EventSection es ) {
        this.es = es;
    }

    public void startCollecting() {

        es.getTaxes().forEach((taxingSection -> {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                System.out.println(offlinePlayer.getUniqueId());
                collect(offlinePlayer, taxingSection);
            }
        }));
    }

    public void startFakeCollecting() {

        es.getTaxes().forEach((taxingSection -> {
            for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                System.out.println(offlinePlayer.getUniqueId());
                collect(offlinePlayer, taxingSection);
            }
        }));
    }

    public void startCollecting(OfflinePlayer player) {
        es.getTaxes().forEach((taxingSection -> collect(player, taxingSection)));
    }

    public CollectTaxes fake(boolean fake) {
        this.fake = fake;
        return this;
    }

    public CollectTaxes enableVerbose(CommandSender sender) {
        this.sender = sender;
        return this;
    }

    private void collect(OfflinePlayer player, TaxingSection ts) {

        if (!(
                getEconomy().getBalance(player) >= ts.getMinMoney()
                && getEconomy().getBalance(player) < ts.getMaxMoneyStrict()
        )) {
            if (sender != null) {
                String msg = String.format("[Over2Taxes] Player %s %s skipped, no interval matching", player.getName(), player.getUniqueId());
                Bukkit.getLogger().info(msg);
                if (sender instanceof Player && ((Player) sender).isOnline()) {
                    sender.sendMessage(msg);
                }
            }
            return;
        }

        if (ts.getTaxingType() == TaxingType.ABSOLUTE) {

            if (!fake) {
                getEconomy().withdrawPlayer(player, ts.getValue());
                new NotifyPlayer(player, ts.getValue(), getEconomy().getBalance(player));
            } else {
                Bukkit.getLogger().info("Nothing were withdrawed in test mode");
            }

            if (sender != null) {
                String msg = String.format("[Over2Taxes] Collected %s from player %s %s", ts.getValue(), player.getName(), player.getUniqueId());
                Bukkit.getLogger().info(msg);
                if (sender instanceof Player && ((Player) sender).isOnline()) {
                    sender.sendMessage(msg);
                }
            }
        }

        if (ts.getTaxingType() == TaxingType.PERCENT) {
            double moneyToCollect = getEconomy().getBalance(player) * ts.getValue() / 100;

            if (!fake) {
                getEconomy().withdrawPlayer(player, moneyToCollect);
                new NotifyPlayer(player, moneyToCollect, getEconomy().getBalance(player));
            } else {
                Bukkit.getLogger().info("Nothing were withdrawed in test mode");
            }

            if (sender != null) {
                String msg = String.format("[Over2Taxes] Collected %s from player %s %s", moneyToCollect, player.getName(), player.getUniqueId());
                Bukkit.getLogger().info(msg);
                if (sender instanceof Player && ((Player) sender).isOnline()) {
                    sender.sendMessage(msg);
                }
            }
        }
    }

    private Economy getEconomy() {
        return Taxes.plugin.getEconomy();
    }



}

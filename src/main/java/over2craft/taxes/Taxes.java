package over2craft.taxes;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import over2craft.taxes.listeners.Commands;
import over2craft.taxes.listeners.OnDeath;
import over2craft.taxes.taxes.CollectTaxes;
import over2craft.taxes.taxes.listeners.*;

import java.util.Objects;

public final class Taxes extends JavaPlugin {

    private Economy econ;

    public static Taxes plugin;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        plugin = this;

        if (!setupEconomy()) {
            this.getLogger().severe("[Over2Taxes] Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new OnDeath(), this);
        Objects.requireNonNull(getCommand("over2taxes-collect")).setExecutor(new Commands());
        Objects.requireNonNull(getCommand("over2taxes-collect-verbose")).setExecutor(new Commands());

        Objects.requireNonNull(getCommand("over2taxes-reload")).setExecutor(
                (commandSender, command, s, strings) -> {
                    Taxes.plugin.reloadConfig();
                    commandSender.sendMessage(" ");
                    commandSender.sendMessage("[Over2Taxes] Plugin reloaded.");
                    commandSender.sendMessage(" ");
                    return true;
                });

        CollectTaxes.registerListener(new NotifyPlayers());
        CollectTaxes.registerListener(new CancelOnTest());
        CollectTaxes.registerListener(new NotifyAdmin());
        CollectTaxes.registerListener(new WithDrawPlayer());
        CollectTaxes.registerListener(new cancelNotInRange());

    }

    @Override
    public void onDisable() {}


    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {
        return econ;
    }
}

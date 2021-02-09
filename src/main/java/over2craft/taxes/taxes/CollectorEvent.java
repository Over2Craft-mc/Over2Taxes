package over2craft.taxes.taxes;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import over2craft.taxes.Taxes;
import over2craft.taxes.configuration.TaxingSection;

public class CollectorEvent {

    private CommandSender sender;
    private TaxingSection TaxConfigSection;
    private boolean fake;
    private OfflinePlayer offlinePlayer;
    private double playerOldBalance;

    private boolean cancelEvent = false;
    private double playerNewBalance;

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public TaxingSection getTaxConfigSection() {
        return TaxConfigSection;
    }

    public void setTaxConfigSection(TaxingSection taxConfigSection) {
        this.TaxConfigSection = taxConfigSection;
    }

    public boolean isFake() {
        return fake;
    }

    public void setFake(boolean fake) {
        this.fake = fake;
    }

    public boolean isCancelled() {
        return cancelEvent;
    }

    public void setCanceled(boolean canceled) {
        this.cancelEvent = canceled;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public boolean isInRange() {
        return getEconomy().getBalance(getOfflinePlayer()) >= getTaxConfigSection().getMinMoney()
                && getEconomy().getBalance(getOfflinePlayer()) < getTaxConfigSection().getMaxMoneyStrict();
    }

    public Economy getEconomy() {
        return Taxes.plugin.getEconomy();
    }

    public double getPlayerOldBalance() {
        return playerOldBalance;
    }

    public void setPlayerOldBalance(double playerOldBalance) {
        this.playerOldBalance = playerOldBalance;
    }

    public double getPlayerNewBalance() {
        return playerNewBalance;
    }

    public void setPlayerNewBalance(double playerNewBalance) {
        this.playerNewBalance = playerNewBalance;
    }
}

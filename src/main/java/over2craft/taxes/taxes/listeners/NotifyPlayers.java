package over2craft.taxes.taxes.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import over2craft.taxes.Utils.StringsUtils;
import over2craft.taxes.configuration.GlobalConfig;
import over2craft.taxes.taxes.CollectorEvent;
import over2craft.taxes.taxes.ITaxCollectorEvent;

import java.util.Objects;

public class NotifyPlayers implements ITaxCollectorEvent {

    private OfflinePlayer player;
    private double withdraw;
    private double money;

    @Override
    public int getPriority() {
        return 25;
    }

    @Override
    public void onWithDraw(CollectorEvent e) {

        this.player = e.getOfflinePlayer();
        this.withdraw = e.getPlayerOldBalance() - e.getPlayerNewBalance();
        this.money = e.getPlayerNewBalance();

        if (player.isOnline()) {
            GlobalConfig.getOnlineMessages().forEach((msg) -> Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).sendMessage(placeholders(msg)));
            return;
        }

        GlobalConfig.getOfflineCommands().forEach((cmd) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placeholders(cmd)));

    }

    private String placeholders(String str) {
        str = str.replace("$player", Objects.requireNonNull(player.getName()));
        str = str.replace("$withdraw", StringsUtils.formatNumber(withdraw));
        return str.replace("$money", StringsUtils.formatNumber(money));
    }
}

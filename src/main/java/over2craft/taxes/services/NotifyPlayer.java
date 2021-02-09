package over2craft.taxes.services;

import over2craft.taxes.configuration.GlobalConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class NotifyPlayer {

    private final OfflinePlayer player;
    private final double withdraw;
    private final double money;

    public NotifyPlayer(OfflinePlayer player, double withdraw, double money) {

        this.player = player;
        this.withdraw = withdraw;
        this.money = money;

        if (player.isOnline()) {
            GlobalConfig.getOnlineMessages().forEach((msg) -> Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).sendMessage(placeholders(msg)));
            return;
        }

        GlobalConfig.getOfflineCommands().forEach((cmd) -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), placeholders(cmd)));
    }

    private String placeholders(String str) {
        str = str.replace("$player", Objects.requireNonNull(player.getName()));
        str = str.replace("$withdraw", formatNumber(withdraw));
        return str.replace("$money", formatNumber(money));
    }

    private String formatNumber(double number) {
        return String.format("%,d", (int) number).replace(',', ' ');
    }

}

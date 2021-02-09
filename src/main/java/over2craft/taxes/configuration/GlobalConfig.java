package over2craft.taxes.configuration;

import over2craft.taxes.Taxes;

import java.util.List;

public class GlobalConfig {

    public static List<String> getOfflineCommands() {
        return Taxes.plugin.getConfig().getStringList("taxes.notify.send-essentials-mail-if-offline");
    }

    public static List<String> getOnlineMessages() {
        return Taxes.plugin.getConfig().getStringList("taxes.notify.send-message-if-online");
    }


}

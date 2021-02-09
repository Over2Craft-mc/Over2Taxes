package over2craft.taxes.listeners;

import over2craft.taxes.configuration.EventSection;
import over2craft.taxes.configuration.EventType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import over2craft.taxes.services.CollectTaxes;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("Please confirm your action");
            help(sender);
            return true;
        }

        if (args[0].equals("test")) {
            sender.sendMessage("[Over2Taxes] Start a fake collecting...");
            (new CollectTaxes(new EventSection(EventType.onCommand))).
                    enableVerbose(sender)
                    .fake(true)
                    .startFakeCollecting();
            sender.sendMessage("[Over2Taxes] Stopped fake collecting...");
            return true;
        }

        if (command.getName().contains("verbose") && args[0].equals("confirm")) {
            sender.sendMessage("[Over2Taxes] Start collecting in verbose mode...");
            (new CollectTaxes(new EventSection(EventType.onCommand)))
                    .enableVerbose(sender)
                    .startCollecting();
            sender.sendMessage("[Over2Taxes] Stopped collecting...");
            return true;
        }

        if (args[0].equals("confirm")) {
            sender.sendMessage("[Over2Taxes] Start collecting...");
            (new CollectTaxes(new EventSection(EventType.onCommand))).startCollecting();
            sender.sendMessage("[Over2Taxes] Stopped collecting...");
            return true;
        }

        help(sender);
        return true;
    }

    public void help(CommandSender sender) {
        sender.sendMessage("/over2taxes-collect confirm - Collect taxes");
        sender.sendMessage("");
        sender.sendMessage("/over2taxes-collect-verbose confirm - Collect taxes in verbose mode (show what are collected");
        sender.sendMessage("");
        sender.sendMessage("/over2taxes-collect test - Simulate /over2taxes-collect-verbose confirm but does not change players balance (testing purpose). Notifications to players won't be sent");
    }

}

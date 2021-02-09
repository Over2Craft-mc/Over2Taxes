package over2craft.taxes.configuration;

import org.bukkit.configuration.ConfigurationSection;
import over2craft.taxes.Taxes;

import java.util.ArrayList;
import java.util.Objects;

public class EventSection {

    private final ArrayList<TaxingSection> taxes = new ArrayList<>();

    private EventType eventType;

    public EventSection(EventType eventType) {
        this.eventType = eventType;

        for (
                String section :
                getConfigSection(eventType.getValue()).getKeys(false)
        ) {
            taxes.add(new TaxingSection(getConfigSection(String.format("%s.%s", eventType.getValue(), section))));
        }
    }

    private ConfigurationSection getConfigSection(String str) {
        return Objects.requireNonNull(Taxes.plugin.getConfig().getConfigurationSection(str));
    }

    public ArrayList<TaxingSection> getTaxes() {
        return taxes;
    }
}

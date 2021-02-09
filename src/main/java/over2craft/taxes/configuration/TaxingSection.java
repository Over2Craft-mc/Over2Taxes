package over2craft.taxes.configuration;

import org.bukkit.configuration.ConfigurationSection;

public class TaxingSection {

    double minMoney;
    double maxMoneyStrict;
    TaxingType taxingType;
    double value;

    public TaxingSection(ConfigurationSection configurationSection) {
        minMoney = configurationSection.getDouble("min");
        maxMoneyStrict = configurationSection.getDouble("max");
        taxingType = TaxingType.valueOf(configurationSection.getString("type"));
        value = configurationSection.getDouble("value");
    }

    public double getMinMoney() {
        return minMoney;
    }

    public double getMaxMoneyStrict() {
        return maxMoneyStrict;
    }

    public TaxingType getTaxingType() {
        return taxingType;
    }

    public double getValue() {
        return value;
    }
}

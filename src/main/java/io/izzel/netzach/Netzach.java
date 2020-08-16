package io.izzel.netzach;

import io.izzel.netzach.config.ConfigSpec;
import io.izzel.netzach.data.DataManager;
import io.izzel.taboolib.loader.Plugin;
import io.izzel.taboolib.module.dependency.Dependency;
import io.izzel.taboolib.module.locale.TLocale;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

@Dependency(maven = "com.h2database:h2:1.4.200")
public final class Netzach extends Plugin {

    private ConfigSpec configSpec;
    private DataManager dataManager;

    @SuppressWarnings("deprecation")
    @Override
    public void onLoading() {
        this.saveDefaultConfig();
        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(ConfigSpec.class, this.getClassLoader()));
        this.configSpec = yaml.load(this.getConfig().saveToString());
        this.dataManager = new DataManager();
    }

    @Override
    public void onStarting() {
        this.dataManager.open();
        TLocale.sendToConsole("load.complete");
    }

    @Override
    public void onStopping() {
        this.dataManager.close();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ConfigSpec getConfigSpec() {
        return configSpec;
    }

    public static Netzach instance() {
        return (Netzach) plugin;
    }

    public static ConfigSpec config() {
        return instance().getConfigSpec();
    }
}

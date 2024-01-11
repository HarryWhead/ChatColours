package chatcolor.chatcolor;

import chatcolor.chatcolor.Commands.OpenChatGUI;
import chatcolor.chatcolor.Commands.OpenNameGUI;
import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import chatcolor.chatcolor.GUI.createGUI;
import chatcolor.chatcolor.PluginAPIs.ChatColorPlaceholderHook;
import chatcolor.chatcolor.listeners.InventoryClick;
import chatcolor.chatcolor.listeners.PlayerJoinlistener;
import chatcolor.chatcolor.listeners.PlayerMessageListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatColor extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        initConfigs();
        initListener();
        initCommands();
        initPlaceHolders();
    }

    private void initConfigs()
    {
        saveResource("ChatColorGUI.yml", false);
        saveResource("NameColorGUI.yml", false);

        //Setup config
        DataFile.setup();
        DataFile.get().options().copyDefaults(true);
        DataFile.save();

        ChatColorConfig.setup();
        ChatColorConfig.get().options().copyDefaults(true);
        ChatColorConfig.save();

        NameColorConfig.setup();
        NameColorConfig.get().options().copyDefaults(true);
        NameColorConfig.save();

        createGUI GUI = new createGUI();
        getServer().getPluginManager().registerEvents(GUI, this);
    }

    private void initListener()
    {
        getServer().getPluginManager().registerEvents(new PlayerMessageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinlistener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
    }

    private void initCommands()
    {
        getCommand("ChatColor").setExecutor(new OpenChatGUI());
        getCommand("ChatName").setExecutor(new OpenNameGUI());
    }

    private void initPlaceHolders()
    {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ChatColorPlaceholderHook(this).register();
        } else {
            getLogger().warning("PlaceholderAPI not found! Your custom placeholders won't work.");
        }

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

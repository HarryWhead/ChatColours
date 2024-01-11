package chatcolor.chatcolor.GUI;

import chatcolor.chatcolor.Configs.ChatColorConfig;
import chatcolor.chatcolor.Configs.DataFile;
import chatcolor.chatcolor.Configs.NameColorConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class createGUI implements Listener {

    public static Map<String, Inventory> cgui;
    public static Map<String, Inventory> ngui;
    public static Map<String,  Map<Integer, ColorData>> ccolorMap;
    public static Map<String,  Map<Integer, ColorData>> ncolorMap;
    public createGUI()
    {
        ccolorMap = new HashMap<>();
        ncolorMap = new HashMap<>();
        cgui = new HashMap<>();
        ngui = new HashMap<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            GUI(Bukkit.createInventory(null, ChatColorConfig.get().getInt("guiSize"), ChatColor.translateAlternateColorCodes('&', ChatColorConfig.get().getString("guiTitle"))), p, ChatColorConfig.get(), "c");
            GUI(Bukkit.createInventory(null, NameColorConfig.get().getInt("guiSize"), ChatColor.translateAlternateColorCodes('&', NameColorConfig.get().getString("guiTitle"))), p, NameColorConfig.get(), "n");
        }

    }

    public static void GUI(Inventory gui, Player p, FileConfiguration config, String ConfigKey)
    {
        Map<Integer, ColorData> playerColorMap = new HashMap<>();

        for (String key : config.getConfigurationSection("items").getKeys(false))
        {
            int slot = Integer.parseInt(key);
            ItemStack itemStack;
            String data = config.getString("items." + key + ".data");
            String name = ChatColor.translateAlternateColorCodes('&', config.getString("items." + key + ".name"));

            if (p.hasPermission(config.getString("items." + key + ".permission"))) {
                Material material = Material.valueOf(config.getString("items." + key + ".material"));

                itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(name);
                itemMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&eClick to Select")));
                itemStack.setItemMeta(itemMeta);

                playerColorMap.put(slot, new ColorData(material, data, name));
            }else
            {
                Material material = Material.valueOf(config.getString("color-unavailable.material"));

                itemStack = new ItemStack(material);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(name);
                itemMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&cUnavailable")));
                itemStack.setItemMeta(itemMeta);
            }
            gui.setItem(slot, itemStack);
        }

        if (Objects.equals(ConfigKey, "c")) {
           cgui.put(p.getName(), gui);
           ccolorMap.put(p.getName(), playerColorMap);}
        else if (Objects.equals(ConfigKey, "n")) {
            ngui.put(p.getName(), gui);
            ncolorMap.put(p.getName(), playerColorMap);}
    }

    public static class ColorData {
        private Material material;
        public String data;
        public String name;

        public ColorData(Material material, String data, String name) {
            this.material = material;
            this.data = data;
            this.name = name;
        }

        public boolean isRainbow() {
            return data.contains("%");
        }
    }
}

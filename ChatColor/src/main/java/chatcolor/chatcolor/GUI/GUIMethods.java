package chatcolor.chatcolor.GUI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Map;

public class GUIMethods
{
    public static void updateGUI(Player player, int selectedSlot, Inventory gui, Map<Integer, createGUI.ColorData> playerColorMap) {
        for (int slot : playerColorMap.keySet()) {
            ItemStack itemStack = gui.getItem(slot);
            if (slot == selectedSlot) {
                itemStack.setItemMeta(getActiveItemMeta(itemStack.getItemMeta()));
            } else {
                itemStack.setItemMeta(getInactiveItemMeta(itemStack.getItemMeta()));
            }
            gui.setItem(slot, itemStack);
        }
        player.updateInventory();
    }

    private static ItemMeta getActiveItemMeta(ItemMeta itemMeta) {
        itemMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&aSelected")));
        return itemMeta;
    }

    private static ItemMeta getInactiveItemMeta(ItemMeta itemMeta) {
        itemMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&eClick to Select")));
        return itemMeta;
    }
}

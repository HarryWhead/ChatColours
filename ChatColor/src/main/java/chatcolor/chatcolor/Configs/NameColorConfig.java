package chatcolor.chatcolor.Configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class NameColorConfig
{
    private static File file;
    private static FileConfiguration customFile;

    // finds or generates the custom config files
    public static void setup()
    {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("ChatColor").getDataFolder(), "NameColorGUI.yml");

        if(!file.exists())
        {
            try{
                file.createNewFile();
            }catch(IOException e){

            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch(IOException e){
            System.out.println("couldn't save file");;
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}

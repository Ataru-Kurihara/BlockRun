package game.blockrun;

import game.blockrun.Worlds.BlockRunGame;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blockrun extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new BlockRunGame(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

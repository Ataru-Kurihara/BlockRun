package game.blockrun.Utils;

import game.blockrun.Blockrun;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerUtil implements Listener {
    public static void onSettingPlayer(Player player) {
        player.setFoodLevel(20);
        player.setHealth(20.0);
        player.setFlying(false);
        player.getWorld().setPVP(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.setPlayerWeather(WeatherType.CLEAR);
    }
}

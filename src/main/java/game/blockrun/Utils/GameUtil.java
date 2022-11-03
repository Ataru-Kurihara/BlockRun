package game.blockrun.Utils;

import game.blockrun.Blockrun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class GameUtil implements Listener {
    public static void countDown(World world, List<Player> players, Blockrun plugin) {
        for (Player player: players) {
            player.sendTitle("3", "", 20,20,20);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("2","",20,20,20);
                }
            }, 20L);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("1","",20,20,20);
                }
            }, 40L);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("Start!!","",20,20,20);
                }
            }, 60L);
        }
    }


}

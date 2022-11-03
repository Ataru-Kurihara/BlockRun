package game.blockrun.Utils;

import game.blockrun.Blockrun;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class WorldUtil {


    //ブロックを置けないようにする
    @EventHandler
    public static void onBlockExplode(BlockExplodeEvent e,String worldName) {
        if (e.getBlock().getWorld().getName().equals(worldName))e.setCancelled(true);
    }

    //ブロックを壊せないようにする
    @EventHandler
    public static void onSetBlock(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }
}

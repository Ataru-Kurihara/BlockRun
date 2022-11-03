package game.blockrun.Utils;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class StageUtil {
    public static void allFloors(int y, World world) {
        Location location = new Location(world,105,y,105);
        for (int i=0; i<5; i++) {
            location.add(0,0,1);
            for (int j = 0; j < 5; j++) {
                location.add(1,0,0);
                world.getBlockAt(location).setType(Material.TNT);
            }
            location.add(-5,0,0);
        }
    }
    public static void ClearFloors(int y, World world) {
        Location location = new Location(world,110,y,110);
        for (int i=0; i<5; i++) {
            location.add(0,0,1);
            for (int j = 0; j < 5; j++) {
                location.add(1,0,0);
                world.getBlockAt(location).setType(Material.AIR);
            }
            location.add(-5,0,0);
        }
    }
    public static void damageFloors(World world) {
        Location location = new Location(world, 100,3,100);
        for (int i = 0; i < 20; i++) {
            location.add(0,0,1);
            for (int j = 0; j < 20; j++) {
                location.add(1,0,0);
                world.getBlockAt(location).setType(Material.LAVA);
            }
            location.add(-20,0,0);
        }
        location.add(0,0,-20);
    }

}

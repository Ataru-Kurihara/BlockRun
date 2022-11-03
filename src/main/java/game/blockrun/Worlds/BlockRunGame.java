package game.blockrun.Worlds;

import game.blockrun.Blockrun;
import game.blockrun.Utils.GameUtil;
import game.blockrun.Utils.PlayerUtil;
import game.blockrun.Utils.StageUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockRunGame implements Listener {
    private Blockrun plugin;
    public String worldName = "br";
    World world;
    public Location startPlace;
    public Player winner;
    public boolean isPlaying = false;
    public ArrayList<Player> PlayerList = new ArrayList<>();
    public BlockRunGame(Blockrun plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.startPlace = new Location(Bukkit.getWorld(this.worldName), 1000,5,1000);
        this.world = Bukkit.getWorld(this.worldName);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (this.world != e.getEntity().getWorld())return;
        Player player = (Player) e.getEntity();
        player.setFireTicks(0);
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)e.setCancelled(true);
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)e.setCancelled(true);
        if (e.getCause() == EntityDamageEvent.DamageCause.LAVA)e.setCancelled(true);
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)e.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent e) {
        String worldName = e.getEntity().getWorld().getName();
        if (worldName.equals(this.worldName))e.setCancelled(true);
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        if (this.world != e.getPlayer().getWorld())return;
        Player player = e.getPlayer();
        e.getPlayer().sendTitle("Welcome to BlockRun","",20,40,20);
        player.getInventory().clear();
        player.teleport(this.startPlace);
        ItemStack itemStack = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("ゲームスタート");
        player.getInventory().addItem(itemStack);
        PlayerUtil.onSettingPlayer(player);
        player.sendMessage("木材を右クリックするとゲームが始まるよ！");
        PlayerList.add(e.getPlayer());
        for (int i=0; i<PlayerList.size(); i++) {
            Player p = PlayerList.get(i);
            if (p.getUniqueId() == player.getUniqueId()) {
                PlayerList.remove(i);
                player.setGameMode(GameMode.SPECTATOR);
                break;
            }
        }
        StageUtil.ClearFloors(50,world);
        StageUtil.ClearFloors(40,world);
        StageUtil.ClearFloors(30,world);
        StageUtil.ClearFloors(20,world);
        StageUtil.ClearFloors(10,world);
    }

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent e) {
        if (this.world != e.getPlayer().getWorld())return;
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        assert item != null;
        if (item.getType() == Material.IRON_AXE) {
            int PlayerCount = player.getWorld().getPlayers().size();
            if (isPlaying) {
                player.sendMessage("ゲームが終わるまで待ってね！");
            } else {
                if (PlayerCount >= 1) {
                    isPlaying = true;
                    this.startGame();
                    this.countDown();
                    player.sendTitle("GameStart", "ゲームスタート",20,20,20);
                    player.sendMessage("移動するよ");
                } else {
                    isPlaying = false;
                    player.sendMessage("2人まで待ってね！");
                }
            }
        }
        StageUtil.allFloors(50,world);
        StageUtil.allFloors(40,world);
        StageUtil.allFloors(30,world);
        StageUtil.allFloors(20,world);
        StageUtil.allFloors(10,world);
        StageUtil.damageFloors(world);
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (this.world != e.getPlayer().getWorld())return;
        Location location = e.getPlayer().getLocation().clone().subtract(0, +1,0);
        Player player = e.getPlayer();
        Block block = location.getBlock();
        if (block.getType() == Material.TNT) {
            this.world.getBlockAt(location).setType(Material.AIR);
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {
                    if (block.getType() == Material.TNT) {
                        player.sendMessage(block.getType().name());
                        world.getBlockAt(location).setType(Material.AIR);
                    }
                }
            }, 20L);
        }
    }
    public void startGame() {
        List<Player> players = world.getPlayers();
        for (Player player: players) {
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {player.teleport(new Location(world,107,67,108));
                }
            }, 20L);
        }
    }
    public void gameClear() {
        World world = Bukkit.getWorld(worldName);
        List<Player> players = world.getPlayers();
        for (Player player: players) {
            String winnerName = this.winner.getDisplayName();
            player.sendTitle("GameWin", winnerName+"が勝利しました",40,40,40);
        }
        isPlaying = false;
    }

    @EventHandler
    public void gameOver(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (this.world != e.getPlayer().getWorld())return;
        Location location = e.getPlayer().getLocation().clone().subtract(0,0,0);
        if (location.getY() <= 3) {
            if (this.playerCheck() == 1) {
                this.gameClear();
            }
            this.playerCheck();
            player.sendTitle("GameOver", "ゲームオーバー",20,20,20);
            player.teleport(startPlace);

        }
    }

    public int playerCheck() {
        World world = Bukkit.getWorld(worldName);
        List<Player> players = world.getPlayers();
        int safePlayerCount = 0;
        int safePlayerIndex = 0;
        int i = 0;
        for (Player player: players) {
            double y = player.getLocation().getY();
            if (y <= 50) {
                safePlayerCount = safePlayerCount + 1;
                safePlayerIndex = i;
            }
            i = i + 1;
        }
        if (safePlayerCount == 1) {
            Player player = players.get(safePlayerIndex);

            this.winner = player;
        }
        return safePlayerCount;
    }

    public void countDown() {
        List<Player> players = world.getPlayers();
        for (Player player: players) {
            player.sendTitle("3", "", 20,20,20);
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("2","",20,20,20);
                }
            }, 20L);
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("1","",20,20,20);
                }
            }, 20L);
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                @Override
                public void run() {
                    player.sendTitle("Start!!","",20,20,20);
                }
            }, 20L);
        }
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        Player player = e.getEntity();
        player.teleport(startPlace);
    }

}

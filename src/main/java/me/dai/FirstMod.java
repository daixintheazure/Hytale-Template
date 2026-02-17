package me.dai;

import com.hypixel.hytale.server.core.command.system.CommandRegistry;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import me.dai.commands.FirstCommand;
import me.dai.config.BlockBreakConfig;
import me.dai.systems.BlockBreakEventSystem;
import me.dai.systems.BlockDamageEventSystem;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class FirstMod extends JavaPlugin {

    private final Config<BlockBreakConfig> config;

    public FirstMod(@NonNullDecl JavaPluginInit init) {
        super(init);
        this.config = this.withConfig("BlockBreakConfig", BlockBreakConfig.CODEC);
    }

    @Override
    protected void setup() {
        this.config.save();
        // Initialize everything
        CommandRegistry commandRegistry = this.getCommandRegistry();
        commandRegistry.registerCommand(new FirstCommand());

        // Initialize Event System
        this.getEntityStoreRegistry().registerSystem(new BlockBreakEventSystem(config));

        this.getEntityStoreRegistry().registerSystem(new BlockDamageEventSystem());
    }
}
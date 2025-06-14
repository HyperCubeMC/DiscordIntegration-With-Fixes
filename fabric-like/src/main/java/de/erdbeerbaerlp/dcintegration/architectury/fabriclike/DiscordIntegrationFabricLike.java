package de.erdbeerbaerlp.dcintegration.architectury.fabriclike;

import de.erdbeerbaerlp.dcintegration.architectury.DiscordIntegrationMod;
import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.compat.DynmapListener;
import de.erdbeerbaerlp.dcintegration.common.storage.Configuration;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.server.MinecraftServer;

public final class DiscordIntegrationFabricLike {
    public static void init() {
        DiscordIntegrationMod.init();
        if (!Configuration.instance().general.botToken.equals("INSERT BOT TOKEN HERE")) {
            ServerLifecycleEvents.SERVER_STARTED.register(DiscordIntegrationMod::serverStarted);
            ServerLifecycleEvents.SERVER_STARTING.register(DiscordIntegrationMod::serverStarting);
            ServerLifecycleEvents.SERVER_STARTING.register(DiscordIntegrationFabricLike::serverStarting);
            ServerLifecycleEvents.SERVER_STOPPED.register(DiscordIntegrationMod::serverStopped);
            ServerLifecycleEvents.SERVER_STOPPING.register(DiscordIntegrationMod::serverStopping);
            CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, selection) -> DiscordIntegrationMod.registerCommands(dispatcher)));
        } else {
            DiscordIntegration.LOGGER.error("Please check the config file and set an bot token");
        }
    }

    private static void serverStarting(MinecraftServer minecraftServer) {
        if (FabricLoaderImpl.INSTANCE.isModLoaded("dynmap")) {
            new DynmapListener().register();
        }
    }
}

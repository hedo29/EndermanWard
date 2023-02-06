package com.hedonic.endermanward;

import com.hedonic.endermanward.block.BlockRegistrar;
import com.hedonic.endermanward.eventhandler.EndermanEventHandler;
import com.hedonic.endermanward.eventhandler.WardEventHandler;
import com.hedonic.endermanward.item.ItemRegistrar;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("endermanward")
public class EndermanWard
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    // constants used by other classes
    public static final String MOD_ID = "endermanward"; // this must match mods.toml
    public static final String RESOURCE_PREFIX = MOD_ID + ":";

    private static final EndermanEventHandler emEventHandler = new EndermanEventHandler();

    private static final WardEventHandler wEventHandler = new WardEventHandler();

    public EndermanWard()
    {
        BlockRegistrar.register();
        ItemRegistrar.register();

        // register event listeners that don't use the event bus
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modConfig);

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(emEventHandler);
        MinecraftForge.EVENT_BUS.register(wEventHandler);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, EndermanWardConfig.SERVER_SPEC);

    }

    public void modConfig(ModConfigEvent event)
    {
        ModConfig config = event.getConfig();
        if (config.getSpec() == EndermanWardConfig.SERVER_SPEC)
        {
            EndermanWardConfig.refreshServer();
        }
    }

    public static void logMessageInfo(String message)
    {
        if (EndermanWardConfig.logLevel.equalsIgnoreCase("all") || EndermanWardConfig.logLevel.equalsIgnoreCase("info"))
        {
            EndermanWard.LOGGER.info(message);
        }
    }

    public static void logMessageWarn(String message)
    {
        if (EndermanWardConfig.logLevel.equalsIgnoreCase("all") || EndermanWardConfig.logLevel.equalsIgnoreCase("info") || EndermanWardConfig.logLevel.equalsIgnoreCase("warn"))
        {
            EndermanWard.LOGGER.warn(message);
        }
    }

    public static void logMessageError(String message)
    {
        EndermanWard.LOGGER.error(message);
    }
}


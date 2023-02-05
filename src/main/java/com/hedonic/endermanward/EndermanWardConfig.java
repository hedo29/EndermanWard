package com.hedonic.endermanward;

import net.minecraftforge.fml.ModList;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

public class EndermanWardConfig {

    public static final int DEFAULT_CONFIG_VERSION = 4;

    public static final int DEFAULT_WARD_RADIUS = 4;

    public static final boolean DEFAULT_ALLOW_TELEPORT = true;

    public static final boolean DEFAULT_ALLOW_GRIEF = false;

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static
    {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    // server options
    public static int configVersion = DEFAULT_CONFIG_VERSION;

    public static int wardRadius = DEFAULT_WARD_RADIUS;

    public static boolean allowTeleporting = DEFAULT_ALLOW_TELEPORT;

    public static boolean allowGriefing = DEFAULT_ALLOW_GRIEF;

    public static String logLevel = "error";

    public static class ServerConfig
    {
        public final ConfigValue<Integer> configVersion;

        public final ConfigValue<Integer> wardRadius;

        public final ConfigValue<Boolean> allowTeleporting;

        public final ConfigValue<Boolean> allowGriefing;

        public final ConfigValue<String> logLevel;

        ServerConfig(ForgeConfigSpec.Builder builder)
        {

            // list of server options and comments
            builder.comment("Options for mod behavior.").push("general");
            configVersion = builder.comment("You shouldn't manually change the version number.").translation("config.endermanward.configVersion").define("configVersion", DEFAULT_CONFIG_VERSION);
            wardRadius = builder.comment("Sets the range of where enderman behavior will be prevented in a square distance around the ward.").translation("config.endermanward.wardRadius").define("wardRadius", 64);
            allowTeleporting = builder.comment("If FALSE, endermen will be unable to teleport within the range of the ward.").translation("config.endermanward.allowTeleporting").define("allowTeleporting", true);
            allowGriefing = builder.comment("If FALSE, endermen will be unable to pick up nor place blocks within the range of the ward.").translation("config.endermanward.allowGriefing").define("allowGriefing", false);
            logLevel = builder.comment("Can be used to limit log spam. Can be set to 'all', 'warn', or 'error'.").translation("config.endermanward.logLevel").define("loglevel", "true");
            builder.pop();
        }
    }

    public static void refreshServer()
    {
        // refresh the server config
        configVersion = SERVER.configVersion.get();
        wardRadius = SERVER.wardRadius.get();
        allowTeleporting = SERVER.allowTeleporting.get();
        allowGriefing = SERVER.allowGriefing.get();
        logLevel = SERVER.logLevel.get();

    }


    public static boolean isModInstalled(String namespace)
    {
        return ModList.get().isLoaded(namespace);
    }
}

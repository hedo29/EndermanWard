package com.hedonic.endermanward;

import net.minecraftforge.fml.ModList;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class EndermanWardConfig {

    public static final int DEFAULT_CONFIG_VERSION = 4;

    public static final int DEFAULT_WARD_RADIUS = 64;

    public static final boolean DEFAULT_ALLOW_TELEPORT = true;

    public static final boolean DEFAULT_ALLOW_GRIEF = false;

    public static final boolean DEFAULT_TELEPORT_FROM_WARD = false;

    public static final boolean DEFAULT_DROP_ON_DESTROY = false;

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

    public static boolean teleportOnWardDetection = DEFAULT_TELEPORT_FROM_WARD;

    public static boolean dropOnDestroy = DEFAULT_DROP_ON_DESTROY;

    public static String logLevel = "warn";

    public static class ServerConfig
    {
        public final ConfigValue<Integer> configVersion;

        public final ConfigValue<Integer> wardRadius;

        public final ConfigValue<Boolean> allowTeleporting;

        public final ConfigValue<Boolean> allowGriefing;

        public final ConfigValue<Boolean> teleportOnWardDetection;

        public final ConfigValue<Boolean> dropOnDestroy;

        public final ConfigValue<String> logLevel;

        ServerConfig(ForgeConfigSpec.Builder builder)
        {

            // list of server options and comments
            builder.comment("Options for mod behavior.").push("general");
            configVersion = builder.comment("You shouldn't manually change the version number.").translation("config.endermanward.configVersion").define("configVersion", DEFAULT_CONFIG_VERSION);
            wardRadius = builder.comment("Sets the range of where enderman behavior will be prevented in a square distance around the ward.").translation("config.endermanward.wardRadius").define("wardRadius", 64);
            allowTeleporting = builder.comment("If FALSE, endermen will be unable to teleport within the range of the ward.").translation("config.endermanward.allowTeleporting").define("allowTeleporting", true);
            allowGriefing = builder.comment("If FALSE, endermen will be unable to pick up nor place blocks within the range of the ward.").translation("config.endermanward.allowGriefing").define("allowGriefing", false);
            teleportOnWardDetection = builder.comment("If TRUE, endermen will be sent to the maximum range of the ward upon doing something they weren't allowed to do.").translation("config.endermanward.teleportOnWardDetection").define("teleportOnWardDetection", false);
            dropOnDestroy = builder.comment("If TRUE, wards will drop as an item when broken. If FALSE, blocks will not drop items on being destroyed and will have to be recrafted.").translation("config.endermanward.dropOnDestroy").define("dropOnDestroy", false);
            logLevel = builder.comment("Can be used to limit log spam. Can be set to 'all', 'info', 'warn', or 'error'.").translation("config.endermanward.logLevel").define("loglevel", "warn");
            builder.pop();
        }
    }

    public static void refreshServer()
    {
        // refresh the server config
        configVersion = SERVER.configVersion.get();
        wardRadius = SERVER.wardRadius.get();
        if(wardRadius > 160) {
            EndermanWard.logMessageWarn("Ward radius is greater than 10 chunks. This exceeds normal simulation distance. You should only increase this beyond this amount" +
                    "if you're using a higher simulation distance.");
        }
        if(wardRadius < 1) {
            EndermanWard.logMessageWarn("Ward radius set to an ineffective value. Setting to effective minimum of 1.");
            wardRadius = 1;
            SERVER.wardRadius.set(1);
        }
        allowTeleporting = SERVER.allowTeleporting.get();
        allowGriefing = SERVER.allowGriefing.get();
        teleportOnWardDetection = SERVER.teleportOnWardDetection.get();
        dropOnDestroy = SERVER.dropOnDestroy.get();
        logLevel = SERVER.logLevel.get();

    }
}

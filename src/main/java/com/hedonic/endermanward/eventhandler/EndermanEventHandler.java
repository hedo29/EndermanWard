package com.hedonic.endermanward.eventhandler;

import com.hedonic.endermanward.EndermanWardConfig;
import com.hedonic.endermanward.WardData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class EndermanEventHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onEndermanGrief(EntityMobGriefingEvent event) {
        if (event.getEntity() instanceof EnderMan) {
            if (checkIfNearWard((EnderMan) event.getEntity())) {
                if (!EndermanWardConfig.allowGriefing) {
                    event.setResult(Event.Result.DENY);
                    if (EndermanWardConfig.teleportOnWardDetection)
                        teleportAwayFromWard((EnderMan) event.getEntity());
                } else {
                }
            }
        }
    }

    @SubscribeEvent
    public void onEndermanTeleport(EntityTeleportEvent.EnderEntity event) {
        if (event.getEntity() instanceof EnderMan) {
            if (checkIfNearWard((EnderMan) event.getEntity())) {
                if (!EndermanWardConfig.allowTeleporting) {
                    event.setCanceled(true);
                    if (EndermanWardConfig.teleportOnWardDetection)
                        teleportAwayFromWard((EnderMan) event.getEntity());
                } else {
                }
            }
        }
    }

    private boolean checkIfNearWard(EnderMan enderMan) {
        ServerLevel level = (ServerLevel) enderMan.level;

        double cur_x = enderMan.getX();
        double cur_y = enderMan.getX();
        double cur_z = enderMan.getX();


        for(BlockPos wardPos: WardData.get(level).getWardList()) {
            if(wardPos.getX() - 64 < cur_x || wardPos.getX() + 64 > cur_x
                || wardPos.getY() - 64 < cur_y || wardPos.getY() + 64 > cur_y
                || wardPos.getZ() - 64 < cur_z || wardPos.getZ() + 64 > cur_z) {
                return true;
            }
        };

        return false;
    }

    private void teleportAwayFromWard(EnderMan enderMan) {
        Random random = new Random();

        int vector_x = 0;
        int vector_z = 0;

        double new_x = enderMan.getX();
        double new_z = enderMan.getZ();

        while(vector_x == 0 && vector_z == 0) {
            vector_x = random.nextInt(2);
            vector_z = random.nextInt(2);
        }

        if(vector_x == 1) {
            new_x += 64;
        } else if(vector_x == 2) {
            new_x -= 64;
        }

        if(vector_z == 1) {
            new_z += 64;
        } else if(vector_z == 2) {
            new_z -= 64;
        }

        enderMan.teleportTo(new_x, enderMan.getY(), new_z);
    }
}

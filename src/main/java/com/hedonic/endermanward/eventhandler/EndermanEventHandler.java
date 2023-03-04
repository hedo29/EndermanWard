package com.hedonic.endermanward.eventhandler;

import com.hedonic.endermanward.EndermanWardConfig;
import com.hedonic.endermanward.WardData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class EndermanEventHandler {

    @SubscribeEvent
    public void onEndermanGrief(EntityMobGriefingEvent event) {
        if (!event.getEntity().getLevel().isClientSide) {
            if (event.getEntity() instanceof EnderMan) {
                if (checkIfNearWard((EnderMan) event.getEntity())) {
                    if (!EndermanWardConfig.allowGriefing) {
                        event.setResult(Event.Result.DENY);
                        if (EndermanWardConfig.teleportOnWardDetection)
                            teleportAwayFromWard((EnderMan) event.getEntity());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEndermanTeleport(EntityTeleportEvent.EnderEntity event) {
        if (!event.getEntity().getLevel().isClientSide) {
            if (event.getEntity() instanceof EnderMan) {
                if (checkIfNearWard((EnderMan) event.getEntity())) {
                    if (!EndermanWardConfig.allowTeleporting) {
                        event.setCanceled(true);
                        if (EndermanWardConfig.teleportOnWardDetection)
                            teleportAwayFromWard((EnderMan) event.getEntity());
                    }
                }
            }
        }
    }

    private boolean checkIfNearWard(EnderMan enderMan) {
        int range = EndermanWardConfig.wardRadius;
        ServerLevel level = (ServerLevel) enderMan.level;

        double cur_x = enderMan.getX();
        double cur_y = enderMan.getX();
        double cur_z = enderMan.getX();


        for(BlockPos wardPos: WardData.get(level).getWardList()) {
            if(wardPos.getX() - range < cur_x || wardPos.getX() + range > cur_x
                || wardPos.getY() - range < cur_y || wardPos.getY() + range > cur_y
                || wardPos.getZ() - range < cur_z || wardPos.getZ() + range > cur_z) {
                return true;
            }
        };

        return false;
    }

    private void teleportAwayFromWard(EnderMan enderMan) {

        int range = EndermanWardConfig.wardRadius;
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
            new_x += range;
        } else if(vector_x == 2) {
            new_x -= range;
        }

        if(vector_z == 1) {
            new_z += range;
        } else if(vector_z == 2) {
            new_z -= range;
        }

        enderMan.teleportTo(new_x, enderMan.getY(), new_z);
    }
}

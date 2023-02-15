package com.hedonic.endermanward.eventhandler;

import com.hedonic.endermanward.WardData;
import com.hedonic.endermanward.block.BlockEndermanWard;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WardEventHandler {

    @SubscribeEvent
    public void onWardPlaced(BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().getBlock() instanceof BlockEndermanWard) {
            ServerLevel level = (ServerLevel) event.getLevel();
            WardData.get(level).addToList(event.getPos());
        }

    }

}

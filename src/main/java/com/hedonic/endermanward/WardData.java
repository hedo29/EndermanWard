package com.hedonic.endermanward;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
public class WardData extends SavedData {

    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<BlockPos> wardList = new ArrayList<>();

    public ArrayList<BlockPos> getWardList() {
        return wardList;
    }

    public void removeFromList(BlockPos block) {
        try {
           for(BlockPos ward: wardList) {
               if(ward.getX() == block.getX() && ward.getY() == block.getY() && ward.getZ() == block.getZ()) {
                   wardList.remove(ward);
               }
           }
        }
        catch  (Exception e) {
            LOGGER.error("Tried to remove ward from ward list at " + block.getX() +  ", " + block.getY() + ", " + block.getZ() + ",  but it wasn't" +
                    "registered as a ward. This can happen if you had a ward placed before the mod actually worked.");
        }
        finally {
            this.setDirty();
        }

    }

    public void addToList(BlockPos block) {
        wardList.add(block);
        this.setDirty();
    }

    @Nonnull
    public static WardData get(Level level) {

        if(level.isClientSide) {
            throw new RuntimeException("No need to process data client-side");
        }

        DimensionDataStorage dimensionData = ((ServerLevel) level).getDataStorage();

        return dimensionData.computeIfAbsent(WardData::load, WardData::create, "wards");
    }

    //Create new list if data doesn't exist.
    public static WardData create() {
        return new WardData();
    }

    //Recreate ward list from data.
    public static WardData load(CompoundTag tag) {
        LOGGER.debug("Recreate ward list from data.");
        ListTag wards = tag.getList("wards", tag.getId());

        WardData data = create();
        data.wardList = new ArrayList<>();

        //Don't NPE on no wards being placed
        if(wards.isEmpty()) {
            data.wardList = new ArrayList<>();
        }

        wards.forEach(rawTag -> {

            CompoundTag wardTag = (CompoundTag)rawTag;
            data.wardList.add(new BlockPos(wardTag.getInt("x"), wardTag.getInt("y"), wardTag.getInt("z")));
        });
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        LOGGER.debug("Saving new ward placements.");
        ListTag wards = new ListTag();
        wardList.forEach(ward -> {
            CompoundTag wardTag = new CompoundTag();
            wardTag.putInt("x", ward.getX());
            wardTag.putInt("y", ward.getY());
            wardTag.putInt("z", ward.getZ());
            wards.add(wardTag);
        });

        tag.put("wards", wards);
        return tag;
    }
}

package com.rae.creatingspace.utilities;

import com.rae.creatingspace.configs.CSConfigs;
import com.rae.creatingspace.init.worldgen.DimensionInit;
import com.rae.creatingspace.server.entities.RocketContraptionEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CustomTeleporter implements ITeleporter {
    protected final ServerLevel level;

    public CustomTeleporter(ServerLevel level) {
        this.level = level;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        double height;
        //use the arrivalHeight instead
        if (CSDimensionUtil.gravity(destWorld.dimensionTypeId())==0f){
            height = CSConfigs.COMMON.spaceSpawnHeight.get();
        }else{
            height = CSConfigs.COMMON.planetSpawnHeight.get();
        }
        Vec3 position;
        if ( entity instanceof RocketContraptionEntity rocketContraptionEntity){

            position = new Vec3(
                    rocketContraptionEntity.rocketEntryCoordinate.getX(),
                    height,
                    rocketContraptionEntity.rocketEntryCoordinate.getZ());

        }
        else {
            position = new Vec3(
                    entity.getX(),
                    height,
                    entity.getZ());
        }
        return new PortalInfo(position, Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    @Override
    public boolean isVanilla() {
        return false;
    }
}

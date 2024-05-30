package com.rae.creatingspace.init;

import com.rae.creatingspace.CreatingSpace;
import com.rae.creatingspace.server.design.ExhaustPackType;
import com.rae.creatingspace.server.design.PowerPackType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class MiscInit {
    public static final DeferredRegister<ExhaustPackType> DEFERRED_EXHAUST_PACK_TYPE =
            DeferredRegister.create(Keys.EXHAUST_PACK_TYPE, CreatingSpace.MODID);
    public static final Supplier<IForgeRegistry<ExhaustPackType>> EXHAUST_PACK_TYPE = DEFERRED_EXHAUST_PACK_TYPE.makeRegistry(
            () -> new RegistryBuilder<ExhaustPackType>().allowModification().disableSaving()
                    .dataPackRegistry(ExhaustPackType.DIRECT_CODEC, ExhaustPackType.DIRECT_CODEC));
    public static final DeferredRegister<PowerPackType> DEFERRED_POWER_PACK_TYPE =
            DeferredRegister.create(Keys.POWER_PACK_TYPE, CreatingSpace.MODID);
    public static final Supplier<IForgeRegistry<PowerPackType>> POWER_PACK_TYPE = DEFERRED_POWER_PACK_TYPE.makeRegistry(
            () -> new RegistryBuilder<PowerPackType>().allowModification().disableSaving().dataPackRegistry(
                    PowerPackType.DIRECT_CODEC, PowerPackType.DIRECT_CODEC));

    /**
     * @return a client side sync version of the DEFERRED_EXHAUST_PACK_TYPE
     */
    @OnlyIn(Dist.CLIENT)
    public static Registry<ExhaustPackType> getSyncedExhaustPackRegistry() {
        return Minecraft.getInstance().getConnection().registryAccess().registry(Keys.EXHAUST_PACK_TYPE)
                .orElseThrow();
    }

    /**
     *
     * @return a client side sync version of the DEFERRED_POWER_PACK_TYPE
     */
    @OnlyIn(Dist.CLIENT)
    public static Registry<PowerPackType> getSyncedPowerPackRegistry() {
        return Minecraft.getInstance().getConnection().registryAccess().registry(Keys.POWER_PACK_TYPE)
                .orElseThrow();
    }

    public static class Keys {
        public static final ResourceKey<Registry<ExhaustPackType>> EXHAUST_PACK_TYPE =
                ResourceKey.createRegistryKey(new ResourceLocation("exhaust_pack_type"));
        public static final ResourceKey<Registry<PowerPackType>> POWER_PACK_TYPE =
                ResourceKey.createRegistryKey(new ResourceLocation("power_pack_type"));


    }

    public static void register(IEventBus modEventBus) {
        DEFERRED_EXHAUST_PACK_TYPE.register(modEventBus);
        DEFERRED_POWER_PACK_TYPE.register(modEventBus);
    }

}

package com.rae.creatingspace.server.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

public class RocketPlumeParticleData implements ParticleOptions,ICustomParticleDataWithSprite<RocketPlumeParticleData>

    {

        public static final Codec<RocketPlumeParticleData> CODEC = RecordCodecBuilder.create(i ->
                i.group(
                                Codec.INT.fieldOf("x").forGetter(p -> p.posX),
                                Codec.INT.fieldOf("y").forGetter(p -> p.posY),
                                Codec.INT.fieldOf("z").forGetter(p -> p.posZ))
                        .apply(i, RocketPlumeParticleData::new));

        public static final ParticleOptions.Deserializer<RocketPlumeParticleData> DESERIALIZER = new ParticleOptions.Deserializer<RocketPlumeParticleData>() {
            public RocketPlumeParticleData fromCommand(ParticleType<RocketPlumeParticleData> particleTypeIn, StringReader reader)
                    throws CommandSyntaxException {
                reader.expect(' ');
                int x = reader.readInt();
                reader.expect(' ');
                int y = reader.readInt();
                reader.expect(' ');
                int z = reader.readInt();
                return new RocketPlumeParticleData(x, y, z);
            }

            public RocketPlumeParticleData fromNetwork(ParticleType<RocketPlumeParticleData> particleTypeIn, FriendlyByteBuf buffer) {
                return new RocketPlumeParticleData(buffer.readInt(), buffer.readInt(), buffer.readInt());
            }
        };

        final int posX;
        final int posY;
        final int posZ;

	public RocketPlumeParticleData(Vec3i pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

	public RocketPlumeParticleData(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

	public RocketPlumeParticleData() {
        this(0, 0, 0);
    }

        @Override
        public ParticleType<?> getType() {
        return AllParticleTypes.AIR_FLOW.get();
    }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(posX);
        buffer.writeInt(posY);
        buffer.writeInt(posZ);
    }

        @Override
        public String writeToString() {
        return String.format(Locale.ROOT, "%s %d %d %d", AllParticleTypes.AIR_FLOW.parameter(), posX, posY, posZ);
    }

        @Override
        public ParticleOptions.Deserializer<RocketPlumeParticleData> getDeserializer() {
        return DESERIALIZER;
    }

        @Override
        public Codec<RocketPlumeParticleData> getCodec(ParticleType<RocketPlumeParticleData> type) {
        return CODEC;
    }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ParticleEngine.SpriteParticleRegistration<RocketPlumeParticleData> getMetaFactory() {
        return PlumeParticle.Factory::new;
    }

    }
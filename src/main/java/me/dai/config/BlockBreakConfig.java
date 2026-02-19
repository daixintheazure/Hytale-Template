package me.dai.config;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class BlockBreakConfig {

    public static final BuilderCodec<BlockBreakConfig> CODEC = BuilderCodec.builder(BlockBreakConfig.class, BlockBreakConfig::new)
            .append(new KeyedCodec<>("AllowedBlock", BuilderCodec.STRING_ARRAY),
                    (blockBreakConfig, strings) -> blockBreakConfig.allowedBlocks = strings,
                    (BlockBreakConfig::getAllowedBlocks)).add()
            .build();

    public String[] getAllowedBlocks() {
        return allowedBlocks;
    }


    private String[] allowedBlocks = {"Soil_Dirt"};

}



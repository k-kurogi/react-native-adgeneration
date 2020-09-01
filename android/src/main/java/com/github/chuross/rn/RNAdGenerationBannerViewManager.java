package com.github.chuross.rn;

import android.view.View;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

public class RNAdGenerationBannerViewManager extends ViewGroupManager<RNAdGenerationBanner> {

    private static final int COMMAND_LOAD = 1;

    @Override
    public String getName() {
        return "RNAdGenerationBanner";
    }

    @Override
    protected RNAdGenerationBanner createViewInstance(ThemedReactContext reactContext) {
        return new RNAdGenerationBanner(reactContext);
    }

    @Override
    public void onDropViewInstance(RNAdGenerationBanner view) {
        view.destroy();
        super.onDropViewInstance(view);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
                .put(RNAdGenerationBanner.EVENT_TAG_ON_MEASURE, MapBuilder.of("registrationName", RNAdGenerationBanner.EVENT_TAG_ON_MEASURE))
                .build();
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.<String, Integer>builder()
                .put("load", COMMAND_LOAD)
                .build();
    }

    @Override
    public void receiveCommand(RNAdGenerationBanner root, int commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
        switch (commandId) {
            case COMMAND_LOAD:
                root.load();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @ReactProp(name = "locationId")
    public void setLocationId(RNAdGenerationBanner view, String locationId) {
        view.setLocationId(locationId);
    }

    @ReactProp(name = "bannerType")
    public void setBannerType(RNAdGenerationBanner view, String bannerType) {
        view.setBannerType(bannerType);
    }
    
    @ReactProp(name = "screenWidth")
    public void setScreenWidth(RNAdGenerationBanner view, int screenWidth) {
        view.setScreenWidth(screenWidth);
    }
    
    @Override
    public void addView(RNAdGenerationBanner parent, View child, int index) {
        throw new UnsupportedOperationException("RNAdGenerationBanner not allow children.");
    }

    @Override
    public void removeView(RNAdGenerationBanner parent, View view) {
        throw new UnsupportedOperationException("RNAdGenerationBanner not allow children.");
    }

}

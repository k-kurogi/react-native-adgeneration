package com.github.chuross.rn;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.socdm.d.adgeneration.ADG;
import com.socdm.d.adgeneration.ADGConsts;
import com.socdm.d.adgeneration.ADGListener;


public class RNAdGenerationBanner extends FrameLayout {

    public static final String EVENT_TAG_ON_MEASURE = "onMeasure";
    private ReactContext reactContext;
    private ADG adg;
    private int screenWidth = 0;
    private String bannerType;
    private Runnable measureRunnable = new Runnable() {
        @Override
        public void run() {
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY);
            measure(widthMeasureSpec, heightMeasureSpec);
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };
    

    public RNAdGenerationBanner(@NonNull Context context) {
        super(context);
        this.reactContext = (ReactContext) context;

        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        adg = new ADG(getContext());

        adg.setAdListener(new ADGListener() {
            @Override
            public void onReceiveAd() {
            }

            @Override
            public void onFailedToReceiveAd(ADGConsts.ADGErrorCode code) {
                super.onFailedToReceiveAd(code);

                switch (code) {
                    case EXCEED_LIMIT:
                    case NEED_CONNECTION:
                    case NO_AD:
                        break;
                    default:
                        if (adg != null) adg.start();
                        break;
                }
            }
        });

        addView(adg);
    }

    public void setLocationId(String locationId) {
        adg.setLocationId(locationId);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureRunnable);
    }

    /**
     * @param bannerType sp|rect|tablet|large
     */
    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
        
        if(this.screenWidth != 0){
            setBannerScale();
        }
    }
    /**
     * @param width screenWidth
     */
    public void setScreenWidth(int width) {
        this.screenWidth = width;
        setBannerScale();
    }

    public void load() {
        if (adg != null) adg.start();
    }

    public void destroy() {
        if (adg != null) adg.stop();
        adg = null;
    }

    private void setBannerScale(){
        float bannerWidth = 320;
        float bannerHeight = 50;

        if (this.bannerType.equalsIgnoreCase("SP")){
            bannerWidth = 320;
            bannerHeight = 50;
        }else if(this.bannerType.equalsIgnoreCase("RECT")){
            bannerWidth = 300;
            bannerHeight = 250;
        }else if(this.bannerType.equalsIgnoreCase("TABLET")){
            bannerWidth = 728;
            bannerHeight = 90;
        }else if(this.bannerType.equalsIgnoreCase("LARGE")){
            bannerWidth = 320;
            bannerHeight = 100;
        }
        float scale = this.screenWidth / bannerWidth;
        float height = bannerHeight *  scale;
        int intHeight = (int)height;
        adg.setAdFrameSize(ADG.AdFrameSize.FREE.setSize(this.screenWidth, intHeight));
        adg.setAdScale(scale);
        refreshBannerLayoutParams(ADG.AdFrameSize.FREE);
    }

    private Rect getBannerRect(ADG.AdFrameSize frameSize) {
        if (frameSize == null) return null;
        return new Rect(0, 0, (int) PixelUtil.toPixelFromDIP(frameSize.getWidth()), (int) PixelUtil.toPixelFromDIP(frameSize.getHeight()));
    }

    private void refreshBannerLayoutParams(ADG.AdFrameSize frameSize) {
        Rect bannerRect = getBannerRect(frameSize);
        adg.setLayoutParams(new LayoutParams(bannerRect.width(), bannerRect.height()));

        sendSizeChangedEvent(frameSize);
    }

    private void sendSizeChangedEvent(ADG.AdFrameSize frameSize) {
        WritableMap event = Arguments.createMap();
        event.putInt("width", frameSize.getWidth());
        event.putInt("height", frameSize.getHeight());

        sendEvent(EVENT_TAG_ON_MEASURE, event);
    }

    private void sendEvent(String eventTag, WritableMap event) {
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), eventTag, event);
    }

}

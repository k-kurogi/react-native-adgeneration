//
//  RNAdGenerationBanner.m
//  RNAdGeneration
//
//  Created by chuross on 2018/05/28.
//  Copyright © 2018年 Facebook. All rights reserved.
//
#import "RNAdGenerationBanner.h"
#import <ADG/ADG.h>

/**
 * Extension
 */
@interface RNAdGenerationBanner() <ADGManagerViewControllerDelegate>

@property (nonatomic) ADGManagerViewController *adg;
@property (weak, nonatomic) IBOutlet UIView *adView;
@property (nonatomic, weak) UIViewController *rootViewController;
@property (nonatomic, copy) NSString *locationid;

@end


/**
 * Implementation
 */
@implementation RNAdGenerationBanner : UIView

- (void)dealloc
{
    self.adg.delegate = nil;
    self.adg = nil;
}

- (void)load
{
    int intWidth = 320;
    int intHeight = 50;
    float scale = 1.0;
    float height = 50;
    if (self.locationId == nil) {
        return;
    }
    if (self.bannerType == nil) {
        return;
    }
    if (self.screenWidth == nil) {
        return;
    }else{
        intWidth = [self.screenWidth intValue];
    }
    
    NSMutableDictionary *params = @{}.mutableCopy;
    [params setObject:self.locationId forKey:@"locationid"];
    
    NSDictionary *event;
    if ([self.bannerType isEqualToString:@"sp"]) {
        [params setObject:@(kADG_AdType_Sp) forKey:@"adtype"];
        scale = (float)intWidth / 320;
        height = 50 * scale;
        intHeight = (int) height;
    }
    if ([self.bannerType isEqualToString:@"rect"]) {
        [params setObject:@(kADG_AdType_Rect) forKey:@"adtype"];
        scale = (float)intWidth / 300;
        height = 250 * scale;
        intHeight = (int) height;
    }
    if ([self.bannerType isEqualToString:@"large"]) {
        [params setObject:@(kADG_AdType_Large) forKey:@"adtype"];
        scale = (float)intWidth / 320;
        height = 100 * scale;
        intHeight = (int) height;
    }
    if ([self.bannerType isEqualToString:@"tablet"]) {
        [params setObject:@(kADG_AdType_Tablet) forKey:@"adtype"];
        scale = (float)intWidth / 728;
        height = 90 * scale;
        intHeight = (int) height;
    }
    
    event = @{ @"width": @(intWidth), @"height": @(intHeight) };
    if (self.onMeasure) {
        self.onMeasure(event);
    }
    
    NSLog(@"Succesed to receive an ad.");
    
    self.adg = [[ADGManagerViewController new] initWithLocationID:_locationId adType:kADG_AdType_Free rootViewController:_rootViewController];
    self.adg.adSize = CGSizeMake(intWidth, intHeight);
    self.adg.adScale = scale;
    [self.adg addAdContainerView:self];
    self.adg.delegate = self;
    [self.adg loadRequest];
}

- (void)ADGManagerViewControllerFailedToReceiveAd:(ADGManagerViewController *)adgManagerViewController code:(kADGErrorCode)code
{
    switch (code) {
        case kADGErrorCodeNeedConnection:
        case kADGErrorCodeExceedLimit:
        case kADGErrorCodeNoAd:
            break;
        default:
            [self.adg loadRequest];
            break;
    }
}

@end

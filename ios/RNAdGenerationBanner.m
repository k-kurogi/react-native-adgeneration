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
    if (self.locationId == nil) {
        return;
    }
    if (self.bannerType == nil) {
        return;
    }
    
    NSMutableDictionary *params = @{}.mutableCopy;
    [params setObject:self.locationId forKey:@"locationid"];
    
    NSDictionary *event;
    if ([self.bannerType isEqualToString:@"sp"]) {
        [params setObject:@(kADG_AdType_Sp) forKey:@"adtype"];
        event = @{ @"width": @(kADGAdSize_Sp_Width), @"height": @(kADGAdSize_Sp_Height) };
        self.adg = [[ADGManagerViewController alloc] initWithLocationID:_locationId adType:kADG_AdType_Sp rootViewController:_rootViewController];
    }
    if ([self.bannerType isEqualToString:@"rect"]) {
        [params setObject:@(kADG_AdType_Rect) forKey:@"adtype"];
        event = @{ @"width": @(kADGAdSize_Rect_Width), @"height": @(kADGAdSize_Rect_Height) };
        self.adg = [[ADGManagerViewController alloc] initWithLocationID:_locationId adType:kADG_AdType_Rect rootViewController:_rootViewController];
    }
    if ([self.bannerType isEqualToString:@"large"]) {
        [params setObject:@(kADG_AdType_Large) forKey:@"adtype"];
        event = @{ @"width": @(kADGAdSize_Large_Width), @"height": @(kADGAdSize_Large_Height) };
    }
    if ([self.bannerType isEqualToString:@"tablet"]) {
        [params setObject:@(kADG_AdType_Tablet) forKey:@"adtype"];
        event = @{ @"width": @(kADGAdSize_Tablet_Width), @"height": @(kADGAdSize_Tablet_Height) };
    }
    
    if (self.onMeasure) {
        self.onMeasure(event);
    }
    
    NSLog(@"Succesed to receive an ad.");
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

#import "OnSpota.h"

#import <Cordova/CDVAvailability.h>

#import <OnSpotSdk/OnSpotaApi.h>
#import <OnSpotSdk/OnSpotSdk.h>

@implementation OnSpota

OnSpotaCore *sharedInstance;

- (void)pluginInitialize {
     sharedInstance = [[OnSpotaCore alloc] init];
    
}
-(void)application:(UIApplication *)application performFetchWithCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler
{
    
    [sharedInstance resumeTracker];
    
    //Tell the system that you ar done.
    completionHandler(UIBackgroundFetchResultNewData);
}


- (void)initTracker:(CDVInvokedUrlCommand*)command
{

    NSString* sdkId = [[command arguments] objectAtIndex:0];
    NSLog(@"%@", [NSString stringWithFormat: @"SDK Iniciado con key: %@", sdkId]);
    
    [sharedInstance initTracker:sdkId];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    
    
}
- (void)suspendTracker:(CDVInvokedUrlCommand*)command
{
    
    
    [sharedInstance suspendTracker];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    
    
}
- (void)setUserId:(CDVInvokedUrlCommand*)command
{
    
    NSString* userId = [[command arguments] objectAtIndex:0];
    
    [sharedInstance setUserId:userId];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
        
}

- (void)setApiHost:(CDVInvokedUrlCommand*)command
{
    
    NSString* apiHost = [[command arguments] objectAtIndex:0];
    
    [sharedInstance setApiHost:apiHost];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    
}

- (void)resumeTracker:(CDVInvokedUrlCommand*)command
{
    
    
    [sharedInstance resumeTracker];
    
    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK];
    
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    
}




@end

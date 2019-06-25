#import <Cordova/CDVPlugin.h>

#import <OnSpotSdk/OnSpotaApi.h>
#import <OnSpotSdk/OnSpotSdk.h>

@interface OnSpota : CDVPlugin {
}

// Encabezados de las funciones del plugin
- (void) initTracker:(CDVInvokedUrlCommand*)command;
- (void) suspendTracker:(CDVInvokedUrlCommand*)command;
- (void) setUserId:(CDVInvokedUrlCommand*)command;
- (void) setApiHost:(CDVInvokedUrlCommand*)command;
- (void) resumeTracker:(CDVInvokedUrlCommand*)command;


@end


var exec = require('cordova/exec');

var PLUGIN_NAME = 'OnSpota';

var OnSpota = {
  initTracker: function (apiKey, successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "initTracker", [apiKey]);
  },
  suspendTracker: function ( successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "suspendTracker", []);
  },
  setUserId: function ( userId, successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "setUserId", [userId]);
  },
  setApiHost: function ( host, successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "setApiHost", [host]);
  },
  resumeTracker: function ( successCallback, errorCallback) {
    exec(successCallback, errorCallback, PLUGIN_NAME, "resumeTracker", []);
  }

}


module.exports = OnSpota;

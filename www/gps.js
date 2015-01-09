window.gps = function(str){
    cordova.exec(function(su){console.log("ok")}, function(err){alert(err)},"GpsPlugin","gps", [str]);
}

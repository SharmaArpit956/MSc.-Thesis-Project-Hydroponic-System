#include "avdweb_VirtualDelay.h"
VirtualDelay weatherTemperatureDelay; // default = millis
void get_weatherTemperature(double interval) {
  interval = interval * 1000;
  weatherTemperatureDelay.start(interval); // calls while running are ignored
  if (weatherTemperatureDelay.elapsed()) {
    //  Check continously after every 50 milliseconds for a new value published to the feed
    Adafruit_MQTT_Subscribe *subscription;
    while ((subscription = mqtt.readSubscription(50))) {
      // If a new value is published, print it on the serial monitor
      if (subscription == &weatherTemperatureFeed) {
        Serial.print(F("Got weather temperature: "));
        double weatherTemperature = atof((char *)weatherTemperatureFeed.lastread);
        Serial.println(weatherTemperature);

      }
    }
  }
}

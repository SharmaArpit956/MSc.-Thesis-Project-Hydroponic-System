// Get the pump speed via IOT in case the pump is being controlled by the pump fragment on the app
void get_pumpSpeed() {
  //  Check continously after every 50 milliseconds for a new value published to the feed
  Adafruit_MQTT_Subscribe *subscription;
  while ((subscription = mqtt.readSubscription(50))) {
    if (subscription == &pumpSpeedFeed) {
      int tempSpeed = atol((char *)pumpSpeedFeed.lastread);
      // If a new value is published, make it equal to the pump speed and print it on the serial monitor
      if (pumpSpeed != tempSpeed) {
        pumpSpeed = tempSpeed;
        convertedPumpSpeed = map(pumpSpeed, 0, 100, 4, 9 );
        Serial.print(F("Got pump speed: "));
        Serial.println(pumpSpeed);
        Serial.println();
      }
    }
  }
}

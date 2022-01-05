#include "avdweb_VirtualDelay.h" // The delay library for delays without blocking.  default = millis
VirtualDelay pumpSpeedPublishDelay; // default = millis

// Send the pump speed calculated by the AI to the cloud via IOT after every 'interval' seonds
void send_pumpSpeed(int interval) {
  interval = interval * 1000;
  pumpSpeedPublishDelay.start(interval); // calls while running are ignored
  if (pumpSpeedPublishDelay.elapsed()) {
    if  (! pumpSpeedPublishFeed.publish(pumpSpeed)) {
      Serial.println(F("Pump Speed publish Failed"));
      Serial.println();
    } else {
      Serial.print(F("Pump Speed (from AI) got published: "));
      Serial.println(pumpSpeed);
      Serial.println();
    }
  }
}

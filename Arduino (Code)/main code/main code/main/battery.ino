#include "RunningMedian.h" // A library for getting the running median of the sensor values for more accuacy
#include "avdweb_VirtualDelay.h" // The delay library for delays without blocking.  default = millis
RunningMedian batterySamples = RunningMedian(10); // A runnng median of 10 consecutive samples
VirtualDelay batteryDelay; 

void send_batteryLevel(int interval) {
  interval = interval * 1000;
  float batteryCorrectionFactor = 12.52 / 13.15;
  //  float batteryCorrectionFactor = 1;
  batteryDelay.start(interval); // calls while running are ignored
  if (batteryDelay.elapsed()) {
    int x = analogRead(A0);
    batterySamples.add(x);
    float adcValue = (float)((batterySamples.getMedian()) * (3.300 / 1024.000));
    float battVoltage = adcValue * batteryCorrectionFactor * 10;
    //    Serial.println();
    //    Serial.print("Read Voltage is : "); Serial.println(adcValue);
    //    Serial.print("Battery voltage is : "); Serial.print(battVoltage); Serial.println(" V ");
    int battPercentage = 100; // The value is hard coded, as it is just for being sent to the cloud via IOT

    if  (! batteryFeed.publish(battPercentage)) {
      Serial.println(F("batery value publish Failed"));
    } else {
      Serial.print(F("batery value published: "));
      Serial.println(battPercentage);
    }
  }
}

 

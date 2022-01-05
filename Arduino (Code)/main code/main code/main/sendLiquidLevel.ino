#include "RunningMedian.h" // A library for getting the running median of the sensor values for more accuacy
#include "avdweb_VirtualDelay.h" // The delay library for delays without blocking.  default = millis
RunningMedian liquidLevelSamples = RunningMedian(10); // A runnng median of 10 consecutive samples
VirtualDelay liquidLevelDelay; // default = millis
const int liquid_level_pin = 33;

#define SOUND_SPEED 0.034  // Constant variable for the sound speed in cm/uS
long duration; // variable for the duration after which the wave is received back

void send_liquidLevel(int interval) {
  interval = interval * 1000;
  float liquidLevelCorrectionFactor = 1;
  liquidLevelDelay.start(interval); // calls while running are ignored
  if (liquidLevelDelay.elapsed()) {

    // Clears the trig_pin
    digitalWrite(trig_pin, LOW);
    delayMicroseconds(2);
    // Sets the trig_pin on HIGH state for 10 micro seconds
    digitalWrite(trig_pin, HIGH);
    delayMicroseconds(10);
    digitalWrite(trig_pin, LOW);

    // Reads the echo_pin, returns the sound wave travel time in microseconds
    duration = pulseIn(echo_pin, HIGH);
    liquidLevelSamples.add(duration);

    // Calculate the distance
    waterLevelValue = 1000 - 100 * liquidLevelSamples.getMedian() * SOUND_SPEED / 2;

    //Publish the latest liquid level to the cloud
    if  (! liquidLevelFeed.publish(waterLevelValue)) {
      Serial.println(F("Liquid level publish Failed"));
    } else {
      Serial.print(F("Liquid level published: "));
      Serial.println(waterLevelValue);
    }
  }
}

#define SOUND_SPEED 0.034  // Constant variable for the sound speed in cm/uS
long duration; // variable for the duration after which the wave is received back

void read_ultrasonic_waterLevel() {

  // Clears the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);

  // Calculate the distance
  waterLevelValue = 1000 - 100 * duration * SOUND_SPEED / 2; // The actual distance half of the distance travelled by the waves

  // Prints the distance in the Serial Monitor
  //  Serial.print("Distance (mm): ");
  //  Serial.println(waterLevelValue);
}

#include "avdweb_VirtualDelay.h" // The delay library for delays without blocking.  default = millis
VirtualDelay pHDelay; // default = millis
unsigned long int avgValue;  //Store the average value of the sensor feedback
int sensorValue = 0; // initialise the sensor value to 0
int buf[10], temp; // temporary variables

void send_pH( double interval) {
  interval = interval * 1000; // convert seconds to milliseconds to be used by the delay function

  // addPublish
  pHDelay.start(interval); // calls while running are ignored
  // Senfd the pH value to the cloud after every 'interval' seconds
  if (pHDelay.elapsed()) {
    //    calculatepH();
    if  (! pHStatusFeed.publish(pHStatus)) {
      Serial.println(F("pH publish Failed"));

    } else {
      Serial.print(F("pH Status published: "));
      Serial.println(pHStatus);

    }
    // Empty the array again for next reding
    memset(pHStatus, 0, sizeof pHStatus);

    if  (! pHFeed.publish((pHValue ))) {
      Serial.println(F("pH Value publish Failed"));
      Serial.println();
    } else {
      Serial.print(F("pH Value value published: "));
      Serial.println(pHValue );
    }
  }
}

// Calculate the pH value based on the sensor value
void calculatepH() {
  memset(pHStatus, 0, sizeof pHStatus);
  sensorValue  = analogRead(pHSensor_pin);     //put Sensor insert into soil
  for (int i = 0; i < 10; i++) //Get 10 sample value from the sensor for smooth the value
  {
    buf[i] = analogRead(pHSensor_pin);
    delay(10); // delay 10 milliseconds
  }

  //sort the analog from small to large
  for (int i = 0; i < 9; i++)
  {
    for (int j = i + 1; j < 10; j++)
    {
      if (buf[i] > buf[j])
      {
        temp = buf[i];
        buf[i] = buf[j];
        buf[j] = temp;
      }
    }
  }
  avgValue = 0;
  for (int i = 2; i < 8; i++)               //take the average value of 6 center sample
    avgValue += buf[i];
  pHValue = (float)avgValue * 5.0 / 4096 / 6; //convert the analog into millivolt
  pHValue = 3.5 * pHValue + 1.63;                  //convert the millivolt into pH value


  // Make sure that the pH value is not out of boundaries
  if (pHValue  < 0) {
    pHValue  = 0;
  } else if (pHValue  > 14) {
    pHValue  = 14;

  }

  // According to the pH value, assign it a pH status as follows:
  if ((pHValue  > 6.5) && (pHValue < 7.5)) {
    strcat(pHStatus, "Almost Neutral");
  }
  else if (pHValue  > 11) {
    strcat(pHStatus, "Very Basic");
  }
  else if (pHValue  > 8.5) {
    strcat(pHStatus, "Basic");
  }
  else if (pHValue  > 7.5) {
    strcat(pHStatus, "Mildly Basic");
  }
  else if (pHValue  > 5.5) {
    strcat(pHStatus, "Mildly Acidic");
  }
  else if (pHValue  > 3) {
    strcat(pHStatus, "Acidic");
  }
  else if (pHValue  > 0) {
    strcat(pHStatus, "Very Acidic");
  }
  else {
    strcat(pHStatus, "NA");
  }
}

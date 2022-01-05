unsigned long int avgValue;  //Store the average value of the sensor feedback
int buf[10], temp; // temporary variables
char   pHStatus[20] = ""; // Variable for storing pH status

void read_pH() {

  // Get 10 sample value from the sensor to smooth the value
  for (int i = 0; i < 10; i++)
  {
    buf[i] = analogRead(pH_sensor_pin);
    delay(10); // delay 10 milliseconds
  }

  // Sort the analog value from small to large
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

  // Take the average value of 6 center samples
  avgValue = 0;
  for (int i = 2; i < 8; i++)
    avgValue += buf[i];
  pHValue = (float)avgValue * 5.0 / 4096 / 6;    //convert the analog into millivolt
  pHValue = 3.5 * pHValue + 1.63;               //convert the millivolt into pH value and add the error (-1.63) for calibration
}

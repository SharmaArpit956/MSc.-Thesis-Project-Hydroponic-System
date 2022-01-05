// Read the potentiometer
void read_potentiometer() {
  int temp_adc_value = analogRead(potmeterPin);    // Read the analog value of the slide potentiometer
  pumpSpeed = map(temp_adc_value, 0, 4095, 0,  1000) / 10.0; // Map the read value between 0 and 4095, to a decimal value between 0.0 and 100.0
  convertedPumpSpeed = map(pumpSpeed, 0, 100, 4, 9 ); // Convert the read value to control the speed of the pump. The values depend on the nature of the pump
}

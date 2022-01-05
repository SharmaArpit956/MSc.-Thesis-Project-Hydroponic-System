void set_pumpSpeed(int speed) {
  //  working range
  //  4-9 ADC
  int adcValue = map(speed, 0, 100, 0, 31 ); // Map the pump speed from 0 to 100 to an ADC value for controllling the pump using PWM
  analogWrite(pump_pin, adcValue); // Use the value to control the pump using PWM
}

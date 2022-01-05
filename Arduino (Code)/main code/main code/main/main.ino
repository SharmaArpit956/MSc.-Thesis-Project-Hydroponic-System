// for ESP 32 microcontroller
#include <WiFi.h>
#include <analogWrite.h>
// for NodeMCU microcontroller
//#include <ESP8266WiFi.h>

// pins
const int pHSensor_pin = 33; // pin for the pH sensor
int pump_pin = 17; // pin for the pump
const int trig_pin = 5; //trigger  pin for the ultrasonic level sensor
const int echo_pin = 18; // Echo pin for the for ultrasonic level sensor

// Variables for actually printing stuff
double pumpSpeed = 0; // variable for the pump speed
int waterLevelValue = 0; // variable for the water level in the nutrient tank
double pHValue; // variable for the pH value
char   pHStatus[20] = ""; // Variable for storing pH status
constexpr float period = 2000;  // Period (milliseconds)
int convertedPumpSpeed = 0; // ADC value for the pump speed after conversion

void setup() {
  Serial.begin(9600); // Begin the serial communication at 9600 baud rate
  pinMode(pump_pin, OUTPUT);  // sets the pin as outputoutput
  digitalWrite(pump_pin, LOW); // set the pump initially off before the experiment
  pinMode(trig_pin, OUTPUT); // Sets the trig_pin as an Output
  pinMode(echo_pin, INPUT); // Sets the echo_pin as an Input
  delay(10); // delay 10 milliseconds
  MQTT_setup(); // Setup the MQTT
  ai_setup(); // Setup the AI
}

void loop() {

  MQTT_loop(); // Conduct MQTT operations continously
  send_pH(16); // Send the pH sensor reading to the cloud via IOT. The parameter is the seconds after which it updates
  send_liquidLevel(17); // Send the liquid level sensor reading to the cloud via IOT. The parameter is the seconds after which it updates
  send_batteryLevel(18); // Send the battery level to the cloud via IOT. The parameter is the seconds after which it updates
  get_weatherTemperature(19); // Get the temperature from the cloud. The parameter is the seconds after which it updates
  calculatepH(); // Calculate the pH value

//  // For calculating the time taken per inference by the AI
//  unsigned long start_timestamp = micros();
//  // Get current timestamp and modulo with period
//  unsigned long timestamp = micros();
//  timestamp = timestamp % (unsigned long)period;
  ai_loop();
//  Serial.print("Time for inference (us): ");
//  Serial.println(micros() - start_timestamp);

  get_pumpSpeed(); // Get the pump speed via IOT in case the pump is being controlled by the pump fragment on the app
  send_pumpSpeed(20); // Send the pump speed calculated by the AI to the cloud via IOT. The parameter is the seconds after which it updates
  set_pumpSpeed(convertedPumpSpeed); // Control the speed of the pump using PWM
}

#include <analogWrite.h>

// pins
int potmeterPin = 35; // pin for the sliding potentiometer
const int pH_sensor_pin = 33; // pin for the pH sensor
int pump_pin = 17; // pin for the pump
const int trigPin = 5; //trigger  pin for the ultrasonic level sensor
const int echoPin = 18; // Echo pin for the for ultrasonic level sensor

// variables:
int convertedPumpSpeed = 0; // ADC value for the pump speed after conversion
int delayTime = 10;  // Delay after which next reading has to be taken
bool experimentationFlag = false; // The flag to stop the experiment by keyboard control on the serial monitor

// Variables for actually printing stuff
int timeWhileExperimenting = 0; // variable for storing the time while the experimenting
double pumpSpeed = 0; // variable for the pump speed
int waterLevelValue = 0; // variable for the water level in the nutrient tank
double pHValue; // variable for the pH value

char  command = 123; // variable for incoming serial data

void setup() {
  Serial.begin(9600);
  pinMode(pump_pin, OUTPUT);  // sets the pin as an output pin
  digitalWrite(pump_pin, LOW); // set the pump initially off before the experiment
  pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output for the water level sensor
  pinMode(echoPin, INPUT); // Sets the echoPin as an Input for the water level sensor
}

void loop() {
  // If something is typed on the keyboard, read it
  if (Serial.available() > 0) {
    // read the incoming byte:
    command = Serial.read();
  }

  // If the key typed is '1', begin the experiment
  if (command == '1') {
    experimentationFlag = true;
  }

  // If the key typed is '0', stop the experiment
  if (command == '0') {
    experimentationFlag = false;
    timeWhileExperimenting = 0;
  }

  // If the key typed is 's', stop the pump (in case potentiometer stops working)
  if (command == 's') {
    pumpSpeed = 0;
  }

  //////////////////////////////////////////// READ THE SENSORS ///////////////////////////////////////////////////////
  read_potentiometer(); // Read the potentiometer
  set_pumpSpeed(pumpSpeed); // Set the
  read_ultrasonic_waterLevel(); // Read the water level sensor
  read_pH(); // Read the pH sensor
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  // If the experiment is being conducted, print the values to the serial monitor
  if (experimentationFlag == true) {
    Serial.print(timeWhileExperimenting);
    Serial.print(" ");
    Serial.print(pumpSpeed);
    Serial.print(" ");
    Serial.print(waterLevelValue);
    Serial.print(" ");
    Serial.print(pHValue);
    Serial.println(" ");
    timeWhileExperimenting += delayTime; // increment the time while experimenting
  }

  // delay before the next reading
  delay(delayTime);
}

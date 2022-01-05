// To subscribe to a new feed ctrl F --> addSubscription
// To publish to a new feed ctrl F -->   addPublish

#include "Adafruit_MQTT.h"
#include "Adafruit_MQTT_Client.h"


/************************* WiFi Access Point *********************************/

#define WLAN_SSID       "Connectify-J6"
#define WLAN_PASS       "33333333"

/************************* Adafruit.io Setup *********************************/

#define AIO_SERVER      "io.adafruit.com"
#define AIO_SERVERPORT  1883                   // use 8883 for SSL
#define AIO_USERNAME    "arpitsmscproject"
#define AIO_KEY         "aio_GPgg95Ao3N0ndl8EpBI9k2E3HxNJ"

// Create an ESP 32 WiFiClient class to connect to the MQTT server.
WiFiClient client;
// Setup the MQTT client class by passing in the WiFi client and MQTT server and login details.
Adafruit_MQTT_Client mqtt(&client, AIO_SERVER, AIO_SERVERPORT, AIO_USERNAME, AIO_KEY);

// addSubscription
// Subscribe to Feeds
Adafruit_MQTT_Subscribe weatherTemperatureFeed = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/weatherTemperature");
Adafruit_MQTT_Subscribe pumpSpeedFeed = Adafruit_MQTT_Subscribe(&mqtt, AIO_USERNAME "/feeds/pumpSpeed");

// addPublish
// Publish to Feeds
Adafruit_MQTT_Publish soilMoistureStatusFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/soilMoistureStatus");
Adafruit_MQTT_Publish soilMoistureInPercentageFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/soilMoistureInPercentage");
Adafruit_MQTT_Publish pumpSpeedPublishFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/pumpSpeed");
Adafruit_MQTT_Publish pHStatusFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/pHStatus");
Adafruit_MQTT_Publish pHFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/pH");

Adafruit_MQTT_Publish batteryFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/battery");

Adafruit_MQTT_Publish liquidLevelFeed = Adafruit_MQTT_Publish(&mqtt, AIO_USERNAME "/feeds/liquidLevel");

void MQTT_loop() {
  int8_t ret;
  // Stop if already connected.
  if (mqtt.connected()) {
    return;
  }
  Serial.print("Connecting to MQTT... ");
  uint8_t retries = 3;
  while ((ret = mqtt.connect()) != 0) { // connect will return 0 for connected
    Serial.println(mqtt.connectErrorString(ret));
    Serial.println("Retrying MQTT connection in 5 seconds...");
    mqtt.disconnect();
    delay(5000);  // wait 5 seconds
    retries--;
    if (retries == 0) {
      // basically die and wait for WDT to reset me
      while (1);
    }
  }
  Serial.println("MQTT Connected!");
  Serial.println();
  Serial.println();
  Serial.println();
}

void MQTT_setup() {
  Serial.println(F("Adafruit MQTT demo"));
  // Connect to WiFi access point.
  Serial.println(); Serial.println();
  Serial.print("Connecting to ");
  Serial.println(WLAN_SSID);
  WiFi.begin(WLAN_SSID, WLAN_PASS);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.println("WiFi connected");
  Serial.println("IP address: "); Serial.println(WiFi.localIP());


  // addSubscription
  mqtt.subscribe(&weatherTemperatureFeed);
  mqtt.subscribe(&pumpSpeedFeed);
}

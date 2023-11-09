#include<Servo.h>

bool on = true;
unsigned char inp0,inp1;
int pin=0, code=0;
int temp=0;
bool next=false;
Servo servo;

void setup()
{
 for(int a=2;a<=13;a++)
    pinMode(a,OUTPUT);
  for(int b=2;b<=13;b++)
    digitalWrite(b,LOW);
    
  Serial.begin(9600);
//  Serial.println("The bluetooth gates are open.\n Connect to HC-05 from any other bluetooth device with 1234 as pairing key!.");
  pinMode(13, 1);
}
 
void loop()
{
 
  if (Serial.available()){
      inp0 = Serial.read();
//      Serial.println(">>");
//      digitalWrite(13, on);
//      on = !on;
  
      pin = inp0>>3;
      code = inp0&7;

      if(next){
        furthurCommands(inp0);
        next=false;
      }
      else if((code)==7){
        digitalWrite(pin,HIGH);
      }
      else if((code)==6){
        digitalWrite(pin,LOW);
      }
      else if((code)==5){
        pinMode(pin,OUTPUT);
      }
      else if((code)==4){
        pinMode(pin,INPUT);
      }
      else if((code)==3){
        inp1 = readFromSerial();
  //      Serial.write(inp1);
        analogWrite(pin,inp1);
      }
      else if((code)==2){
        temp=analogRead(pin);
        Serial.write(temp%128);
        Serial.write(temp/128);
      }
      else if((code)==1){
        Serial.write(digitalRead(pin));
      }
      else if(((code)==0)){
            temp = pin;
            next = true;
      }

      /// for debugging
//      Serial.print("pin=");
//      Serial.print(pin);
//      Serial.print("code=");
//      Serial.print(code);
//      Serial.println("| ");
    }
}


inline unsigned char readFromSerial(){
  while(Serial.available()<1);
  return Serial.read();
}

void furthurCommands(unsigned char input){
  pin = temp;
  if (input == 1){
    servo.attach(pin);
  }
  else if (input == 2){
    inp1 = readFromSerial();
    servo.write(inp1);
  }
  else if (input == 3){
    Serial.write(servo.read());
  }
  else if (input == 4){ // for ultrasonic
    code = readFromSerial(); // echo pin, pin=trig pin
//    digitalWrite(pin, LOW); // ensuring clean wave
//    delayMicroseconds(5);
    digitalWrite(pin, HIGH);
    delayMicroseconds(10);
    digitalWrite(pin, LOW);
    temp = 0;
    temp = pulseIn(code, HIGH, 1000000); // duration measure
    temp = temp * 0.0343/2;
    Serial.write(temp);
  }
}


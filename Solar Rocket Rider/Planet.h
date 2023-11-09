#pragma once

class Planet {
private:
public:
    float radius, mass, rotation, revolution, gravity, distance;
    vec3 scale;
    vec3 translate;
    vec3 rotate;
    string planetName, pathToPlanet;
    int parent;
    Planet(string name, string path, float _radius, float _mass, float _rotation, float _revolution, float _gravity,
           float _distance): scale(vec3(1.0f,1.0f,1.0f)), translate(vec3()), rotate(vec3()) {
        radius = _radius;
        mass = _mass;
        rotation = _rotation;
        revolution = _revolution;
        gravity = _gravity;
        planetName = name;
        pathToPlanet = path;
        distance = _distance;
    }
    void setTranslate(vec3 trans){
        translate=trans;
    }
    void setRotate(vec3 rot){
        rotate = rot;
    }
    void setScale(float scal){
        scale = vec3(scal,scal, scal);
    }


    float getDistance(){
        return distance;
    }
    float getRadius(){
        return radius;
    }
    float getRotate(){
        return rotation;
    }
    float getRevolution(){
        return revolution;
    }
    vec3 getTranslation(){
        return translate;
    }
    vec3 getRotation(){
        return rotate;
    }
    vec3 getScaling(){
        return scale;
    }
    string getName() {
        return planetName;
    }

    string getLocation() {
        return pathToPlanet;
    }

    void initPhysics(){

        return;
    }
};

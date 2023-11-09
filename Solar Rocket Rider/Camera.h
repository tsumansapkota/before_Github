#pragma once
#include <iostream>
#include <glm/glm.hpp>
class Camera {
public:
    glm::vec3 position;
    bool upChanged=false;
    glm::vec3 viewDirection;
    glm::vec3 Up;
    glm::vec3 toRotate;
    glm::vec3 strafeDirection;
    glm::vec2 oldMousePosition;
    float MOVEMENT_SPEED;
    float ROTATIONAL_SPEED;

    void printVec(std::string name, glm::vec3 vector);
    Camera();
    glm::mat4 getWorldToViewMatrix() const;
    glm::vec3 getPosition(){
        return position;
    }
    void mouseUpdate(const glm::vec2& newMousePosition);
    virtual void moveForeward();
    virtual void moveBackard();
    virtual void starfeLeft();
    virtual void strafeRight();
    virtual void moveUp();
    virtual void moveDown();
    virtual void tiltLeft();
    virtual void tiltRight();

};
#include "Camera.h"
#include <glm/gtx/transform.hpp>
#include "Display.h"




Camera::Camera() :
        viewDirection(1.0f, 0.0f, 0.0f),
        position(0.0f, 0.0f, 0.0f),
        Up(0.0f, 1.0f, 0.0f) {
    MOVEMENT_SPEED = 0.01f;  //0.1f;
    ROTATIONAL_SPEED =  0.2f ;  //0.2f;

}

glm::mat4 Camera::getWorldToViewMatrix() const {

    return glm::lookAt(position, position + viewDirection, Up);
}


void Camera::mouseUpdate(const glm::vec2 &newMousePosition) {

    glm::vec2 mouseDelta = newMousePosition;
    //glm::vec2 mouseDelta = newMousePosition-oldMousePosition;
    strafeDirection = glm::normalize(glm::cross(viewDirection,Up)); //todo left and right


    //glm::vec3 toRotateAround = glm::normalize(glm::cross(viewDirection, Up)); //

    glm::mat4 rotator = glm::rotate(-ROTATIONAL_SPEED * glm::radians(mouseDelta.x), Up) *
                        glm::rotate(-ROTATIONAL_SPEED * glm::radians(mouseDelta.y), strafeDirection);
    glm::vec3 oldViewDirection = viewDirection;
    viewDirection = glm::mat3(rotator) * viewDirection;
    Up = glm::mat3(rotator)* Up;
    //toRotate=toRotateAround;


    int noChanged=0;
    for(int a=0; a<3; a++){
        if(oldViewDirection[a]/viewDirection[a]<0) noChanged++;
    }
    if(noChanged>1)viewDirection=oldViewDirection;
    //oldMousePosition = newMousePosition;

}
void Camera::printVec(std::string name, glm::vec3 vector){
    std::cout<<name<<" : "<<vector.x<<" , "<<vector.y<<" , "<<vector.z<<"   "<<" \n ";

}

void Camera::moveBackard() {
    position -= MOVEMENT_SPEED*viewDirection;

}

void Camera::moveForeward() {
    position += MOVEMENT_SPEED*viewDirection;
}

void Camera::starfeLeft() {
    position-= MOVEMENT_SPEED*strafeDirection;
}

void Camera::strafeRight() {
    position+= MOVEMENT_SPEED*strafeDirection;

}

void Camera::moveUp() {
    position+= MOVEMENT_SPEED * Up;
}

void Camera::moveDown() {
    position-= MOVEMENT_SPEED * Up;
}
void Camera::tiltRight() {
    strafeDirection = glm::normalize(glm::cross(viewDirection,Up));
    Up-= strafeDirection*ROTATIONAL_SPEED/100.0f;
}
void Camera::tiltLeft() {
    strafeDirection = glm::normalize(glm::cross(viewDirection,Up));
    Up+= strafeDirection*ROTATIONAL_SPEED/100.0f;
}

#pragma once


#include <iostream>
#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>
#include <glm/gtx/rotate_vector.hpp>
#include "Camera.h"
#include <btBulletDynamicsCommon.h>

#define PIby3 1.0471975512f
#define PIby2 1.57079632679f


class Spaceship : public Camera {
private:
    vec3 temp;
public:

    glm::vec3 forwardDirection;
    glm::vec3 rightDirection;
    glm::vec3 normalDirection;
    glm::vec3 force;
    btRigidBody *spaceshipBody;
    float radius, height, mass, damping;
    float DAMPINGFACTOR = 0.1f;

    Spaceship() : Camera() {
        forwardDirection = glm::vec3(1.0f, 0.0f, 0.0f);
        rightDirection = glm::vec3(0.0f, 0.0f, 1.0f);
        normalDirection = glm::vec3(0.0f, 1.0f, 0.0f);
        damping = 0.0f;
        height = 0.0f;
        temp = forwardDirection;
    }

    void calculateVectors() {
        btQuaternion myQuaternion = spaceshipBody->getWorldTransform().getRotation();
        float yaw, pitch, roll, x, y, z;
        btMatrix3x3(myQuaternion).getEulerYPR(yaw, pitch, roll);
        x = cos(pitch) * cos(yaw);
        y = cos(pitch) * sin(yaw);
        z = sin(pitch);
        //printVec("yaw pitch roll", vec3(yaw,pitch,roll));
        btVector3 myVector = btVector3(x, y, -z);
        forwardDirection = glm::normalize(getGlmVec3(myVector));
        pitch = pitch + PIby2;
        x = cos(pitch) * cos(yaw);
        y = cos(pitch) * sin(yaw);
        z = sin(pitch);
        myVector = btVector3(x, y, -z);
        rightDirection = -glm::rotate(glm::normalize(getGlmVec3(myVector)), roll, forwardDirection);
        normalDirection = glm::normalize(glm::cross(rightDirection, forwardDirection));
        viewDirection = glm::normalize(viewDirection+(forwardDirection-temp));
        Up = normalDirection;
        strafeDirection = rightDirection;
        temp = forwardDirection;
        /*printVec("forward ", forwardDirection);
        printVec("upward ", normalDirection);
        printVec("right ", rightDirection);*/
    }

    void implementDamping() {
        damping = DAMPINGFACTOR;
        //if(damping<DAMPINGFACTOR) damping=DAMPINGFACTOR;
        spaceshipBody->setDamping(damping, damping * 8.0f);
    }

    void moveForeward() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyCentralForce(getBtVec3(forwardDirection) * 10);
    }

    void moveBackard() {
        damping += 0.001f;
        if (damping > DAMPINGFACTOR * 10) damping = DAMPINGFACTOR * 10;
        spaceshipBody->setDamping(damping, damping * 8.0f);
    }

    void starfeLeft() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyTorque(getBtVec3(normalDirection));
        //rotate left
    }

    void strafeRight() {
        implementDamping();
        spaceshipBody->applyTorque(getBtVec3(-normalDirection));
        //rotate right
    }

    void moveUp() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyTorque(getBtVec3(rightDirection));
        //rotate up
    }

    void moveDown() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyTorque(getBtVec3(-rightDirection));
        //rotate down
    }

    void tiltLeft() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyTorque(getBtVec3(forwardDirection));
        //roll left
    }

    void tiltRight() {
        implementDamping();
        calculateVectors();
        spaceshipBody->applyTorque(getBtVec3(-forwardDirection));
        //roll right
    }

    glm::vec3 getGlmVec3(btVector3 vec) {
        return glm::vec3(vec.x(), vec.y(), vec.z());
    }

    btVector3 getBtVec3(glm::vec3 vec) {
        return btVector3(vec.x, vec.y, vec.z);
    }
};

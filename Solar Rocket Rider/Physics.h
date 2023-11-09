#pragma once
#include <btBulletDynamicsCommon.h>
#include <btBulletCollisionCommon.h>
#include <vector>
#include <map>
#include <string>
#include <glm/detail/type_mat.hpp>
#define GLEW_STATIC
#include <GL/glew.h>
#include "Camera.h"

using namespace std;

class Physics{
public:
    btDiscreteDynamicsWorld *myWorld;
    btBroadphaseInterface *myBroadphase;
    btCollisionDispatcher *myDispatcher;
    btDefaultCollisionConfiguration *myCollisionConfiguration;
    btSequentialImpulseConstraintSolver *mySequentialImpulseConstraintSolver;
    btDefaultMotionState *myMotionState, *myMotionState_Sol;
    btTransform myTransform;
    btVector3 myVector;
    vector<btRigidBody *> bodies;
    map<string,unsigned long> bodiesName;
    btRigidBody* cameraBody;

    btRigidBody *addSphere(float rad, float x, float y, float z, float mass, btQuaternion quat = btQuaternion(),string name="") {

        btCollisionShape *shape = new btSphereShape(rad);
        myTransform.setIdentity();
        myTransform.setOrigin(btVector3(x, y, z));
        if (quat != btQuaternion())
            myTransform.setRotation(quat);

        btVector3 localInertia(0, 0, 0);
        if (mass != 0.0f)
            shape->calculateLocalInertia(mass, localInertia);

        myMotionState = new btDefaultMotionState(myTransform);
        btRigidBody::btRigidBodyConstructionInfo myBoxRigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);
        btRigidBody *body = new btRigidBody(myBoxRigidBodyConstructionInfo);
        body->setCollisionFlags(body->getCollisionFlags() | btCollisionObject::CF_CUSTOM_MATERIAL_CALLBACK);
        body->setUserIndex((int) bodies.size());
        myWorld->addRigidBody(body);
        bodies.push_back(body);
        /*if(name.compare("")!=0){
            bodiesName[name]= (unsigned long) (bodies.size() - 1);
        }*/
        return body;
    }

    btRigidBody *addBox() {
        btCollisionShape *shape_sol = new btBoxShape(btVector3(1000, 1, 1000));
        myTransform.setIdentity();
        myTransform.setOrigin(btVector3(0, -100, 0));
        btVector3 localInertiaSol(0, 0, 0);

        btScalar mass = 0;

        myMotionState_Sol = new btDefaultMotionState(myTransform);

        btRigidBody::btRigidBodyConstructionInfo sol_info(mass, myMotionState_Sol, shape_sol, localInertiaSol);

        btRigidBody *box = new btRigidBody(sol_info);
        myWorld->addRigidBody(box);
        bodies.push_back(box);
        return box;
    }

    void initPhysics() {
        myCollisionConfiguration = new btDefaultCollisionConfiguration();
        myDispatcher = new btCollisionDispatcher(myCollisionConfiguration);
        myBroadphase = new btDbvtBroadphase();
        mySequentialImpulseConstraintSolver = new btSequentialImpulseConstraintSolver;
        myWorld = new btDiscreteDynamicsWorld(myDispatcher, myBroadphase, mySequentialImpulseConstraintSolver,
                                              myCollisionConfiguration);
        myWorld->setGravity(btVector3(0, 0, 0));

        initCamera();


    }
    void initCamera(){
        btCollisionShape *shape = new btSphereShape(0);
        myTransform.setIdentity();
        myTransform.setOrigin(btVector3(0, 0, 0));
        btVector3 localInertia(0, 0, 0);
        shape->calculateLocalInertia(0.05, localInertia);
        myMotionState = new btDefaultMotionState(myTransform);
        btRigidBody::btRigidBodyConstructionInfo myBoxRigidBodyConstructionInfo(0.05, myMotionState, shape, localInertia);
        btRigidBody *body = new btRigidBody(myBoxRigidBodyConstructionInfo);
        body->setCollisionFlags(body->getCollisionFlags() | btCollisionObject::CF_NO_CONTACT_RESPONSE);
        myWorld->addRigidBody(body);
        body->applyCentralForce(btVector3(50,0,0));
        cameraBody= body;
        cameraBody->setDamping(0.1,0.1);

    }

    void deleteObjects(){
        myWorld->removeCollisionObject(cameraBody);
        delete cameraBody;
        delete cameraBody->getCollisionShape();
        delete cameraBody->getMotionState();
        for (
                int i = 0; i < bodies.size(); i++) {
            myWorld->removeCollisionObject(bodies[i]);
            btMotionState *motionState = bodies[i]->getMotionState();
            btCollisionShape *shape = bodies[i]->getCollisionShape();
            delete bodies[i];
            delete shape;
            delete motionState;
        }

        delete myDispatcher;
        delete myCollisionConfiguration;
        delete mySequentialImpulseConstraintSolver;
        delete myBroadphase;
        delete myWorld;

        bodies.clear();
    }
};
#include <iostream>

#define GLEW_STATIC
#include <GL/glew.h>
#include <SDL2/SDL.h>

#undef main

#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtx/transform.hpp>
#include <glm/gtx/quaternion.hpp>
#include <glm/gtc/type_ptr.hpp>
#include <glm/gtc/random.hpp>
#include <chrono>
#include <thread>
#include <btBulletDynamicsCommon.h>
#include <btBulletCollisionCommon.h>
#include "LinearMath/btMatrix3x3.h"
#include <fstream>
#include <sstream>
#include <stdlib.h>
#include "Display.h"
#include "Camera.h"
#include "Shader.h"
#include "Model.h"
#include "Timer.h"
#include "ObjectHandler.h"
#include "Light.h"
#include "Texture.h"
#include "Planet.h"
#include "Text.h"
#include "AudioEngine.h"
#include "Physics.h"
#include "Spaceship.h"

#define EYEPOSVIEW 10
#define MODELTOPROJECTION 12
#define MODELTOVIEW 13
#define PI 3.14159265358979323846f
#define PIX2 6.28318530718f
#define GCONST 6.66e-3f


bool shouldSpaceshipRotate = true;
bool shouldWeaponFire = false;
float fuelPercent = 100.0;
bool showHelp = false;
bool showAbout = false;
bool showSettings = false;
char textColor = 'r';
char playColor = 'r';
char helpColor = 'r';
char settingColor = 'r';
char aboutColor = 'r';
char exitColor = 'r';
int menuItem = 1;

btVector3 myVector;
btTransform myTransform;
btQuaternion myQuaternion;
float matrix[16];

void displayMainMenu(glm::mat4 &projection,Camera &camera, GLuint &skyboxShader, GLuint &skyboxVAO, GLuint &cubemap,
                     Text redText, Text blueText, Display &display, Timer &timer);

using glm::vec3;
using glm::vec4;
using glm::mat4;
using glm::mat3;
using std::string;
using std::map;
using std::thread;

bool reRunProgram = false;
float speedSimulation = 63.0f;
float radiusFactor = 1900.0f;
const int noOfAsteroids = 2000;
struct Asteroid{
    int type;
    float radius;
    unsigned int bulletPosition;
};

vector<Asteroid> asteroids;

float RandomFloat(float a, float b) {
    float random = ((float) std::rand()) / (float) RAND_MAX;
    float diff = b - a;
    float r = random * diff;
    return a + r;
}
int RandomInteger(int a, int b) {
    float random = ((float) std::rand()) / (float) RAND_MAX;
    int diff = b - a;
    int r = (int) (random * diff);
    return a + r;
}
void printVec(std::string name, glm::vec3 vector) {
    std::cout << name << " : " << vector.x << " , " << vector.y << " , " << vector.z << "   " << " \n ";
}
glm::vec3 getGlmVec3(btVector3 vec){
    return glm::vec3(vec.x(),vec.y(),vec.z());
}
btVector3 getBtVec3(glm::vec3 vec){
    return btVector3(vec.x,vec.y,vec.z);
}

void setupSkybox(GLuint &skyboxVAO, GLuint &cubemapTexture);

void displaySkybox(const glm::mat4 &projection, const Camera &camera, unsigned int skyboxShader, GLuint &skyboxVAO,
                   GLuint &cubemapTexture);

void setupOrbit(GLuint &orbitVAO, float radius, float h = 0.0, float k = 0.0);

void displayOrbit(float radius, const glm::mat4 &projection, const Camera &camera, unsigned int orbitShader, GLuint &orbitVAO);

void setupAsteroidData(float distance1, float distance2, Physics& phys) {
    for(int i=0; i<noOfAsteroids; i++) {
        float _dist = RandomFloat(distance1, distance2);
        float _angle = RandomFloat(0, PIX2);
        float _size = RandomFloat(0.6f, 2.0f);
        float _mass = RandomFloat(10.1f, 20.5f);
        float x = sin(_angle) * _dist;
        float z = cos(_angle) * _dist;
        float y = RandomFloat(-3.0f,3.0f);
        Asteroid ast1;
        ast1.type = RandomInteger(0,7);
        ast1.radius = _size;
        ast1.bulletPosition = (unsigned int) phys.bodies.size();
        phys.addSphere(_size,x,y,z,_mass);
        asteroids.push_back(ast1);
        phys.bodies[ast1.bulletPosition]->applyTorque(getBtVec3(glm::linearRand(vec3(-1,-1,-1),vec3(1,1,1)))*100.0f);
        phys.bodies[ast1.bulletPosition]->applyCentralForce(getBtVec3(glm::linearRand(vec3(-1,-1,-1),vec3(1,1,1)))*10.0f);

    }
    return;
}


std::function<void(Camera &, Display &, Physics&, Spaceship &)> eventHandleThread = [](Camera &cam, Display &disp, Physics& phy, Spaceship& spaceship) {
    std::cout << "ActionThreadRunning\n";
    Timer updateTimer;
    updateTimer.start();
    while (!(disp.m_isClosed) && !reRunProgram) {
        spaceship.spaceshipBody->activate();
        phy.cameraBody->activate();
        spaceship.calculateVectors();
        if (disp.KEYS[SDLK_ESCAPE]) {
            disp.m_isClosed = true;
        }

        if (disp.KEYS[SDLK_KP_PLUS] || disp.KEYS[SDLK_PLUS]) {
            speedSimulation += 0.1;
        } else if (disp.KEYS[SDLK_KP_MINUS] || disp.KEYS[SDLK_MINUS]) {
            speedSimulation -= 0.1;
        }

        if (!disp.IsMainMenuClosed()) {
            if (disp.KEYS[SDLK_KP_ENTER] || disp.KEYS[SDLK_RETURN]) {
                switch (menuItem) {
                    case 1:
                        disp.m_isClosed = false;
                        disp.mainMenu_isClosed = true;
                        break;
                    case 2:
                        showHelp = true;    showAbout = false; showSettings = false;
                        break;
                    case 3:
                        showSettings = true;    showAbout = false;  showHelp = false;
                        break;
                    case 4:
                        showAbout = true;   showHelp = false;   showSettings = false;
                        break;
                    case 5:
                        disp.m_isClosed = true;
                        disp.mainMenu_isClosed = false;
                        break;
                }
            }
            if (disp.KEYS[SDLK_ESCAPE]) {
                disp.m_isClosed = true;
                disp.mainMenu_isClosed = true;
            }
            if (disp.KEYS[SDLK_p]) {
                disp.m_isClosed = false;
                disp.mainMenu_isClosed = true;
            }
            if (disp.KEYS[SDLK_h]) {
                showHelp = true;    showAbout = false;
            } else if (disp.KEYS[SDLK_a]) {
                showAbout = true;   showHelp = false;
            } else if (disp.KEYS[SDLK_s]) {
                showSettings = true;
            }

            if (disp.KEYS[SDLK_UP]) {
                if(menuItem > 1)  menuItem--;
                SDL_Delay(200);
            }
            if (disp.KEYS[SDLK_DOWN]) {
                SDL_Delay(200);
                if(menuItem < 5) menuItem++;
            }
        }

        if (disp.KEYS[SDLK_BACKSPACE]) {
            //disp.mainMenu_isClosed = false;
            reRunProgram = true;
        }

        if(!disp.capsLock()){
            if (disp.mouseMoved) {
                cam.mouseUpdate(disp.mousePosition);
                disp.mouseMoved = false;
            }
            if (disp.KEYS[SDLK_w]) {
                phy.cameraBody->applyCentralForce(getBtVec3(cam.viewDirection) / 10);
            } else if (disp.KEYS[SDLK_s]) {
                phy.cameraBody->applyCentralForce(getBtVec3(-cam.viewDirection) / 10);
            }
            if (disp.KEYS[SDLK_a]) {
                phy.cameraBody->applyCentralForce(getBtVec3(-cam.strafeDirection) / 10);
            } else if (disp.KEYS[SDLK_d]) {
                phy.cameraBody->applyCentralForce(getBtVec3(cam.strafeDirection) / 10);
            }
            if (disp.KEYS[SDLK_r]) {
                phy.cameraBody->applyCentralForce(getBtVec3(cam.Up) / 10);
            } else if (disp.KEYS[SDLK_f]) {
                phy.cameraBody->applyCentralForce(getBtVec3(-cam.Up) / 10);
            }
            if (disp.KEYS[SDLK_q]) {
                cam.tiltRight();
            } else if (disp.KEYS[SDLK_e]) {
                cam.tiltLeft();
            }
    }else{
            if (disp.mouseMoved) {
                spaceship.mouseUpdate(disp.mousePosition);
                disp.mouseMoved = false;
            }
            if (disp.KEYS[SDLK_w]) {
                spaceship.moveForeward();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(cam.viewDirection) / 10);
            } else if (disp.KEYS[SDLK_s]) {
                spaceship.moveBackard();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(-cam.viewDirection) / 10);
            }
            if (disp.KEYS[SDLK_a]) {
                spaceship.starfeLeft();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(-cam.strafeDirection) / 10);
            } else if (disp.KEYS[SDLK_d]) {
                spaceship.strafeRight();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(cam.strafeDirection) / 10);
            }
            if (disp.KEYS[SDLK_r]) {
                spaceship.moveUp();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(cam.Up) / 10);
            } else if (disp.KEYS[SDLK_f]) {
                spaceship.moveDown();
                //phy.bodies[phy.bodies.size() - 1]->applyCentralForce(getBtVec3(-cam.Up) / 10);
            }
            if (disp.KEYS[SDLK_q]) {
                spaceship.tiltRight();
            } else if (disp.KEYS[SDLK_e]) {
                spaceship.tiltLeft();
            }
        }

        if (disp.KEYS[SDLK_SPACE]) {
            spaceship.spaceshipBody->setLinearVelocity(btVector3(0, 0, 0));
            spaceship.spaceshipBody->setAngularVelocity(btVector3(0, 0, 0));
        }
        while(updateTimer.elapsedTime()<8) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
        }
        updateTimer.start();
    }
};


int main() {
    Display display(WIDTH, HEIGHT, "OpenGL");

    Text text, textBlue;
    text.init("./../res/fonts/HolsteinRed.DDS");
    textBlue.init("./../res/fonts/HolsteinBlue.DDS");
    AudioEngine audioEngine;
    audioEngine.init();
    Music music = audioEngine.loadMusic("./../res/audio/TimeandSpace.mp3");
    SoundEffect weaponfire = audioEngine.loadSoundEffect("./../res/audio/sfx/weaponfire7.wav");

    Shader shader("./../res/mainShader");
    Shader shader2("./../res/mainShader2");
    Shader skyboxShader("./../res/skyboxShader");
    Shader orbitShader("./../res/shaders/orbit");

    ObjectHandler obj;
    //obj.addModel("./../res/spaceship/spaceshipp.dae", "spaceship");
    obj.addModel("./../res/spaceship/spaceship.obj", "spaceship");
    obj.addModel("./../res/models/moon/earth1.obj", "moon");
    obj.addModel("./../res/models/saturn/saturnRing.obj", "saturnRing");
    obj.addModel("./../res/models/bullet/bullet.obj", "bullet");


    obj.addModel("./../res/models/asteroid/Rock/rock1.obj", "rock1");
    obj.addModel("./../res/models/asteroid/Rock/rock2.obj", "rock2");
    obj.addModel("./../res/models/asteroid/desertRock/desertRock.obj", "rock4");
    obj.addModel("./../res/models/asteroid/desertRock/desertRock.obj", "rock5");
    obj.addModel("./../res/models/asteroid/Rock/rock2.obj", "rock6");
    obj.addModel("./../res/models/asteroid/Rock/rock1.obj", "rock7");
    obj.addModel("./../res/models/asteroid/Rock/rock2.obj", "rock8");
    obj.addModel("./../res/models/asteroid/desertRock/desertRock.obj", "rock3");


    Lights light[2]{
            vec4(0.1f, 0.1f, 0.1f, 1.0f),
            vec4(1.0f, 1.0f, 1.0f, 1.0f),
            vec3(0.0f, 3.0f, 0.0f),
            // vec3(1.0f, 0.09f, 0.032f),
            vec3(1.0f, 0.00f, 0.0f),

            vec4(0.1f, 0.1f, 0.0f, 1.0f),
            vec4(1.0f, 0.1f, 0.1f, 1.0f),
            vec3(0.0f, 3.0f, -15.0f),
            vec3(1.0f, 0.09f, 0.032f),
    };
    vector<Planet> loadedPlanets;

    std::ifstream file;
    string line, word;
    file.open("./../res/planetinfo.txt");
    if (file.is_open()) {
        for (int lineno = 0; file.good(); lineno++) {
            getline(file, line);
            istringstream _line(line);
            for (int i = 0; _line >> word; i++) {
                if (!word.compare("Planet")) break;
                string name = word;
                _line >> word;
                string path = "./../res/" + word;
                _line >> word;
                float radius = std::stof(word);
                _line >> word;
                float mass = std::stof(word);
                _line >> word;
                float rotation = std::stof(word);
                _line >> word;
                float revolution = std::stof(word);
                _line >> word;
                float gravity = std::stof(word);
                _line >> word;
                float distance = std::stof(word);
                _line >> word;
                int parent = std::stoi(word);

                loadedPlanets.push_back(Planet(name, path, radius, mass, rotation, revolution, gravity, distance));
                loadedPlanets[loadedPlanets.size()-1].parent=parent-1;
                break;
            }
        }
    } else {
        std::cerr << "Unable to read file " << std::endl;
    }
    for (int i = 0; i < loadedPlanets.size(); i++) {
        obj.addModel(loadedPlanets[i].getLocation(), loadedPlanets[i].getName());
    }
    Light light1(shader.Program);
    light1.addLight(light[0]);
    light1.addLight(light[1]);


    GLuint skyboxVAO, cubemapTexture;
    setupSkybox(skyboxVAO, cubemapTexture);
    GLuint orbitVAO[10];

    float rad = 5;
    for (int i = 0; i < loadedPlanets.size(); i++) {
        setupOrbit(orbitVAO[i], loadedPlanets[i].getDistance());
    }

    struct KineticBody {
        float mass;
        float radius;
        vec3 distance;
        vec3 up;
        vec3 force;
    };

    ////loopable
    Camera camera;
    do {

        music.play(0);
        reRunProgram = false;
        bool enablePhysics = false;
        Timer timer;
        std::srand((int) time(0));


        float relativeTime = 0.0f;
        Physics phy;
        phy.initPhysics();
        //initPhysics();
        Spaceship spaceship;

        thread actionThread(eventHandleThread, std::ref(camera), std::ref(display), std::ref(phy), std::ref(spaceship));

        int fpsCount = 0;
        timer.start();


        for (int i = 0; i < loadedPlanets.size(); i++) {
            float randomF = RandomFloat(0.0f, 3.14159265f);
            //randomF=0.0f;
            float x = sin(randomF) * loadedPlanets[i].getDistance();
            float z = cos(randomF) * loadedPlanets[i].getDistance();

            loadedPlanets[i].setTranslate(vec3(x, 0.0f, z));
            loadedPlanets[i].setScale(loadedPlanets[i].getRadius() / 1000000.0f/6.0f);

            if (i == loadedPlanets[0].parent) {
                phy.addSphere(loadedPlanets[i].radius / radiusFactor / 10.0f, x, 0.0f, z,
                          loadedPlanets[i].mass*10000.0f); /// this is for viewing
                /*phy.phy.addSphere(loadedPlanets[i].radius / radiusFactor / 10.0f, x, 0.0f, z,
                              loadedPlanets[i].mass);*/
            } else {
                phy.addSphere(loadedPlanets[i].radius / radiusFactor, x, 0.0f, z,
                          loadedPlanets[i].mass*10000.0f); /// this is for viewing
                /*phy.phy.addSphere(loadedPlanets[i].radius / radiusFactor, x, 0.0f, z,
                              loadedPlanets[i].mass);*/
                ///thisHasMagicNumber phy.addSphere(loadedPlanets[i].radius / 2000.0f, x / 10.0f, 0.0f / 10.0f, z / 10.0f, 1.0f);
            }
            //phy.bodies[i]->setLinearVelocity(btVector3(0, 0, 0));
        }

        spaceship.position=vec3(500.0f, 0.0f, 0.0f);
        spaceship.viewDirection = vec3(1,0,0);
        spaceship.strafeDirection = vec3(0,0,1);
        spaceship.radius = 0.50f;
        spaceship.force = vec3();
        spaceship.mass = 8.0f;
        phy.addSphere(spaceship.radius, spaceship.position.x,spaceship.position.y,spaceship.position.z,spaceship.mass);
        spaceship.spaceshipBody = phy.bodies[phy.bodies.size() - 1];

        /*KineticBody kbody[1];
        kbody[0].radius = 2 * radiusFactor;
        kbody[0].distance = vec3(500.0f, 0.0f, 0.0f);
        kbody[0].mass = 1.0f;
        kbody[0].up = vec3(0, 1.0f, 0);
        kbody[0].force = vec3(0, 0, 0);
        //phy.addSphere(2.0f, 0.0f, 0.0f, 50.0f, 1.0f, btQuaternion(0, -PI / 2, 0));
        phy.addSphere(2.0f, 500.0f, 0.0f, 0.0f, 1.0f);
        */
        spaceship.spaceshipBody->activate(true);
        setupAsteroidData(300.0f,700.0f,std::ref(phy));
//        thread asteroidThread([](){},);


        mat4 projection, view, model2view, view2proj, model2proj;
        vec3 eyePosView;
        glm::vec3 bulletDirection;
        glm::vec3 bulletInitialPosition;
        glm::mat4 bulletrotation;
        bool fireBullet;
        const float timeFactor = 0.1f; //this much days in one second
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
//        cout << "asteroids = "<<asteroids.size();


        while (true) {
            /*if(!display.IsMainMenuClosed()) displayMainMenu(projection, camera, skyboxShader.Program, skyboxVAO,
                                                            cubemapTexture, text, textBlue, display, timer);*/
            display.Clear(0.15f, 0.15f, 0.15f, 1.0f);
            relativeTime += 1.0f / 63.0f;
            if(display.capsLock()){
                phy.cameraBody->setLinearVelocity(btVector3()); //STOP MOVING THE FREE CAMERA
                myTransform = spaceship.spaceshipBody->getWorldTransform();
                myVector = myTransform.getOrigin();
                vec3 newCameraPos = getGlmVec3(myVector)-spaceship.viewDirection*2.0f;
                camera = static_cast<Camera>(spaceship);
                camera.position = newCameraPos;
                /*
                camera.viewDirection = spaceship.viewDirection;
                camera.strafeDirection = spaceship.strafeDirection;
                camera.Up = spaceship.Up;
                camera.toRotate = spaceship.toRotate;*/
                eyePosView = camera.getPosition();
            }else{
                eyePosView = getGlmVec3(phy.cameraBody->getWorldTransform().getOrigin());
                camera.position = eyePosView;

            }

            projection = glm::infinitePerspective(glm::radians(60.0f), (float) WIDTH / (float) HEIGHT, 0.1f);
            //projection = glm::perspective(glm::radians(60.0f), (float) WIDTH / (float) HEIGHT, 0.1f, 20.0f);
            view = camera.getWorldToViewMatrix();
            view2proj = projection * view;
            model2view = mat4();
            model2proj = mat4();
/////////////////-------------------------------------------
            if(!display.IsMainMenuClosed()) displayMainMenu(projection, camera, skyboxShader.Program, skyboxVAO,
                                                            cubemapTexture, text, textBlue, display, timer);
            displaySkybox(projection, camera, skyboxShader.Program, skyboxVAO, cubemapTexture);
            text.display("Go to Jupiter", 0, 550, 20);


            shader.Use();
            glUniform3fv(EYEPOSVIEW, 1, &eyePosView[0]);

            light1.shineBrightLikeADiamond(0);
            light1.shineBrightLikeADiamond(1);

                spaceship.force = vec3();
                for (int j = 0; j < loadedPlanets.size(); j++) {
                    float cons = (GCONST * loadedPlanets[j].mass * spaceship.mass
                                  / pow(glm::distance(loadedPlanets[j].getTranslation(), spaceship.position), 2));
                    spaceship.force += glm::normalize(loadedPlanets[j].getTranslation() - spaceship.position) * cons;

                }


            for (int i = 0; i < loadedPlanets.size(); i++) {
                if(i==9) continue;
               // cout<<"Displaying "<<loadedPlanets[i].planetName;
               // cout<<"   parent "<<loadedPlanets[i].parent<<endl;
                model2view = mat4();
                if(loadedPlanets[i].parent!=9){
                    myTransform = phy.bodies[loadedPlanets[i].parent]->getWorldTransform();
                    model2view = glm::translate(model2view, getGlmVec3(myTransform.getOrigin()));
                }
                model2view = glm::rotate(model2view,
                                         relativeTime / loadedPlanets[i].getRevolution() * timeFactor * PIX2 / 365,
                                         glm::vec3(0.0f, 1.0f, 0.0f));
                model2view = glm::translate(model2view, loadedPlanets[i].getTranslation());
                //model2view = glm::translate(model2view, loadedPlanets[loadedPlanets[i].parent].getTranslation());
                model2view = glm::rotate(model2view,
                                         relativeTime / loadedPlanets[i].getRotate() * timeFactor * PIX2,
                                         glm::vec3(0.0f, 1.0f, 0.0f));
                myTransform.setFromOpenGLMatrix(&model2view[0][0]);
                //both necessary
                phy.bodies[i]->setWorldTransform(myTransform);
                phy.bodies[i]->getMotionState()->setWorldTransform(myTransform);
                //model2view = glm::scale(model2view, loadedPlanets[i].getScaling() * 10.0f);
                model2view = glm::scale(model2view, loadedPlanets[i].getScaling()*5.0f);

                model2proj = view2proj * model2view;
                glUniformMatrix4fv(MODELTOPROJECTION, 1, GL_FALSE, &model2proj[0][0]);
                glUniformMatrix4fv(MODELTOVIEW, 1, GL_FALSE, &model2view[0][0]);
                obj.model[loadedPlanets[i].getName()]->Draw(shader.Program);
            }
///This is for sun
            shader2.Use();
            model2view = mat4();
            myTransform = phy.bodies[loadedPlanets[0].parent]->getWorldTransform();
            myTransform.getOpenGLMatrix(matrix);
            model2view = glm::make_mat4(matrix);
            model2view = glm::scale(model2view, loadedPlanets[loadedPlanets[0].parent].getScaling() /1.0f);
            model2proj = view2proj * model2view;
            glUniformMatrix4fv(glGetUniformLocation(shader2.Program, "model2projection"), 1, GL_FALSE,&model2proj[0][0]);
            glUniformMatrix4fv(glGetUniformLocation(shader2.Program, "model2view"), 1, GL_FALSE, &model2view[0][0]);
            obj.model[loadedPlanets[loadedPlanets[0].parent].getName()]->Draw(shader2.Program);

            shader.Use();
            btVector3 force = getBtVec3(spaceship.force);
            phy.bodies[loadedPlanets.size()]->applyCentralForce(force);

            model2view = mat4();
            myTransform = spaceship.spaceshipBody->getWorldTransform();
            myTransform.getOpenGLMatrix(matrix);
            model2view = glm::make_mat4(matrix);
            model2view = glm::rotate(model2view,PI/2, vec3(0,1,0));
            model2view = glm::scale(model2view, vec3(0.002, 0.002, 0.002));
            model2proj = view2proj * model2view;
            glUniformMatrix4fv(MODELTOPROJECTION, 1, GL_FALSE, &model2proj[0][0]);
            glUniformMatrix4fv(MODELTOVIEW, 1, GL_FALSE, &model2view[0][0]);
            obj.model["spaceship"]->Draw(shader2.Program);
            spaceship.position = getGlmVec3(myTransform.getOrigin());


            //cout << "asteroids = "<<asteroids.size();
            for(int i = 0; i<asteroids.size(); i++){
                model2view = mat4();
                myTransform = phy.bodies.at(asteroids[i].bulletPosition)->getWorldTransform();
                if(glm::distance(eyePosView,getGlmVec3(myTransform.getOrigin()))>200.0f) continue;
                myTransform.getOpenGLMatrix(matrix);
                model2view = glm::make_mat4(matrix);
                model2view = glm::scale(model2view, vec3(asteroids[i].radius*5.0f));
                model2proj = view2proj * model2view;
                glUniformMatrix4fv(MODELTOPROJECTION, 1, GL_FALSE, &model2proj[0][0]);
                glUniformMatrix4fv(MODELTOVIEW, 1, GL_FALSE, &model2view[0][0]);
                obj.model["rock"+std::to_string(asteroids[i].type+1)]->Draw(shader.Program);
            }

            model2view = mat4();
            model2view = glm::rotate(model2view,
                                     relativeTime / loadedPlanets[5].getRevolution() * timeFactor * PIX2 / 365,
                                     glm::vec3(0.0f, 1.0f, 0.0f));
            model2view = glm::translate(model2view, loadedPlanets[5].getTranslation());
            model2view = glm::rotate(model2view, glm::radians(-10.0f), glm::vec3(1.0f, 0.0f, 1.0f));
            model2view = glm::rotate(model2view, relativeTime / loadedPlanets[5].getRotate() * timeFactor * PIX2,
                                     glm::vec3(0.0f, 1.0f, 0.0f));
            model2view = glm::scale(model2view, glm::vec3(2.0f, 2.0f, 2.0f));
            model2proj = view2proj * model2view;
            glUniformMatrix4fv(MODELTOPROJECTION, 1, GL_FALSE, &model2proj[0][0]);
            glUniformMatrix4fv(MODELTOVIEW, 1, GL_FALSE, &model2view[0][0]);
            obj.model["saturnRing"]->Draw(shader.Program);

            //** Orbits Draw ......**//
            model2view = glm::mat4();
            model2view = glm::translate(model2view, glm::vec3(0.0f, 0.0f, 0.0f));
            model2proj = view2proj * model2view;
            for (int i = 0; i < 10; i++) {
                displayOrbit(loadedPlanets[i].getDistance(),model2proj, camera, orbitShader.Program, orbitVAO[i]);
            }

            text.display("Mission Space", 0, 550, 20);
            text.display(("FUEL: " + to_string(fuelPercent -= 0.001f)).c_str(), 0, 0, 15);


            shader.Use();

            //Audio.........///////// Audio  /////////
            if (shouldWeaponFire) {
                weaponfire.play();
                bulletDirection = camera.viewDirection;
                bulletInitialPosition = camera.position;
                fireBullet = 1;
                shouldWeaponFire = false;
            }
            if (fireBullet) {
                model2view = glm::mat4();
                model2view = glm::translate(model2view, bulletInitialPosition +
                                                        (bulletDirection *= 1.01f) /*+ (camera.viewDirection * 3.5f)*/);
                model2view = glm::rotate(model2view, glm::radians(90.0f), glm::vec3(0.0f, 1.0f, 0.0f));
                //model2view = glm::rotate(model2view, glm::radians())
                model2view = glm::scale(model2view, glm::vec3(0.0051f, 0.0051f, 0.0051f));
                model2proj = view2proj * model2view;
                glUniformMatrix4fv(glGetUniformLocation(shader.Program, "model2projection"), 1, GL_FALSE,
                                   &model2proj[0][0]);
                glUniformMatrix4fv(glGetUniformLocation(shader.Program, "model2view"), 1, GL_FALSE, &model2view[0][0]);
                obj.model["bullet"]->Draw(shader.Program);
            }
            // cout << camera.position.x << ',' << camera.position.y << ',' << camera.position.z << endl;




/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------
/////////////////-------------------------------------------

            phy.myWorld->stepSimulation(1 / 63.0f);
/////////////////-------------------------------------------
            int numManifolds = phy.myWorld->getDispatcher()->getNumManifolds();
            for (int i = 0; i < numManifolds; i++) {
                btPersistentManifold *contactManifold = phy.myWorld->getDispatcher()->getManifoldByIndexInternal(i);
                {
                    const btCollisionObject *obj1 = contactManifold->getBody0();
                    const btCollisionObject *obj2 = contactManifold->getBody1();
                    myTransform = obj1->getWorldTransform();
                    myVector = myTransform.getOrigin();
                    int indexOf1=obj1->getUserIndex();
                    // cout << "obj1Loc=" << myVector.x() << " , " << myVector.y() << " , " << myVector.z() << "\n";
                    myTransform = obj2->getWorldTransform();
                    myVector = myTransform.getOrigin();
                    int indexOf2=obj2->getUserIndex();
                    // cout << "obj1Loc=" << myVector.x() << " , " << myVector.y() << " , " << myVector.z() << "\n";

                    //from the index find collision objects

                    float r1 = 0.0f, r2 = 0.0f;
                    if (obj1->getCollisionShape()->getShapeType() == SPHERE_SHAPE_PROXYTYPE) {
                        float r = ((btSphereShape *) obj1->getCollisionShape())->getRadius();
                        r1 = r * radiusFactor;
                        // cout << "Sphere--> radius=" << r * radiusFactor << "\n";
                    }
                    if (obj2->getCollisionShape()->getShapeType() == SPHERE_SHAPE_PROXYTYPE) {
                        float r = ((btSphereShape *) obj2->getCollisionShape())->getRadius();
                        r2 = r * radiusFactor;
                        // cout << "Sphere--> radius=" << r * radiusFactor << "\n";
                    }

                    if ((r1 + r2) == (6051.8 + 2439.7)) {
                        reRunProgram = true;
                        cout << "Mercury & Venus __________________\n";
                    }
                }
                /*int numContacts = contactManifold->getNumContacts();
                for (int j = 0; j < numContacts; j++) {
                    btManifoldPoint &pt = contactManifold->getContactPoint(j);
                    const btVector3 &ptA = pt.getPositionWorldOnA();
                    const btVector3 &ptB = pt.getPositionWorldOnB();
                    const btVector3 &normalOnB = pt.m_normalWorldOnB;
                }*/
            }
/////////////////-------------------------------------------



            //////FPS Managed from down here..
            if (timer.elapsedTime() > 999) {
                cout << "FPS = " << fpsCount << " in time (ms) " << timer.elapsedTime() << std::endl;
                timer.start();
                fpsCount = 0;
            }
            display.Update();
            display.registerEvent();
            for (long et = timer.elapsedTime(); et < 16 * (fpsCount + 1) && et < 1000;) {
                //display.registerEvent();
                std::this_thread::sleep_for(
                        std::chrono::milliseconds(1));
                et = timer.elapsedTime();
            }
            fpsCount++;
            if (display.IsClosed()) break;
            if (reRunProgram) {
                phy.deleteObjects();
                break;
            }
        }
        actionThread.join();
        if (display.m_isClosed)break;
    } while (true);

    glDeleteProgram(shader.Program);


    return 0;
}


void setupSkybox(GLuint &skyboxVAO, GLuint &cubemapTexture) {

    std::function<GLuint(GLchar *)> loadTexture = [](GLchar *path) {
        //Generate texture ID and load texture data
        GLuint textureID;
        glGenTextures(1, &textureID);
        int width, height;
        unsigned char *image = SOIL_load_image(path, &width, &height, 0, SOIL_LOAD_RGB);
        // Assign texture to ID
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);

        // Parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glBindTexture(GL_TEXTURE_2D, 0);
        SOIL_free_image_data(image);
        return textureID;
    };


    std::function<GLuint(vector<const GLchar *>)> loadCubemap = [](vector<const GLchar *> faces) {
        GLuint textureID;
        glGenTextures(1, &textureID);

        int width, height;
        unsigned char *image;

        glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
        for (GLuint i = 0; i < faces.size(); i++) {
            image = SOIL_load_image(faces[i], &width, &height, 0, SOIL_LOAD_RGB);
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE,
                         image);
            SOIL_free_image_data(image);
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);

        return textureID;
    };


    GLfloat cubeVertices[] = {
            // Positions          // Texture Coords
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
    };
    GLfloat skyboxVertices[] = {
            // Positions
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f
    };

    // Setup cube VAO
    GLuint cubeVAO, cubeVBO;
    glGenVertexArrays(1, &cubeVAO);
    glGenBuffers(1, &cubeVBO);
    glBindVertexArray(cubeVAO);
    glBindBuffer(GL_ARRAY_BUFFER, cubeVBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(cubeVertices), &cubeVertices, GL_STATIC_DRAW);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 5 * sizeof(GLfloat), (GLvoid *) 0);
    glEnableVertexAttribArray(1);
    glVertexAttribPointer(1, 2, GL_FLOAT, GL_FALSE, 5 * sizeof(GLfloat), (GLvoid *) (3 * sizeof(GLfloat)));
    glBindVertexArray(0);
    // Setup skybox VAO
    GLuint skyboxVBO;
    glGenVertexArrays(1, &skyboxVAO);
    glGenBuffers(1, &skyboxVBO);
    glBindVertexArray(skyboxVAO);
    glBindBuffer(GL_ARRAY_BUFFER, skyboxVBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(skyboxVertices), &skyboxVertices, GL_STATIC_DRAW);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid *) 0);
    glBindVertexArray(0);

    // Load textures
    GLuint cubeTexture = loadTexture((GLchar *) "./../res/skybox/shishirUP.png");

    // Cubemap (Skybox)
    vector<const GLchar *> faces;
    faces.push_back(
            "./../res/skybox/shishirRT.png");
    faces.push_back("./../res/skybox/shishirLF.png");
    faces.push_back("./../res/skybox/shishirUP.png");
    faces.push_back("./../res/skybox/shishirDN.png");
    faces.push_back("./../res/skybox/shishirFT.png");
    faces.push_back("./../res/skybox/shishirBK.png");
    cubemapTexture = loadCubemap(faces);


}

void displaySkybox(const glm::mat4 &projection, const Camera &camera, unsigned int skyboxShader, GLuint &skyboxVAO,
                   GLuint &cubemapTexture) {
    glDepthFunc(GL_LEQUAL);
    glUseProgram(skyboxShader);
    glm::mat4 view = glm::mat4(
            glm::mat3(camera.getWorldToViewMatrix()));    // Remove any translation component of the view matrix
    glUniformMatrix4fv(glGetUniformLocation(skyboxShader, "view"), 1, GL_FALSE, glm::value_ptr(view));
    glUniformMatrix4fv(glGetUniformLocation(skyboxShader, "projection"), 1, GL_FALSE, glm::value_ptr(projection));
    // skybox cube
    glBindVertexArray(skyboxVAO);
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapTexture);
    glDrawArrays(GL_TRIANGLES, 0, 36);
    glBindVertexArray(0);
    glDepthFunc(GL_LESS); // Set depth function back to default
}

void setupOrbit(GLuint &orbitVAO, float radius, float h, float k) {
    //float theta = 0;
    float x, y = 0, z;
    int noOfDashes = (int) (radius);
    float orbitVertices[noOfDashes * 3];
    int i = 0;
    float increament = 360.0f/noOfDashes;
    float theta = 0.0f;
    for (; theta < 360.0f; theta+=increament) {
        x = radius * glm::cos(glm::radians(theta));
        z = radius * glm::sin(glm::radians(theta));

        orbitVertices[i++] = x;
        orbitVertices[i++] = y;
        orbitVertices[i++] = z;
    }
    GLuint orbitVBO;
    glGenVertexArrays(1, &orbitVAO);
    glGenBuffers(1, &orbitVBO);
    glBindVertexArray(orbitVAO);
    glBindBuffer(GL_ARRAY_BUFFER, orbitVBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(orbitVertices), &orbitVertices, GL_STATIC_DRAW);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), (GLvoid *) 0);
    glBindVertexArray(0);

}

void displayOrbit(float radius, const glm::mat4 &projection, const Camera &camera, unsigned int orbitShader, GLuint &orbitVAO) {
    glUseProgram(orbitShader);
    //glm::mat4 matri; = glm::mat4(glm::mat3(camera.getWorldToViewMatrix()));
    glUniformMatrix4fv(glGetUniformLocation(orbitShader, "model2view"), 1, GL_FALSE, glm::value_ptr(projection));
    glBindVertexArray(orbitVAO);
    glDrawArrays(GL_POINTS, 0, static_cast<int>(radius) * 3);
    glBindVertexArray(0);
}

void displayMainMenu(glm::mat4 &projection,Camera &camera, GLuint &skyboxShader, GLuint &skyboxVAO, GLuint &cubemapTexture,
                     Text redText, Text blueText, Display &display, Timer &maintimer)
{
    //SDL_SetRelativeMouseMode(SDL_FALSE);
    SDL_Event mainEvent;
    int fpsCount = 0;
    Timer timer;
    timer.start();
    float x=0.0f, y;
    cout << "main menu opened";
    while(!display.IsMainMenuClosed()) {
//        cout << "main menu looop opened";
        display.Clear(0.15f, 0.55f, 0.15f, 1.0f);
        displaySkybox(projection, camera, skyboxShader, skyboxVAO, cubemapTexture);

        redText.display("MISSION SPACE", 200 + 200 * glm::sin(x += 0.01f), 550, 30);

        playColor = 'r';
        helpColor = 'r';
        settingColor = 'r';
        aboutColor = 'r';
        exitColor = 'r';

        switch(menuItem) {
            case 1:
                playColor = 'b';    break;
            case 2:
                helpColor = 'b';    break;
            case 3:
                settingColor = 'b';    break;
            case 4:
                aboutColor = 'b';    break;
            case 5:
                exitColor = 'b';    break;

        }

        if (playColor == 'r')            redText.display("Play (P)", 0, 500, 25);
        else                             blueText.display("Play (P)", 0, 500, 25);
        if (helpColor == 'r')            redText.display("Help (H)", 0, 450, 25);
        else                             blueText.display("Help (H)", 0, 450, 25);
        if (settingColor == 'r')         redText.display("Setting (S)", 0, 400, 25);
        else                             blueText.display("Setting (S)", 0, 400, 25);
        if (aboutColor == 'r')           redText.display("About (A)", 0, 350, 25);
        else                             blueText.display("About (A)", 0, 350, 25);
        if (exitColor == 'r')            redText.display("Exit (Esc)", 0, 300, 25);
        else                             blueText.display("Exit (Esc)", 0, 300, 25);

        if(showHelp) {
            redText.display("Press W to move FORWARD.", 300, 300+200, 20);
            redText.display("Press A/S to rotate spaceship", 300, 250+200, 20);
            redText.display("Use mouse to view around", 300, 200+200, 20);
        }

        if(showAbout) {
            redText.display("Game Made by: ", 300, 300+200, 20);
            redText.display("Shishir Bhandari", 300, 250+200, 20);
            redText.display("Suman Sapkota", 300, 200+200, 20);
            redText.display("Rohan Thapa", 300, 150+200, 20);
        }

        //////FPS Managed from down here..
        if (timer.elapsedTime() > 999) {
            cout << "FPS = " << fpsCount << " in time (ms) " << timer.elapsedTime() << std::endl;
            timer.start();
            fpsCount = 0;
        }
        display.Update();
        display.registerEvent();
        for (long et = timer.elapsedTime(); et < 16 * (fpsCount + 1) && et < 1000;) {
            //display.registerEvent();
            std::this_thread::sleep_for(
                    std::chrono::milliseconds(1));
            et = timer.elapsedTime();
        }
        fpsCount++;
        if (display.IsClosed()) break;
    }

}
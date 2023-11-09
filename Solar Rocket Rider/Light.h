#pragma once

#define GLEW_STATIC

#include <GL/glew.h>
#include <SDL2/SDL.h>
#undef main
#include <glm/glm.hpp>
#include <vector>
#include <iostream>


#define NOOFSOURCE 11
#define LIGHTS 50

using glm::vec3;
using glm::vec4;
using glm::mat4;
using glm::mat3;
using std::vector;
using std::string;

struct Lights{
    vec4 ambientLight;
    vec4 lightSourceColor;
    vec3 lightPosView;
    vec3 quadConsts;
};
class Light{
private:
    GLint program;
    bool lightSent;
    vector<Lights> lightCollec;
public:
    Light(GLint programID):program(programID), lightSent(false){}
    void addLight(Lights lightCollection){
        lightCollec.push_back(lightCollection);
        lightSent=false;
    }
    void addLight(vec4 ambientLight,
                  vec4 lightSourceColor,
                  vec3 lightPosView,
                  vec3 quadConsts){
        Lights lighhh;
        lighhh.ambientLight=ambientLight;
        lighhh.lightSourceColor=lightSourceColor;
        lighhh.lightPosView=lightPosView;
        lighhh.quadConsts=quadConsts;
        lightCollec.push_back(lighhh);
        lightSent= false;
    }
    void shineBrightLikeADiamond(int index){
        passThroughUniform(index);
    }

private:
    void passThroughUniform(int index){
        int startfrom =LIGHTS+ index*4;
        glUniform4fv(startfrom+0, 1,&(lightCollec[index].ambientLight[0]));
        glUniform4fv(startfrom+1, 1, &(lightCollec[index].lightSourceColor[0]));
        glUniform3fv(startfrom+2, 1, &(lightCollec[index].lightPosView[0]));
        glUniform3fv(startfrom+3, 1, &(lightCollec[index].quadConsts[0]));
        //glUniform4fv(glGetUniformLocation(program, "light[0].ambientLight"), 1,&(lightCollec[index].ambientLight[0]));
        //glUniform4fv(glGetUniformLocation(program, "light[0].lightSourceColor"), 1, &(lightCollec[index].lightSourceColor[0]));
        //glUniform3fv(glGetUniformLocation(program, "light[0].lightPosView"), 1, &(lightCollec[index].lightPosView[0]));
        //glUniform3fv(glGetUniformLocation(program, "light[0].quadConsts"), 1, &(lightCollec[index].quadConsts[0]));

        if(!lightSent){
            glUniform1i(NOOFSOURCE, lightCollec.size());
            lightSent = true;
        }
    }
};

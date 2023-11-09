#pragma once

#define GLEW_STATIC
#include<GL/glew.h>
#include <glm/glm.hpp>
#include "Shader.h"

using namespace glm;

class UniformLocation {
private:
    GLint location;
public:
    UniformLocation();
    UniformLocation(Shader& shader, const GLchar *uniformVariableName){
        location=glGetUniformLocation(shader.Program,uniformVariableName);
    }
    GLint getLocation(){
        return location;
    }
    void sendData(vec3& vector){
        glUniform3fv(location,1,&vector[0]);
    }
    void sendData(vec4& vector){
        glUniform4fv(location,1,&vector[0]);
    }
    void sendData(mat4& matrix){
        glUniformMatrix4fv(location,1,GL_FALSE,&matrix[0][0]);
    }
};
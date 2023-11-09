#pragma once

#define GLEW_STATIC
#include<GL/glew.h>
#include<iostream>
#include <string>
#include <map>


class Shader
{
public:
    GLuint Program;
    Shader(const std::string& filename);
    void Use();
    virtual ~Shader();
};

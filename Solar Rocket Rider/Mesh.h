#pragma once

#define GLEW_STATIC
#include <GL/glew.h>
#include <glm/glm.hpp>
#include <iostream>
#include <vector>
#include "Shader.h"
#include <assimp/Importer.hpp>
#include <assimp/scene.h>
#include <assimp/postprocess.h>

using namespace std;

struct Vertex {
    glm::vec3 Position;
    glm::vec3 Normal;
    glm::vec2 TexCoords;
};

struct Texture {
    GLuint id;
    string type;
    aiString path;
};

class Mesh {
public:
    vector<Vertex> vertices;
    vector<GLuint> indices;
    vector<Texture> textures;

    Mesh(vector<Vertex> vertices, vector<GLuint> indices, vector<Texture> textures);
    void Draw(unsigned int Program);
    void Clear(){
        vertices.clear();
        indices.clear();
        textures.clear();
    }
private:
    GLuint VAO, VBO, EBO; //Vertex Array Object             Buffer Location
    void setupMesh();
};

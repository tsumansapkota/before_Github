#pragma once

#define GLEW_STATIC
#include <GL/glew.h>
#include <glm/glm.hpp>
#include "Shader.h"
#include "Mesh.h"

#include <assimp/Importer.hpp>
#include <assimp/scene.h>
#include <assimp/postprocess.h>
#include <SOIL.h>

GLint TextureFromFile(const char* path, string directory);

class Model
{
public:
    Model(std::string path)
    {
        this->loadModel(path);
    }
    void Draw(unsigned int shader);
    void Clear(){
        for(int i=0; i<meshes.size(); i++)
            meshes[i].Clear();
        textures_loaded.clear();
        meshes.clear();
        directory="";
    }

private:
    vector<Texture> textures_loaded;
    vector<Mesh> meshes;
    string directory;
    void loadModel(string path);
    void processNode(aiNode* node, const aiScene* scene);
    Mesh processMesh(aiMesh* mesh, const aiScene* scene);
    vector<Texture> loadMaterialTextures(aiMaterial* mat, aiTextureType type,
                                         string typeName);
};
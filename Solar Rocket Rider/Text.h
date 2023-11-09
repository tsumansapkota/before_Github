#ifndef TEXT_H
#define TEXT_H

#include "Texture.h"
#include "Shader.h"

class Text
{
    public:
        /** Default constructor */
        Text();
        /** Default destructor */
        ~Text();

        void init(const GLchar* path);
        void display(const char* text, int x, int y, int size);
        void cleanUp();

    protected:

    private:
        Shader textShader;

        GLuint Text2DTextureID;
        unsigned int Text2DVertexBufferID;
        unsigned int Text2DUVBufferID;
        unsigned int Text2DShaderID;
        unsigned int Text2DUniformID;
};

#endif // TEXT_H

#ifndef TEXTURE_H
#define TEXTURE_H

#include <SOIL.h>
#include <GL/glew.h>
#include <vector>
using namespace std;

namespace  shishir {
    class Texture
    {
    public:
        /** Default constructor */
        Texture();
        /** Default destructor */
        ~Texture();

        static GLuint loadTexture(const GLchar* path);
        static GLuint loadCubeMap(vector<const GLchar*> faces);
        static GLuint loadDDS(const char * imagepath);
        static GLuint loadBMP_custom(const char * imagepath);

    protected:

    private:
    };
}

#endif // TEXTURE_H

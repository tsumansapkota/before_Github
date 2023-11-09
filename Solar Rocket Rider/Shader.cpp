#include "Shader.h"
#include <fstream>

static std::string LoadShader(const std::string& filename);
static void CheckShaderError(GLuint shader, GLuint flag, bool isProgram, const std::string& errorMessage);
static GLuint CreateShader(const std::string& text, GLenum shaderType);


Shader::Shader(const std::string& fileName)
{
    static const int NUM_SHADERS = 2;
    GLuint m_shadersID[NUM_SHADERS];

    Program = glCreateProgram();
    m_shadersID[0] = CreateShader(LoadShader(fileName + ".vs"), GL_VERTEX_SHADER);
    m_shadersID[1] = CreateShader(LoadShader(fileName + ".fs"), GL_FRAGMENT_SHADER);

    for (unsigned int i = 0; i < NUM_SHADERS; i++)
        glAttachShader(Program, m_shadersID[i]);

    glLinkProgram(Program);
    CheckShaderError(Program, GL_LINK_STATUS, true, "Error linking shader program");

    glValidateProgram(Program);
    CheckShaderError(Program, GL_LINK_STATUS, true, "Invalid shader program");

    for (unsigned int i = 0; i < NUM_SHADERS; i++)
        glDeleteShader(m_shadersID[i]);
}

Shader::~Shader()
{
    glDeleteProgram(Program);
}

void Shader::Use()
{
    glUseProgram(Program);
}

std::string LoadShader(const std::string& fileName)
{
    std::ifstream file;
    file.open((fileName).c_str());

    std::string output;
    std::string line;

    if (file.is_open())
    {
        while (file.good())
        {
            getline(file, line);
            output.append(line + "\n");
        }
    }
    else
    {
        std::cerr << "Unable to load shader: " << fileName << std::endl;
    }

    return output;
}

void CheckShaderError(GLuint shader, GLuint flag, bool isProgram, const std::string& errorMessage)
{
    GLint success = 0;
    GLchar error[1024] = { 0 };

    if (isProgram)
        glGetProgramiv(shader, flag, &success);
    else
        glGetShaderiv(shader, flag, &success);

    if (success == GL_FALSE)
    {
        if (isProgram)
            glGetProgramInfoLog(shader, sizeof(error), NULL, error);
        else
            glGetShaderInfoLog(shader, sizeof(error), NULL, error);

        std::cerr << errorMessage << ": '" << error << "'" << std::endl;
    }
}

GLuint CreateShader(const std::string& shaderText, unsigned int shaderType)
{
    GLuint shaderID = glCreateShader(shaderType);

    if (shaderID == 0)
        std::cerr << "Error compiling shader type " << shaderType << std::endl;

    const GLchar* adapter[1];
    adapter[0] = shaderText.c_str();
    GLint lengths[1];
    lengths[0] = shaderText.length();

    glShaderSource(shaderID, 1, adapter, lengths);
    glCompileShader(shaderID);

    CheckShaderError(shaderID, GL_COMPILE_STATUS, false, "Error compiling shader!");

    return shaderID;
}

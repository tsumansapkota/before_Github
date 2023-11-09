cmake_minimum_required(VERSION 3.6)
project(GameDev)

set(PROJECT_Flags "-pthread")

link_directories("${CMAKE_CURRENT_SOURCE_DIR}/bin")
link_libraries(mingw32 SDL2main SDL2 assimp SOIL SDL2_mixer)

find_package(GLEW REQUIRED)
if (GLEW_FOUND)
    link_libraries(glew32s OpenGL32)
endif(GLEW_FOUND)

link_libraries(BulletDynamics)
link_libraries(BulletCollision)
link_libraries(LinearMath)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 ${PROJECT_Flags}")
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}/bin")

include_directories("F:/Utilities/mingw64/include" "${CMAKE_CURRENT_SOURCE_DIR}/include")



set(SOURCE_FILES main.cpp Display.h Display.cpp Camera.cpp Camera.h Mesh.cpp Mesh.h
        Model.cpp Model.h Shader.h Shader.cpp UniformLocation.h Timer.h ObjectHandler.h Light.h PhysicsModel.h
        Texture.h Texture.cpp   Physics.h
        Planet.h Physics.h Text.h Text.cpp
        AudioEngine.h AudioEngine.cpp
        Spaceship.h)
add_executable(GameDev ${SOURCE_FILES})
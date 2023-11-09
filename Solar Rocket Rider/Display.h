#pragma once

#include <SDL2/SDL.h>
#undef main
#include <string>
#include <glm/glm.hpp>
#include <map>
#include <thread>

#define WIDTH 1366//960//1366//683//1366
//#define WIDTH 1366
#define HEIGHT 768//540//768//384//768
//#define HEIGHT 768

class Display {
public:
    Display(int width, int height, const std::string title);
    Display(){}
    void Update();
    virtual ~Display();
    bool IsClosed();
    bool IsMainMenuClosed();
    void Clear(float r, float g, float b, float a);

    bool m_isClosed = false;
    bool mainMenu_isClosed = false;
    glm::vec2 mousePosition;
    std::map<int, bool> KEYS;
    bool mouseMoved=false, mouseClickL=false, mouseClickR =false, eventRegistered=false;

    bool capsLock(){
        int temp = SDL_GetModState();
        temp = temp & KMOD_CAPS;
        return temp == KMOD_CAPS;
    }
    void registerEvent();

private:
    SDL_Window* m_window;
    SDL_GLContext m_glContext;
    SDL_Event event;
};


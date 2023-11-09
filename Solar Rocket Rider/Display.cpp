#include <iostream>
#include "Display.h"

#define GLEW_STATIC

#include <GL/glew.h>

Display::Display(int width, int height, const std::string title) {
    SDL_Init(SDL_INIT_EVERYTHING);

    SDL_GL_SetAttribute(SDL_GL_RED_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_GREEN_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_BLUE_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_ALPHA_SIZE, 8);
    SDL_GL_SetAttribute(SDL_GL_BUFFER_SIZE, 32);
    SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 16);
    SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);
    SDL_GL_SetAttribute(SDL_GL_MULTISAMPLESAMPLES, 4);

    m_window = SDL_CreateWindow(title.c_str(), SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height,
                                SDL_WINDOW_OPENGL);

    //SDL_SetWindowFullscreen(m_window, SDL_WINDOW_FULLSCREEN);

    m_glContext = SDL_GL_CreateContext(m_window);
    glewExperimental = GL_TRUE;
    GLenum status = glewInit();
    if (status != GLEW_OK) {
        std::cerr << "Glew failed to initialize\n";
    }
    m_isClosed = false;

    glViewport(0, 0, WIDTH, HEIGHT);

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_MULTISAMPLE);
    glDisable(GL_DEPTH_CLAMP);
    //(GL_NORMALIZE);
    //glEnable(GL_FRAMEBUFFER_SRGB);

    SDL_SetRelativeMouseMode(SDL_TRUE);

    for (int i = 0; i < 322; i++) {
        KEYS[i] = false;
    }

    /*thread1 = std::thread([this](){
        while (!this->m_isClosed) {
            //std::cout << "Lambda test thread is running\n";
            std::this_thread::sleep_for (std::chrono::milliseconds(1));
        }
    });*/


}

Display::~Display() {
    SDL_GL_DeleteContext(m_glContext);
    SDL_DestroyWindow(m_window);
    SDL_Quit();
    std::cout << "DisplayClosed\n";
}

bool Display::IsClosed() {
    return m_isClosed;
}

bool Display::IsMainMenuClosed() {
    return  mainMenu_isClosed;
}

void Display::Clear(float r, float g, float b, float a) {
    glClearColor(r, g, b, a);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clears color and depth buffer
    // glClear(GL_COLOR_BUFFER_BIT);
}

void Display::Update() {
    SDL_GL_SwapWindow(m_window);
    registerEvent();
}

void Display::registerEvent(){
    eventRegistered=true;
    while (SDL_PollEvent(&event)) {
        switch (event.type) {
            case SDL_QUIT:
                m_isClosed = true;
                break;
            case SDL_MOUSEMOTION:
/*
                int x, y, x2, y2;
                SDL_GetMouseState(&x, &y);
                SDL_GetRelativeMouseState(&x, &y);
                //SDL_WarpMouseInWindow(m_window,WIDTH/2,HEIGHT/2);
                std::cout<<"MouseMoved\n"<<"("<<x << " , " << y<<")\n";
*/
                mousePosition = glm::vec2(event.motion.xrel, event.motion.yrel);
                mouseMoved = true;
                break;
            case SDL_MOUSEBUTTONDOWN:
                if(event.button.button == SDL_BUTTON_LEFT)
                    mouseClickL=true;
                else if (event.button.button == SDL_BUTTON_RIGHT)
                    mouseClickR=true;
                break;
            case SDL_MOUSEBUTTONUP:
                if(event.button.button == SDL_BUTTON_LEFT)
                    mouseClickL= false;
                else if (event.button.button == SDL_BUTTON_RIGHT)
                    mouseClickR=false;
                break;
            case SDL_KEYDOWN:
                KEYS[event.key.keysym.sym] = true;
                if (KEYS[SDLK_ESCAPE]) {
                    m_isClosed = true;
                }
                break;
            case SDL_KEYUP:
                KEYS[event.key.keysym.sym] = false;
                break;
            default:
                break;
        }
    }

}

/*
void KeyListener(Camera &camera){
    while(!m_isClosed){
        SDL_Event event;
        while (SDL_PollEvent(&event)) {
            switch (event.type) {
                case SDL_QUIT:
                    m_isClosed = true;
                    break;
                case SDL_MOUSEMOTION:
                    int x, y;
                    SDL_GetMouseState(&x, &y);
                    SDL_GetRelativeMouseState(&x, &y);
                    mousePosition = glm::vec2(x, y);
                    mouseMoved = true;
                    break;
                case SDL_KEYDOWN:
                    KEYS[event.key.keysym.sym] = true;
                    if (KEYS[SDLK_ESCAPE]) {
                        m_isClosed = true;
                    }
                    break;
                case SDL_KEYUP:
                    KEYS[event.key.keysym.sym] = false;
                    break;
                default:
                    break;
            }
        }
        //checking the events upto here
        if(mouseMoved){
            camera.mouseUpdate(mousePosition);
            mouseMoved=false;
        }if(KEYS[SDLK_w]){
            camera.moveForeward();
        }else if(KEYS[SDLK_s]){
            camera.moveBackard();
        }if(KEYS[SDLK_a]){
            camera.starfeLeft();
        }else if(KEYS[SDLK_d]){
            camera.strafeRight();
        }if(KEYS[SDLK_r]){
            camera.moveUp();
        }else if(KEYS[SDLK_f]){
            camera.moveDown();
        }
        std::this_thread::sleep_for (std::chrono::milliseconds(1));
    }
}
*/

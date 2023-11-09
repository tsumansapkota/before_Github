#ifndef AUDIOENGINE_H
#define AUDIOENGINE_H

#include <SDL2/SDL_mixer.h>
#include <string>
#include <map>

class SoundEffect
{
public:
    friend class AudioEngine;
    void play(int loops = 0);

private:
    Mix_Chunk *chunk = NULL;

};

class Music
{
public:
    friend class AudioEngine;
    void play(int loops = -1);
    void pause();
    void resume();
    void stop();
    void setVolume(float vol);
    void increaseVolume( );
    void decreaseVolume( );

private:
    float volume = 20.0;
    Mix_Music *music = NULL;

};

class AudioEngine
{
    public:
        /** Default constructor */
        AudioEngine();
        /** Default destructor */
        ~AudioEngine();

        void init();
        void destroy();
        SoundEffect loadSoundEffect(const std::string& filePath);
        Music loadMusic(const std::string& filePath);

    protected:

    private:

        std::map<std::string, Mix_Chunk*> effectMap;
        std::map<std::string, Mix_Music*> musicMap;
        bool isInitialized;
};

#endif // AUDIOENGINE_H

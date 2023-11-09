#include "AudioEngine.h"
#include <iostream>

void SoundEffect::play(int loops /* = 0 */) // default:: plays one time
{
    Mix_VolumeChunk(chunk, 20);
    if( Mix_PlayChannel(-1, chunk, loops) == -1) {
        if( Mix_PlayChannel(0, chunk, loops) == -1)
            std::cout << "\nMix_PlayChannel error!" << std::endl;
    }
}

void Music::play(int loops /* = -1 */) //default:: loops forever
{
    if( Mix_PlayMusic(music, loops) == -1)
        std::cout << "\nMix_PlayMusic error!" << std::endl;
    setVolume(volume);
}

void Music::pause()
{
    Mix_PauseMusic();
}
void Music::resume()
{
    Mix_ResumeMusic();
    setVolume(volume);
}
void Music::stop()
{
    Mix_HaltMusic();
}

void Music::setVolume(float vol)
{
    volume = vol;
    if(volume >= 128.0)
        volume -= 1.0;
    volume *= 0.5;
    Mix_VolumeMusic(volume);
}
void Music::increaseVolume()
{
    volume += 2.0;
    setVolume(volume);

}
void Music::decreaseVolume()
{
    volume -= 2.0;
    setVolume(volume);
}

AudioEngine::AudioEngine()
{
    //ctor
}

AudioEngine::~AudioEngine()
{
    //dtor
    destroy();
}

void AudioEngine::init()
{
    if((Mix_Init(MIX_INIT_MP3 | MIX_INIT_OGG)) == -1) {
        std::cout << "\nCould not initialize SDL_Mixer" << std::endl;
    }

    if((Mix_OpenAudio(MIX_DEFAULT_FREQUENCY, MIX_DEFAULT_FORMAT, 2, 2048)) == -1) {
        std::cout << "\nCould not initialize SDL_Mixer" << std::endl;
    }
    isInitialized = true;
}
void AudioEngine::destroy()
{
    if(isInitialized)
    {
        isInitialized = false;
        Mix_CloseAudio();
        Mix_Quit();
    }
}
SoundEffect AudioEngine::loadSoundEffect(const std::string& filePath)
{
    auto it = effectMap.find(filePath);

    SoundEffect effect;

    if(it == effectMap.end()) {
        //Failed to find it. Must load.
        Mix_Chunk *chunk = Mix_LoadWAV(filePath.c_str());
        if(chunk == NULL)
            std::cout << "\nCould not open file: " << filePath << std::endl;

        effect.chunk = chunk;
        effectMap[filePath] = chunk;

    }
    else {
        //Its already cached
        effect.chunk = it->second;
    }

    return effect;
}

Music AudioEngine::loadMusic(const std::string& filePath)
{
    auto it = musicMap.find(filePath);

    Music tempMusic;

    if(it == musicMap.end()) {
        //Failed to find it. Must load.
        Mix_Music *mixMusic = Mix_LoadMUS(filePath.c_str());
        if(mixMusic == NULL)
            std::cout << "\nCould not open file: " << filePath << std::endl;

        tempMusic.music = mixMusic;
        musicMap[filePath] = mixMusic;

    }
    else {
        //Its already cached
        tempMusic.music = it->second;
    }

    return tempMusic;
}

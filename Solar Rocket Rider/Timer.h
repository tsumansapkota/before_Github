#pragma once

#include <ctime>

class Timer {
private:
    unsigned long begTime;
public:
    void start() {
        begTime = clock();
    }
    unsigned long elapsedTime() {
        return ((unsigned long) clock() - begTime);
    }
    bool isTimeout(unsigned long seconds) {
        return seconds >= elapsedTime();
    }
};
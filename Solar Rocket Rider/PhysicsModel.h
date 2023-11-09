#pragma once

#include <iostream>
#include "Model.h"
#include "Camera.h"

class PhysicsModel {
private:

public:
    std::vector<string> modellist;
    map<string, Model *> model;
    Camera* camera;

    void addModel(string pathToModel, string modelName = "") {
        if (modelName.length() > 0) {
            modellist.push_back(modelName);
            model[modelName] = new Model(pathToModel);
        } else {
            modellist.push_back("" + modellist.size());
            model["" + modellist.size()] = new Model(pathToModel);
        }
    }

    void removeModel(string modelName) {
        if (model.erase(modelName) > 0)
            modellist.erase(std::remove(modellist.begin(), modellist.end(), modelName), modellist.end());
    }

    void removeModel(int number) {
        if (model.erase(modellist[number]) > 0)
            modellist.erase(modellist.begin() + number);
    }


    PhysicsModel(Camera& cam) {
        model.clear();
        modellist.clear();
        camera = &cam;
    }

    ~PhysicsModel() {
        model.clear();
        modellist.clear();
    }

};
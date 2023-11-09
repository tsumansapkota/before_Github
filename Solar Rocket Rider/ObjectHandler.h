#pragma once

#include <iostream>
#include "Model.h"



class ObjectHandler {
public:
    std::vector<string> modellist;
    map<string, Model*> model;

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


    ObjectHandler() {
        model.clear();
        modellist.clear();
    }
    ~ObjectHandler() {
        model.clear();
        modellist.clear();
    }

private:
    void displayListInfo() {
        std::cout << "rn--modelList " << modellist.size() << "\n";
        std::cout << "modelCollecs " << model.size() << "\n";

        for (int i = 0; i < modellist.size(); i++) {
            std::cout << "modelList " << modellist[i] << "\n";
        }
    }

};

#pragma once
#include <iostream>
#include <cstdlib>
#include "utils.hpp"


namespace console {

    std::string inputs (std::string premise) {
        std::string value;
        std::cout << premise;
        std::getline(std::cin, value);
        return value;
    }

    long inputl (std::string premise) {
        return utils::stol(inputs(premise));
    }

    std::string spaces (const int& indent) {
        std::string sp;
        for (int i = 0; i < indent; ++i) {
            sp += " ";
        }
        return sp;
    }

    void clear() {
        #ifdef _WIN32
            std::system("cls");
        #else
            std::system ("clear");
        #endif
    }

}

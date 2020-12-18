#include <iostream>
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

}

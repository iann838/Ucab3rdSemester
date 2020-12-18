#include <iostream>
#include <string>
#include <chrono>
#include <cctype>
#include <algorithm>
#include "exceptions.hpp"


namespace utils {

    bool is_num(const std::string& s)
    {
        return !s.empty() && std::find_if(s.begin(), 
            s.end(), [](unsigned char c) { return !std::isdigit(c); }) == s.end();
    }

    long stol (std::string str) {
        if (is_num(str)) return std::stol(str);
        throw exceptions::ValueError();
    }

    std::chrono::_V2::steady_clock::time_point date_now() {
        return std::chrono::steady_clock::now();
    }

}

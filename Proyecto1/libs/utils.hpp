#pragma once
#include <iostream>
#include <sstream>
#include <string>
#include <cctype>
#include <vector>
#include <algorithm>
#include "exceptions.hpp"
#include "json.hpp"


using json = nlohmann::json;


namespace utils {

    bool is_num(const std::string& s)
    {
        return !s.empty() && std::find_if(s.begin(), 
            s.end(), [](unsigned char c) { return !std::isdigit(c); }) == s.end();
    }

    long stol (std::string str) {
        if (is_num(str)) return std::stol(str);
        throw exceptions::ValueError("El valor introducido no es un numero entero.");
    }

    std::vector<long> stovl(std::string str) {
        std::vector<long> vect;

        std::stringstream ss(str);

        for (int i; ss >> i;) {
            vect.push_back(i);    
            if (ss.peek() == ',')
                ss.ignore();
        }
        return vect;
    }

    double mean (std::vector<double> v) {
        double m = 0;
        long count = v.size();
        for (auto it: v) {
            m += it;
        }
        return m / count;
    }

    double mean (std::vector<json> v, const std::string& key) {
        double m = 0;
        long count = v.size();
        for (auto it: v) {
            m += it[key].get<double>();
        }
        return m / count;
    }

}

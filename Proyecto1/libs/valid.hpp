#include <iostream>
#include <vector>
#include <regex>
#include "../libs/exceptions.hpp"


namespace valid {

    void gt (long& value, long& compare) {
        if (value > compare) return;
        throw exceptions::ValidationError("El dato introducido no es mayor a lo pedido");
    }

    void gte (long& value, long& compare) {
        if (value >= compare) return;
        throw exceptions::ValidationError("El dato introducido no es mayor igual a lo pedido");
    }

    void lt (long& value, long& compare) {
        if (value < compare) return;
        throw exceptions::ValidationError("El dato introducido no es menor a lo pedido");
    }

    void lte (long& value, long& compare) {
        if (value <= compare) return;
        throw exceptions::ValidationError("El dato introducido no es menor igual a lo pedido");
    }

    void btw (long& value, long& lower, long& upper) {
        if (value > lower && value < upper) return;
        throw exceptions::ValidationError("El dato introducido no esta dentro del rango abierto");
    }

    void btwe (long& value, long& lower, long& upper) {
        if (value >= lower && value <= upper) return;
        throw exceptions::ValidationError("El dato introducido no esta dentro del rango cerrado");
    }

    void isin (std::string& value, std::vector<std::string>& v) {
        for (auto it = v.begin(); it != v.end(); ++it) {
            if (*it == value) return;
        }
        throw exceptions::ValidationError("El dato introducido no es una de las opciones");
    }

    void isin (long& value, std::vector<long>& v) {
        for (auto it = v.begin(); it != v.end(); ++it) {
            if (*it == value) return;
        }
        throw exceptions::ValidationError("El dato introducido no es una de las opciones");
    }

    void maxlen (std::string& value, long& len) {
        if (value.length() <= len) return; 
        throw exceptions::ValidationError("El dato introducido es muy largo");
    }

    void minlen (std::string& value, long& len) {
        if (value.length() >= len) return; 
        throw exceptions::ValidationError("El dato introducido es muy corto");
    }

    void email (std::string& value) {
        const std::regex pattern("(\\w+)(\\.|_)?(\\w*)@(\\w+)(\\.(\\w+))+"); 
        bool valid = std::regex_match(value, pattern);
        if (!valid) throw exceptions::ValidationError("El dato introducido no es un correo valido");
    }

}

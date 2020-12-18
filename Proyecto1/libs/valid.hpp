#include <iostream>
#include <vector>
#include <regex>
#include "../libs/exceptions.hpp"


namespace valid {

    void gt (const long& value, const long& compare) {
        if (value > compare) return;
        throw exceptions::ValidationError("El dato introducido no es mayor a lo pedido");
    }

    void gte (const long& value, const long& compare) {
        if (value >= compare) return;
        throw exceptions::ValidationError("El dato introducido no es mayor igual a lo pedido");
    }

    void lt (const long& value, const long& compare) {
        if (value < compare) return;
        throw exceptions::ValidationError("El dato introducido no es menor a lo pedido");
    }

    void lte (const long& value, const long& compare) {
        if (value <= compare) return;
        throw exceptions::ValidationError("El dato introducido no es menor igual a lo pedido");
    }

    void btw (const long& value, const long& lower, const long& upper) {
        if (value > lower && value < upper) return;
        throw exceptions::ValidationError("El dato introducido no esta dentro del rango abierto");
    }

    void btwe (const long& value, const long& lower, const long& upper) {
        if (value >= lower && value <= upper) return;
        throw exceptions::ValidationError("El dato introducido no esta dentro del rango cerrado");
    }

    void isin (const std::string& value, const std::vector<std::string>& v) {
        for (auto it = v.begin(); it != v.end(); ++it) {
            if (*it == value) return;
        }
        throw exceptions::ValidationError("El dato introducido no es una de las opciones");
    }

    void isin (const long& value, const std::vector<long>& v) {
        for (auto it = v.begin(); it != v.end(); ++it) {
            if (*it == value) return;
        }
        throw exceptions::ValidationError("El dato introducido no es una de las opciones");
    }

    void maxlen (const std::string& value, const long& len) {
        if (value.length() <= len) return; 
        throw exceptions::ValidationError("El dato introducido es muy largo");
    }

    void minlen (const std::string& value, const long& len) {
        if (value.length() >= len) return; 
        throw exceptions::ValidationError("El dato introducido es muy corto");
    }

    void email (const std::string& value) {
        const std::regex pattern("(\\w+)(\\.|_)?(\\w*)@(\\w+)(\\.(\\w+))+"); 
        bool valid = std::regex_match(value, pattern);
        if (!valid) throw exceptions::ValidationError("El dato introducido no es un correo valido");
    }

}

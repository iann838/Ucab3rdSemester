#include <iostream>
#include "../libs/exceptions.hpp"
#include "../libs/console.hpp"
#include "../libs/json.hpp"
#include "views.hpp"


using json = nlohmann::json;


namespace app {

    json session = "null"_json;

    void auth_loop () {
        std::cout << "      ---  Modulo 8  ---      " << std::endl;
        std::cout << "1. Iniciar sesion." << std::endl;
        std::cout << "0. Salir del programa." << std::endl;

        long option;
        try {
            option = console::inputl("\nIntroduzca una opcion por favor: ");
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
        }

        std::cout << std::endl;

        switch (option) {
            case 0:
                throw exceptions::SigExit();
            case 1:
                session = views::log_in();
                break;
            default:
                std::cout << "\n> La opcion introducida no es valida" << std::endl;
                break;
        }
    }

    void app_loop () {
        std::cout << "      ---  Modulo 8  ---      " << std::endl;
        std::cout << "1. Cerrar sesion." << std::endl;
        std::cout << "0. Salir del programa." << std::endl;

        long option;
        try {
            option = console::inputl("\nIntroduzca una opcion por favor: ");
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what() << std::endl;
        }

        switch (option) {
            case 0:
                throw exceptions::SigExit();
            case 1:
                std::cout << "> Session cerrada";
                session = "null"_json;
                break;
            default:
                std::cout << "\n> La opcion introducida no es valida" << std::endl;
                break;
        }
    }

    void loop () {
        try {
            while (true) {
                if (session.empty()) auth_loop();
                else app_loop();
                std::cout << std::endl;
            }
        } catch (exceptions::SigExit) {
            std::cout << "> Bye" << std::endl;
        }
    }

}

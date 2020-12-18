#include <iostream>
#include "../libs/exceptions.hpp"
#include "../libs/console.hpp"
#include "../libs/json.hpp"
#include "views.hpp"


using json = nlohmann::json;


namespace app {

    json session = "null"_json;

    void auth_loop () {
        std::cout << "1. Iniciar sesion." << std::endl;
        std::cout << "0. Salir del programa." << std::endl;

        long option;
        try {
            option = console::inputl("\nIntroduzca una opcion por favor: ");
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
            return;
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

    void teacher_loop () {
        std::cout << "1. Crear pregunta." << std::endl;
        std::cout << "2. Modificar pregunta." << std::endl;
        std::cout << "3. Eliminar pregunta." << std::endl;
        std::cout << "4. Crear examen." << std::endl;
        std::cout << "5. Modificar Examen." << std::endl;
        std::cout << "6. Eliminar Examen." << std::endl;
        std::cout << "7. Mostrar promedio de nota de todos los examenes." << std::endl;
        std::cout << "8. Mostrar lista de aprovados y no aprovados de un examen." << std::endl;
        std::cout << "9. Mostrar las notas obtenidas de un estudiante." << std::endl;
        std::cout << "10. Crear un nuevo usuario." << std::endl;
        std::cout << "11. Modificar perfil y clave." << std::endl;
        std::cout << "12. Cerrar sesion." << std::endl;
        std::cout << "0. Salir del programa." << std::endl;

        long option;
        try {
            option = console::inputl("\nIntroduzca una opcion por favor: ");
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
            return;
        }

        std::cout << std::endl;

        switch (option) {
            case 0:
                throw exceptions::SigExit();
            case 1:
                views::create_question();
                break;
            case 10:
                views::create_user();
                break;
            case 11:
                session = views::modify_profile(session["id"], session["group"]);
                break;
            case 12:
                std::cout << "> Session cerrada.";
                session = "null"_json;
                break;
            default:
                std::cout << "\n> La opcion introducida no es valida." << std::endl;
                break;
        }
    }

    void student_loop () {
        std::cout << "1. Realizar un examen." << std::endl;
        std::cout << "2. Ver nota de un examen." << std::endl;
        std::cout << "3. Ver todas las notas." << std::endl;
        std::cout << "4. Ver promedio de notas." << std::endl;
        std::cout << "5. Comparar respuestas de un examen." << std::endl;
        std::cout << "6. Modificar perfil y clave." << std::endl;
        std::cout << "7. Cerrar sesion." << std::endl;
        std::cout << "0. Salir del programa." << std::endl;

        long option;
        try {
            option = console::inputl("\nIntroduzca una opcion por favor: ");
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
            return;
        }

        std::cout << std::endl;

        switch (option) {
            case 0:
                throw exceptions::SigExit();
            case 6:
                session = views::modify_profile(session["id"], session["group"]);
                break;
            case 7:
                std::cout << "> Session cerrada.";
                session = "null"_json;
                break;
            default:
                std::cout << "\n> La opcion introducida no es valida." << std::endl;
                break;
        }
    }

    void loop () {
        try {
            while (true) {
                console::clear();
                std::cout << "    ---  Modulo 8 ";
                if (!session.empty()) {
                    std::cout << "(@" << session["id"].get<long>() << ")";
                }
                std::cout << " ---    " << std::endl;
                if (session.empty()) auth_loop();
                else if (session["group"] == "T") teacher_loop();
                else student_loop();
                std::cout << std::endl << std::endl << "Presione enter para continuar... ";
                std::cin.get();
            }
        } catch (exceptions::SigExit) {
            std::cout << "> Bye." << std::endl;
        }
    }

}

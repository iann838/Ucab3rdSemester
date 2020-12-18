#include "../libs/console.hpp"
#include "../libs/json.hpp"
#include "models.hpp"


using json = nlohmann::json;


namespace views {

    json log_in() {
        long id;
        try {
            id = console::inputl("Cedula identidad: ");
            std::string pw = console::inputs("Clave de ingreso: ");
            json session = users::authenticate(id, pw);
            if (session.empty()) {
                std::cout << "> Usuario no existe o clave invalida.";
            } else {
                std::cout << "> Session iniciada.";
            }
            return session;
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
            return "null"_json;
        }
    }

    json modify_profile (const long& id, const std::string& group) {
        json j = users::cin(id, group);
        users::put(id, j);
        std::cout << "> Perfil modificado.";
        return j;
    }

    long create_user () {
        json j = users::cin();
        users::create(j);
        std::cout << "> Usuario id: " << j["id"] << " creado.";
        return j["id"];
    }

    void create_question () {
        json j = questions::cin();
        long id = questions::create(j);
        std::cout << "> Pregunta id: " << id << " creado.";
    }

    

}

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
                std::cout << "> Usuario no existe o clave invalida";
            } else {
                std::cout << "> Session iniciada";
            }
            return session;
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what();
            return "null"_json;
        }
    }

}

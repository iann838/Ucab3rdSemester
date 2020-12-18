#include <vector>
#include "../libs/exceptions.hpp"
#include "../libs/constants.hpp"
#include "../libs/console.hpp"
#include "../libs/valid.hpp"
#include "../libs/json.hpp"
#include "../libs/db.hpp"

using json = nlohmann::json;


namespace users {

    /*
    "id": long
    "names": std::string
    "lastnames": std::string
    "group": std::char
    "email": std::string
    "password": std::string
    */

    std::vector<json> all () {
        return db::all("users");
    }

    std::vector<json> filter (const std::string& key, std::string& value) {
        return db::filter("users", key, value);
    }

    std::vector<json> filter (const std::string& key, long& value) {
        return db::filter("users", key, value);
    }

    json get (long& id) {
        return db::get("users", id);
    }

    long put (long& id, json& j) {
        j = clean(j);
        return db::put("users", id, j);
    }

    long create (json& j) {
        j = clean(j);
        return db::put("users", j["id"], j);
    }

    bool del (long& id) {
        return db::del("users", id);
    }

    json clean (json& j) {
        valid::lte(j["id"], 100000000);
        valid::minlen(j["names"], 3);
        valid::maxlen(j["names"], 60);
        valid::minlen(j["lastnames"], 3);
        valid::maxlen(j["lastnames"], 60);
        valid::email(j["email"]);
        valid::isin(j["group"], {"T", "S"});
        valid::minlen(j["password"], 8);
        valid::maxlen(j["password"], 30);

        return j;
    }

    json cin () {
        json j = "null"_json;
        
        while (!j) {
            try {
                j["id"] = console::inputl("Cedula identidad (sin letras): ");
                valid::lte(j["id"], 100000000);
                j["names"] = console::inputs("Nombres: ");
                valid::minlen(j["names"], 3);
                valid::maxlen(j["names"], 60);
                j["lastnames"] = console::inputs("Apellidos: ");
                valid::minlen(j["lastnames"], 3);
                valid::maxlen(j["lastnames"], 60);
                j["email"] = console::inputs("Correo electronico: ");
                valid::email(j["email"]);
                j["group"] = console::inputs("Tipo usuario (T: docente / S: estudiante): ");
                valid::isin(j["group"], {"T", "S"});
                j["password"] = console::inputs("Clave de usuario: ");
                valid::minlen(j["password"], 8);
                valid::maxlen(j["password"], 30);
            } catch (exceptions::ValueError& e) {
                std::cout << "> " << e.what() << std::endl;
                j = "null"_json;
            } catch (exceptions::ValidationError& e) {
                std::cout << "> " << e.what() << std::endl;
                j = "null"_json;
            }
        }

        return j;
    }

    
    json authenticate (long id, std::string pw) {
        json j = get(id);
        if (!j || j['password'] != pw) return "null"_json;
        return j;
    }

}

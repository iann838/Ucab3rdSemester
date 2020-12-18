#include <vector>
#include <string>
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

    const std::vector<std::string> group_choices = {"T", "S"};

    std::vector<json> all () {
        return db::all("users");
    }

    std::vector<json> filter (const std::string& key, const std::string& value) {
        return db::filter("users", key, value);
    }

    std::vector<json> filter (const std::string& key, const long& value) {
        return db::filter("users", key, value);
    }

    json get (const long& id) {
        return db::get("users", id);
    }

    json clean (const json& j) {
        valid::lte(j["id"], 100000000);
        valid::minlen(j["names"], 3);
        valid::maxlen(j["names"], 60);
        valid::minlen(j["lastnames"], 3);
        valid::maxlen(j["lastnames"], 60);
        valid::email(j["email"]);
        valid::isin(j["group"].get<std::string>(), group_choices);
        valid::minlen(j["password"], 8);
        valid::maxlen(j["password"], 30);

        return j;
    }

    long put (const long& id, json& j) {
        j = clean(j);
        return db::put("users", id, j);
    }

    long create (json& j) {
        j = clean(j);
        return db::put("users", j["id"], j);
    }

    bool del (const long& id) {
        return db::del("users", id);
    }

    json cin (const long& id = NULL_LONG, const std::string& group = "S") {
        json j = "null"_json;
        
        while (j.empty()) {
            try {
                if (id == NULL_LONG) 
                    j["id"] = console::inputl("Cedula identidad (sin letras): ");
                else
                    j["id"] = id;
                valid::lte(j["id"], 100000000);
                j["names"] = console::inputs("Nombres: ");
                valid::minlen(j["names"], 3);
                valid::maxlen(j["names"], 60);
                j["lastnames"] = console::inputs("Apellidos: ");
                valid::minlen(j["lastnames"], 3);
                valid::maxlen(j["lastnames"], 60);
                j["email"] = console::inputs("Correo electronico: ");
                valid::email(j["email"]);
                j["group"] = group;
                valid::isin(j["group"].get<std::string>(), group_choices);
                j["password"] = console::inputs("Clave de ingreso: ");
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

    
    json authenticate (const long& id, const std::string& pw) {
        json j;
        try {
            j = get(id);
        } catch (exceptions::DoesNotExist) {
            return "null"_json;
        }
        if (j["password"] != pw) return "null"_json;
        return j;
    }

}


namespace questions {

    /*
    "id": long
    "statement": std::string
    "type": std::string
    "answerAmount": long
    "answers": std::vector<std::string>
    "correctAnswers": std::vector<long>
    */

    const std::vector<std::string> type_choices = {"VF", "SM"};

    std::vector<json> all () {
        return db::all("questions");
    }

    std::vector<json> filter (const std::string& key, const std::string& value) {
        return db::filter("questions", key, value);
    }

    std::vector<json> filter (const std::string& key, const long& value) {
        return db::filter("questions", key, value);
    }

    json get (const long& id) {
        return db::get("questions", id);
    }

    json clean (const json& j) {
        valid::minlen(j["statement"], 10);
        valid::isin(j["type"], type_choices);
        valid::btwe(j["answerAmount"], 2, 90);
        valid::btwe(j["answers"].size(), 2, j["answerAmount"]);
        valid::btwe(j["correctAnswers"].size(), 1, j["answerAmount"]);
        for (auto it: j["correctAnswers"]) {
            valid::btwe(it, 1, j["answerAmount"]);
        }

        return j;
    }

    long put (const long& id, json& j) {
        j = clean(j);
        return db::put("questions", id, j);
    }

    long create (json& j) {
        j = clean(j);
        return db::put("questions", j);
    }

    bool del (const long& id) {
        return db::del("questions", id);
    }

    json cin () {
        json j = "null"_json;
        
        while (j.empty()) {
            try {
                j["statement"] = console::inputs("Enunciado: ");
                valid::minlen(j["statement"], 10);
                j["type"] = console::inputs("Tipo (VF / SM): ");
                valid::isin(j["type"], type_choices);
                if (j["type"] == "VF") {
                    j["answerAmount"] = 2;
                    j["answers"] = {"Verdadero", "Falso"};
                    j["correctAnswers"] = console::inputl("Respuesta correcta (1: V / 2: F): ");
                    valid::btwe(j["correctAnswers"], 1, 2);
                    j["correctAnswers"] = json::array({j["correctAnswers"]});
                } else {
                    j["answerAmount"] = console::inputl("Cantidad de respuestas (min. 2): ");
                    valid::btwe(j["answerAmount"], 2, 90);
                    j["answers"] = json::array();
                    for (int i = 1; i <= j["answerAmount"]; ++i) {
                        j["answers"].push_back(console::inputs("Respuesta " + std::to_string(i) + ": "));
                    }
                    j["correctAnswers"] = console::inputs("Respuesta correctas (numeros separados de comas sin espacios): ");
                    valid::vlong(j["correctAnswers"]);
                    j["correctAnswers"] = utils::stovl(j["correctAnswers"]);
                    valid::btwe(j["correctAnswers"].size(), 2, j["answerAmount"]);
                    for (auto it: j["correctAnswers"]) {
                        valid::btwe(it, 1, j["answerAmount"]);
                    }
                }
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

}


namespace quizzes {

    /*
    "id": long
    "title": std::string
    "questionAmount": long
    "questions": std::vector<long>
    "pointsPerQuestion": long
    */

    std::vector<json> all () {
        return db::all("quizzes");
    }

    std::vector<json> filter (const std::string& key, const std::string& value) {
        return db::filter("quizzes", key, value);
    }

    std::vector<json> filter (const std::string& key, const long& value) {
        return db::filter("quizzes", key, value);
    }

    json get (const long& id) {
        return db::get("quizzes", id);
    }

    json clean (const json& j) {
        valid::minlen(j["title"], 10);
        valid::btwe(j["questionAmount"], 1, 90);
        valid::btwe(j["questions"].size(), 1, j["questionAmount"]);
        for (auto it: j["question"]) {
            questions::get(it);
        }
        valid::eq(j["pointsPerQuestion"], 20.0 / j["questionAmount"].get<int>());
        return j;
    }

    long put (const long& id, json& j) {
        j = clean(j);
        return db::put("quizzes", id, j);
    }

    long create (json& j) {
        j = clean(j);
        return db::put("quizzes", j);
    }

    bool del (const long& id) {
        return db::del("quizzes", id);
    }

    json cin () {
        json j = "null"_json;
        
        while (j.empty()) {
            try {
                j["title"] = console::inputs("Enunciado: ");
                valid::minlen(j["title"], 10);
                j["questionAmount"] = console::inputl("Cantidad de preguntas (min. 1): ");
                valid::btwe(j["questionAmount"], 1, 90);
                j["questions"] = json::array();
                for (int i = 1; i <= j["questionAmount"]; ++i) {
                    long qid = console::inputl("Pregunta " + std::to_string(i) + " (ingrese id): ");
                    questions::get(qid);
                    j["questions"].push_back(qid);
                }
                j["pointsPerQuestion"] = 20.0 / j["questionAmount"].get<int>();
            } catch (exceptions::ValueError& e) {
                std::cout << "> " << e.what() << std::endl;
                j = "null"_json;
            } catch (exceptions::ValidationError& e) {
                std::cout << "> " << e.what() << std::endl;
                j = "null"_json;
            } catch (exceptions::DoesNotExist& e) {
                std::cout << "> " << e.what() << std::endl;
                j = "null"_json;
            }
        }

        return j;
    }

}


namespace records {

    /*
    "id": long
    "quizz": long
    "user": long
    "presentationDate": std::string
    "startTime": long
    "endTime": long
    "calification": long
    "duration": long
    "answers": std::vector<std::vector<long>>
    */

    std::vector<json> all () {
        return db::all("records");
    }

    std::vector<json> filter (const std::string& key, const std::string& value) {
        return db::filter("records", key, value);
    }

    std::vector<json> filter (const std::string& key, const long& value) {
        return db::filter("records", key, value);
    }

    json get (const long& id) {
        return db::get("records", id);
    }

    json clean (const json& j) {
        return j;
    }

    long put (const long& id, json& j) {
        j = clean(j);
        return db::put("records", id, j);
    }

    long create (json& j) {
        j = clean(j);
        return db::put("records", j);
    }

    bool del (const long& id) {
        return db::del("records", id);
    }

}

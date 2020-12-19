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

    json modify_user (const long& id, const std::string& group) {
        json j = users::cin(id, group);
        users::put(id, j);
        std::cout << "> Usuario id: " << j["id"] << " modificado.";
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

    void modify_question () {
        long id = console::inputl("Pregunta (ingrese id): ");
        std::vector<json> objs = quizzes::filter("questions", id);
        if (objs.size() > 0) {
            std::cout << "> Pregunta id: " << id << " ya ha sido registrada en unos de los examenes.";
            return;
        }
        json j = questions::cin();
        long id = questions::put(id, j);
        std::cout << "> Pregunta id: " << id << " modificado.";
    }

    void delete_question () {
        long id = console::inputl("Pregunta (ingrese id): ");
        std::vector<json> objs = quizzes::filter("questions", id);
        if (objs.size() > 0) {
            std::cout << "> Pregunta id: " << id << " ya ha sido registrada en unos de los examenes.";
            return;
        }
        try {
            questions::del(id);
        } catch (exceptions::DoesNotExist& e) {
            std::cout << "> " << e.what();
            return;
        }
        std::cout << "> Pregunta id: " << id << " eliminado.";
    }

    void create_quizz () {
        json j = quizzes::cin();
        long id = quizzes::create(j);
        std::cout << "> Examen id: " << id << " creado.";
    }

    void modify_quizz () {
        long id = console::inputl("Examen (ingrese id): ");
        std::vector<json> objs = records::filter("quizz", id);
        if (objs.size() > 0) {
            std::cout << "> Examen id: " << id << " ya ha sido respondido por unos de los estudiantes.";
            return;
        }
        json j = quizzes::cin();
        long id = quizzes::put(id, j);
        std::cout << "> Pregunta id: " << id << " modificado.";
    }

    void delete_quizz () {
        long id = console::inputl("Examen (ingrese id): ");
        std::vector<json> objs = records::filter("quizz", id);
        if (objs.size() > 0) {
            std::cout << "> Examen id: " << id << " ya ha sido respondido por unos de los estudiantes.";
            return;
        }
        try {
            quizzes::del(id);
        } catch (exceptions::DoesNotExist& e) {
            std::cout << "> " << e.what();
            return;
        }
        std::cout << "> Examen id: " << id << " eliminado.";
    }

}

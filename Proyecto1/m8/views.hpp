#include <iostream>
#include <algorithm>
#include <memory>
#include <string>
#include <vector>
#include "../libs/console.hpp"
#include "../libs/exceptions.hpp"
#include "../libs/datetime.hpp"
#include "../libs/utils.hpp"
#include "../libs/list.hpp"
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
                std::cout << "> Usuario no existe o clave invalida." << std::endl;
            } else {
                std::cout << "> Session iniciada." << std::endl;
            }
            return session;
        } catch (exceptions::ValueError& e) {
            std::cout << "> " << e.what() << std::endl;
            return "null"_json;
        }
    }

    json modify_user (const long& id, const std::string& group) {
        json j = users::cin(id, group);
        users::put(id, j);
        std::cout << "> Usuario id: " << j["id"] << " modificado." << std::endl;
        return j;
    }

    long create_user () {
        json j = users::cin();
        users::create(j);
        std::cout << "> Usuario id: " << j["id"] << " creado." << std::endl;
        return j["id"];
    }

    void create_question () {
        json j = questions::cin();
        long id = questions::create(j);
        std::cout << "> Pregunta id: " << id << " creado." << std::endl;
    }

    void modify_question () {
        long id = console::inputl("Pregunta (ingrese id): ");
        std::vector<json> objs = quizzes::filter("questions", id);
        if (objs.size() > 0) {
            std::cout << "> Pregunta id: " << id << " ya ha sido registrada en unos de los examenes." << std::endl;
            return;
        }
        json j = questions::cin();
        questions::put(id, j);
        std::cout << "> Pregunta id: " << id << " modificado." << std::endl;
    }

    void delete_question () {
        long id = console::inputl("Pregunta (ingrese id): ");
        std::vector<json> objs = quizzes::filter("questions", id);
        if (objs.size() > 0) {
            std::cout << "> Pregunta id: " << id << " ya ha sido registrada en unos de los examenes." << std::endl;
            return;
        }
        try {
            questions::del(id);
        } catch (exceptions::DoesNotExist& e) {
            std::cout << "> " << e.what() << std::endl;
            return;
        }
        std::cout << "> Pregunta id: " << id << " eliminado." << std::endl;
    }

    void create_quizz () {
        json j = quizzes::cin();
        long id = quizzes::create(j);
        std::cout << "> Examen id: " << id << " creado." << std::endl;
    }

    void modify_quizz () {
        long id = console::inputl("Examen (ingrese id): ");
        std::vector<json> objs = records::filter("quizz", id);
        if (objs.size() > 0) {
            std::cout << "> Examen id: " << id << " ya ha sido respondido por unos de los estudiantes." << std::endl;
            return;
        }
        json j = quizzes::cin();
        quizzes::put(id, j);
        std::cout << "> Pregunta id: " << id << " modificado." << std::endl;
    }

    void delete_quizz () {
        long id = console::inputl("Examen (ingrese id): ");
        std::vector<json> objs = records::filter("quizz", id);
        if (objs.size() > 0) {
            std::cout << "> Examen id: " << id << " ya ha sido respondido por unos de los estudiantes." << std::endl;
            return;
        }
        try {
            quizzes::del(id);
        } catch (exceptions::DoesNotExist& e) {
            std::cout << "> " << e.what() << std::endl;
            return;
        }
        std::cout << "> Examen id: " << id << " eliminado." << std::endl;
    }

    void do_quizz (json& user) {
        auto allq = quizzes::all();
        auto quizzes = std::make_unique<struct list::List>();
        quizzes->loadvj(allq);
        std::cout << "  id       titulo" << std::endl << std::endl;
        for (auto it = quizzes->first; it != NULL; it=it->next) {
            std::cout << "  " << it->dict["id"] << "   " << it->dict["title"].get<std::string>() << std::endl;
        }
        std::cout << std::endl;
        long id = console::inputl("Examen (ingrese id): ");
        std::cout << std::endl;
        
        auto quizz = quizzes::get(id);
        datetime::datetime start = datetime::now();
        auto questions = std::make_unique<struct list::List>();
        questions->loadvl(quizz["questions"]);
        quizzes::cout(quizz);
        
        std::vector<std::vector<long>> ans = {};
        double points = 0;
        int i = 0;
        for (auto lit = questions->first; lit != NULL; lit=lit->next) {
            std::cout << std::endl;
            std::cout << "(" << ++i << ")";
            auto question = questions::get(lit->value);
            questions::cout(question, 2);
            std::vector<long> answers = {};
            while (true) {
                try {
                    if (question["type"] == "SM") {
                        std::string vlstr = console::inputs(console::spaces(2) + "Respuestas correctas (separados de ','): ");
                        valid::vlong(vlstr);
                        std::vector<long> unsafeAnswers = utils::stovl(vlstr);
                        valid::btwe(unsafeAnswers.size(), 1, question["answerAmount"]);
                        for (auto it: unsafeAnswers) {
                            valid::btwe(it, 1, question["answerAmount"]);
                        }
                        answers = unsafeAnswers;
                        break;
                    } else {
                        long unsafeAnswer = console::inputl(console::spaces(2) + "Respuesta correcta (1: V / 2: F): ");
                        valid::btwe(unsafeAnswer, 1, 2);
                        answers = {unsafeAnswer};
                        break;
                    }
                } catch (exceptions::ValueError& e) {
                    std::cout << console::spaces(2) << "> " << e.what() << std::endl;
                } catch (exceptions::ValidationError& e) {
                    std::cout << console::spaces(2) << "> " << e.what() << std::endl;
                }
            }
            double ppa = quizz["pointsPerQuestion"].get<double>() / question["correctAnswers"].size();
            double qpoints = 0;
            for (auto it: answers) {
                try {
                    valid::isin(it, question["correctAnswers"]);
                    qpoints += ppa;
                } catch (exceptions::ValidationError) {
                    if (qpoints > 0) qpoints -= ppa;
                }
            }
            points += qpoints;
            ans.push_back(answers);
        }
        datetime::datetime end = datetime::now();
        std::cout << std::endl << "Nota obtenida: " << points << std::endl;
        json j;
        j["quizz"] = quizz["id"];
        j["user"] = user["id"];
        j["startTime"] = datetime::str(start);
        j["endTime"] = datetime::str(end);
        datetime::timedelta duration = end-start;
        j["duration"] = duration.count();
        j["calification"] = points;
        j["answers"] = ans;
        records::create(j);
    }

    void all_quizzes_average () {
        auto allq = quizzes::all();
        auto quizzes = std::make_unique<struct list::List>();
        quizzes->loadvj(allq);
        std::cout << "  id  nota(p)  duracion(p)       titulo" << std::endl << std::endl;
        for (auto it = quizzes->first; it != NULL; it=it->next) {
            long id = it->dict["id"];
            auto allr = records::filter("quizz", id);
            double meanp = utils::mean(allr, "calification");
            datetime::timedelta meand = datetime::timedelta(utils::mean(allr, "duration"));
            std::cout << "  " << id << "    " << std::to_string(meanp).substr(0, 5) << "    " << datetime::str(meand) << "     " <<
            it->dict["title"].get<std::string>() << std::endl;
        }
    }

    void list_approve_by_quizz () {
        long id = console::inputl("Examen (ingrese id): ");
        std::cout << std::endl;
        quizzes::get(id);
        
        auto allr = records::filter("quizz", id);
        if (allr.size() == 0) {
            std::cout << "> Aun nadie ha presentado este examen." << std::endl;
            return;
        }
        std::sort(allr.begin(), allr.end(), [&](json &j1, json &j2)-> bool { return j1["calification"] > j2["calification"]; });
        auto records = std::make_unique<struct list::List>();
        records->loadvj(allr);
        std::cout << "  apro.   nota     usuario" << std::endl << std::endl;
        for (auto it = records->first; it != NULL; it=it->next) {
            long uid = it->dict["user"];
            json user = users::get(uid);
            std::cout << "    ";
            if (it->dict["calification"] >= 9.5) std::cout << "O";
            else std::cout << "X";
            std::cout << "    "  << std::to_string(it->dict["calification"].get<double>()).substr(0, 5) <<
            "   " << user["names"].get<std::string>() << " " << user["lastnames"].get<std::string>() << std::endl;
        }
    }

    void calification_by_user () {
        long id = console::inputl("Usuario (ingrese ci): ");
        std::cout << std::endl;
        users::get(id);

        auto allr = records::filter("user", id);
        if (allr.size() == 0) {
            std::cout << "> Este usuario aun no ha presentado examenes." << std::endl;
            return;
        }
        auto records = std::make_unique<struct list::List>();
        records->loadvj(allr);
        std::cout << "   nota           examen" << std::endl << std::endl;
        for (auto it = records->first; it != NULL; it=it->next) {
            long qid = it->dict["quizz"];
            json quizz = quizzes::get(qid);
            std::cout << "  " << std::to_string(it->dict["calification"].get<double>()).substr(0, 5) <<
            "   (" << quizz["id"] << ")  " << quizz["title"].get<std::string>() << std::endl;
        }
    }

    void calification_by_quizz (json& user) {
        auto allr = records::filter("user", user["id"].get<long>());
        if (allr.size() == 0) {
            std::cout << "> Aun no has presentado ningun examen." << std::endl;
            return;
        }
        auto records = std::make_unique<struct list::List>();
        records->loadvj(allr);
        std::cout << "  id        examen" << std::endl << std::endl;
        for (auto it = records->first; it != NULL; it=it->next) {
            long qid = it->dict["quizz"];
            json quizz = quizzes::get(qid);
            std::cout << "  " << it->dict["id"] << "    " << quizz["title"].get<std::string>() << std::endl;
        }
        std::cout << std::endl;

        long id = console::inputl("Record (ingrese id): ");
        std::cout << std::endl;
        json rec = records::get(id);
        records::cout(rec);
    }

    void all_calification (json& user) {
        long id = user["id"];
        auto allr = records::filter("user", id);
        if (allr.size() == 0) {
            std::cout << "> Aun no has presentado ningun examen." << std::endl;
            return;
        }
        auto records = std::make_unique<struct list::List>();
        records->loadvj(allr);
        std::cout << "   nota           examen" << std::endl << std::endl;
        for (auto it = records->first; it != NULL; it=it->next) {
            long qid = it->dict["quizz"];
            json quizz = quizzes::get(qid);
            std::cout << "  " << std::to_string(it->dict["calification"].get<double>()).substr(0, 5) <<
            "   (" << quizz["id"] << ")  " << quizz["title"].get<std::string>() << std::endl;
        }
    }

    void average_calification (json& user) {
        long id = user["id"];
        auto allr = records::filter("user", id);
        if (allr.size() == 0) {
            std::cout << "> Aun no has presentado ningun examen." << std::endl;
            return;
        }
        double mean = utils::mean(allr, "calification");
        std::cout << "Promedio de nota: " << mean << std::endl;
    }

    void compare_answers_quizz (json& user) {
        auto allr = records::filter("user", user["id"].get<long>());
        if (allr.size() == 0) {
            std::cout << "> Aun no has presentado ningun examen." << std::endl;
            return;
        }
        auto records = std::make_unique<struct list::List>();
        records->loadvj(allr);
        std::cout << "  id        examen" << std::endl << std::endl;
        for (auto it = records->first; it != NULL; it=it->next) {
            long qid = it->dict["quizz"];
            json quizz = quizzes::get(qid);
            std::cout << "  " << it->dict["id"] << "    " << quizz["title"].get<std::string>() << std::endl;
        }
        std::cout << std::endl;

        long id = console::inputl("Record (ingrese id): ");
        std::cout << std::endl;
        json rec = records::get(id);

        json quizz = quizzes::get(rec["quizz"].get<long>());
        auto questions = std::make_unique<struct list::List>();
        questions->loadvl(quizz["questions"]);
        quizzes::cout(quizz);
        int ind = 0;
        for (auto it = questions->first; it != NULL; it = it->next) {
            std::cout << std::endl;
            std::cout << "(" << ++ind << ")";
            auto question = questions::get(it->value);
            questions::cout(question, 2, true);
            std::cout << console::spaces(2) << "Tus respuestas: ";
            double ppa = quizz["pointsPerQuestion"].get<double>() / question["correctAnswers"].size();
            double qpoints = 0;
            std::vector<long> urans = rec["answers"][ind-1];
            for (auto it2: urans) {
                std::cout << "(" << it2 << ") ";
                try {
                    valid::isin(it2, question["correctAnswers"]);
                    qpoints += ppa;
                } catch (exceptions::ValidationError) {
                    if (qpoints > 0) qpoints -= ppa;
                }
            }
            std::cout << std::endl;
            std::cout << console::spaces(2) << "Puntos: " << qpoints << " / " << quizz["pointsPerQuestion"] << std::endl;
            std::cout << std::endl;
        }
        records::cout(rec);
    }

}

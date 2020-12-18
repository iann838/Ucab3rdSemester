#include <iostream>
#include "libs/json.hpp"
#include "libs/utils.hpp"
#include "libs/db.hpp"

using json = nlohmann::json;


long createQuestion() {
    std::string aux;

    std::string enunciado;
    std::cout << "Introduzca el enunciado: ";
    std::getline(std::cin, enunciado);

    int answerAmount;
    std::cout << "Cantidad de respuestas: ";
    std::getline(std::cin, aux);
    if (utils::is_num(aux)) answerAmount = std::stoi(aux);
    else return -1;

    std::vector<std::string> answers = {};
    for (int i = 0; i < answerAmount; ++i) {
        std::string respuesta;
        std::cout << "Introduzca la respuesta " << i+1 << ": ";
        std::getline(std::cin, respuesta);
        answers.push_back(respuesta);
    }

    int correctAnswer;
    std::cout << "Respuesta correcta (numero): ";
    std::getline(std::cin, aux);
    if (utils::is_num(aux)) correctAnswer = std::stoi(aux);
    else return -1;

    json j;
    j["enunciado"] = enunciado;
    j["answerAmount"] = answerAmount;
    j["answers"] = answers;
    j["correctAnswer"] = correctAnswer;
    return db::put("questions", j);
}


int main() {
    createQuestion();
    // json j;
    // j["hello"] = "world";

    // std::cout << j["hello"];

    // Para poner un json (id automatico): nombre de coleccion, objeto json
    // El id se genera automatico, mejor para documentos nuevos
    // db::put("questions", j);

    // para poner un json: nombre de coleccion, id del documento, objeto json
    // Mejor para modificar o actualizar un documento existente
    // db::put("questions", 0, j);

    // para leer un json: nombre de coleccion, id del documento
    // Devuelve un objeto json
    // json j2 = db::get("questions", 0);

    // para deletear un json: nombre de coleccion, id del documento
    // Devuelve true si fue satisfactorio, false si no pudo encontrar el json a deletear
    // db::del("questions", 0);

    return 0;
}

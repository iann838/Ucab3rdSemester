#include <iostream>
#include "libs/json.hpp"
#include "libs/utils.hpp"
#include "libs/db.hpp"

using json = nlohmann::json;


int main() {
    json j;
    j["hello"] = "world";

    // Para poner un json (id automatico): nombre de coleccion, objeto json
    // El id se genera automatico, mejor para documentos nuevos
    db::put("questions", j);

    // para poner un json: nombre de coleccion, id del documento, objeto json
    // Mejor para modificar o actualizar un documento existente
    db::put("questions", 0, j);

    // para leer un json: nombre de coleccion, id del documento
    // Devuelve un objeto json
    json j2 = db::get("questions", 0);

    // para deletear un json: nombre de coleccion, id del documento
    // Devuelve true si fue satisfactorio, false si no pudo encontrar el json a deletear
    db::del("questions", 0);

    return 0;
}

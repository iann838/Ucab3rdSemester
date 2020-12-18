#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <iomanip>
#include <algorithm>
#include "json.hpp"
#include "filesys.hpp"
#include "exceptions.hpp"


using json = nlohmann::json;


namespace db {

    std::string db_path = "db";

    std::string path (const std::string& collection, long id) {
        return db_path + "/" + collection + "/" + std::to_string(id) + ".json";
    }

    bool ls (const std::string& collection, json& j) {
        std::sort(j.begin(), j.end());

        std::string colpath = db_path + "/" + collection;
        if (!filesys::dir_exist(colpath)) {
            filesys::mkdir(colpath);
        }

        std::ofstream o(colpath + "/index.json");
        o << std::setw(4) << j << std::endl;
        return true;
    }

    json ls (const std::string& collection) {
        std::ifstream inf(db_path + "/" + collection + "/index.json");
        json index;

        std::stringstream indexfile;
        indexfile << inf.rdbuf();

        if (indexfile.str().length() > 0) {
            index = json::parse(indexfile);
        } else {
            index = "[]"_json;
            ls(collection, index);
        }
        return index;
    }

    json get (const std::string& collection, long id) {
        json index = ls(collection);
        json j;

        if (std::binary_search(index.begin(), index.end(), id)) {
            std::ifstream i(path(collection, id));
            i >> j;
        } else {
            throw exceptions::DoesNotExist();
        }
        return j; 
    }

    long put (const std::string& collection, long id, json& j) {
        json index = ls(collection);
        j["id"] = id;

        if (!std::binary_search(index.begin(), index.end(), id)) {
            index.push_back(id);
            ls(collection, index);
        }

        std::ofstream o(path(collection, id));
        o << std::setw(4) << j << std::endl;
        return id;
    }

    long put (const std::string& collection, json& j) {
        json index = ls(collection);
        long id;

        if (index.size() == 0) {
            id = 0;
        } else {
            id = index.back();
            ++id;
        }
        j["id"] = id;
        index.push_back(id);
        ls(collection, index);

        std::ofstream o(path(collection, id));
        o << std::setw(4) << j << std::endl;
        return id;
    }

    bool del (const std::string& collection, long id) {
        json index = ls(collection);

        if (std::binary_search(index.begin(), index.end(), id)) {
            std::remove(path(collection, id).c_str());
            index.erase(std::remove(index.begin(), index.end(), id), index.end());
            ls(collection, index);
            return true;
        } else {
            return false;
        }
    }

    std::vector<json> all (const std::string& collection) {
        json index = ls(collection);
        std::vector<json> objects = {};

        for (auto it = index.begin(); it != index.end(); ++it) {
            json j;
            std::ifstream i(path(collection, *it));
            i >> j;
            objects.push_back(j);
        }

        return objects;
    }

    std::vector<json> filter (const std::string& collection, const std::string& key, std::string& value) {
        auto all(collection);
        std::vector<json> objects = {};

        for (auto it: objects) {
            if (it[key] == value) objects.push_back(it);
        }

        return objects;
    }

    std::vector<json> filter (const std::string& collection, const std::string& key, long& value) {
        auto all(collection);
        std::vector<json> objects = {};

        for (auto it: objects) {
            if (it[key] == value) objects.push_back(it);
        }

        return objects;
    }

}

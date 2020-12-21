#pragma once
#include <iostream>
#include <iomanip>
#include <string>
#include <chrono>


namespace datetime {

    typedef std::chrono::system_clock::time_point datetime;
    typedef std::chrono::duration<double> timedelta;

    typedef std::chrono::duration<int, std::ratio<86400>> days;
    typedef std::chrono::duration<int, std::ratio<3600>> hours;
    typedef std::chrono::duration<int, std::ratio<60>> minutes;
    typedef std::chrono::duration<int, std::ratio<1>> seconds;

    datetime now() {
        return std::chrono::system_clock::now();
    }

    std::string str(datetime& dt) {
        std::time_t t = std::chrono::system_clock::to_time_t(dt);
        std::string dstr = std::ctime(&t);
        return dstr.substr(0, dstr.length() - 2);
    }

    std::string str(timedelta& td) {
        std::string dstr = "";
        auto h = std::chrono::duration_cast<hours>(td);
        td -= h;
        auto m = std::chrono::duration_cast<minutes>(td);
        td -= m;
        auto s = std::chrono::duration_cast<seconds>(td);
        dstr += std::string(2 - std::to_string(h.count()).length(), '0') + std::to_string(h.count()) + ":";
        dstr += std::string(2 - std::to_string(m.count()).length(), '0') + std::to_string(m.count()) + ":";
        dstr += std::string(2 - std::to_string(s.count()).length(), '0') + std::to_string(s.count());
        return dstr;
    }

}

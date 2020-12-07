#include <iostream>
#include <string>
#include <sys/stat.h> // stat
#include <errno.h>    // errno, ENOENT, EEXIST
#if defined(_WIN32)
#include <direct.h>   // _mkdir
#endif

namespace filesys {

    bool dir_exist(const std::string& path)
    {
        #if defined(_WIN32)
            struct _stat info;
            if (_stat(path.c_str(), &info) != 0)
            {
                return false;
            }
            return (info.st_mode & _S_IFDIR) != 0;
        #else 
            struct stat info;
            if (stat(path.c_str(), &info) != 0)
            {
                return false;
            }
            return (info.st_mode & S_IFDIR) != 0;
        #endif
    }

    bool mkdir(const std::string& path)
    {
        #if defined(_WIN32)
            int ret = _mkdir(path.c_str());
        #else
            mode_t mode = 0755;
            int ret = mkdir(path.c_str(), mode);
        #endif
        if (ret == 0)
            return true;

        switch (errno) {
        case ENOENT:
        {
            int pos = path.find_last_of('/');
            if (pos == std::string::npos)
            #if defined(_WIN32)
                pos = path.find_last_of('\\');
            if (pos == std::string::npos)
            #endif
                return false;
            if (!mkdir( path.substr(0, pos) ))
                return false;
            #if defined(_WIN32)
                return 0 == _mkdir(path.c_str());
            #else 
                return 0 == mkdir(path.c_str(), mode);
            #endif
        }
        case EEXIST:
            return dir_exist(path);
        default:
            return false;
        }
    }

}

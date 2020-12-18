#include <exception>


namespace exceptions {

    class ValidationError : public std::exception {
        public:
            ValidationError(std::string msg = "Uno o mas datos invalidos") : detail(msg) { }
            const char * what () const throw ()
            {
                return detail.c_str();
            }
        private:
            std::string detail;
    };
    
    class ValueError : public std::exception {
        public:
            ValueError(std::string msg = "Dato ingresado no es del tipo pedido") : detail(msg) { }
            const char * what () const throw ()
            {
                return detail.c_str();
            }
        private:
            std::string detail;
    };

    class DoesNotExist : public std::exception {
        public:
            DoesNotExist(std::string msg = "El objeto pedido no existe") : detail(msg) { }
            const char * what () const throw ()
            {
                return detail.c_str();
            }
        private:
            std::string detail;
    };
}

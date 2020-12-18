
/* THIS IS HARDLY DEPRECATED BETTER APPROACH EXISTS*/

#include <iostream>
#include <vector>
#include "json.hpp"


using json = nlohmann::json;


namespace list {

    struct Node {
        json dict;
        struct Node *next;
    };

    typedef struct Node *NodePtr;

    struct List {
        struct Node *first; 
        struct Node *last;

        void loadv(std::vector<json> vj) {
            int i = 0;
            NodePtr lastIt = NULL;
            for (auto it: vj) {
                NodePtr q = new (struct Node);
                q->dict = it;
                q->next = NULL;
                if (lastIt != NULL) {
                    lastIt->next = q;
                }
                if (i == 0) {
                    first = q;
                }
                if (i == vj.size()) {
                    last = q;
                }
            }
        }

        void append(json& j) {
            NodePtr q = new (struct Node);

            q->dict = j;
            q->next = NULL;

            if (first == NULL) {
                first = q;
                last = q;
            } else {
                last->next = q;
                last = q;
            }
        }
    }

}

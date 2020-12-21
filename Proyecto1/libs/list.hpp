
/* THIS IS HARDLY DEPRECATED BETTER APPROACH EXISTS*/

#pragma once
#include <iostream>
#include <vector>
#include <memory>
#include "json.hpp"


using json = nlohmann::json;


namespace list {

    struct Node {
        long value;
        json dict;
        std::shared_ptr<list::Node> next;
    };

    typedef std::shared_ptr<list::Node> NodePtr;

    struct List {
        NodePtr first; 
        NodePtr last;

        void loadvl(std::vector<long> vj) {
            int i = 0;
            NodePtr lastIt = NULL;
            for (auto it: vj) {
                auto q = std::make_shared<struct Node>();
                q->value = it;
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
                ++i;
                lastIt = q;
            }
        }

        void loadvj(std::vector<json> vj) {
            int i = 0;
            NodePtr lastIt = NULL;
            for (auto it: vj) {
                auto q = std::make_shared<struct Node>();
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
                ++i;
                lastIt = q;
            }
        }

        std::vector<long> dumpvl() {
            std::vector<long> v = {};
            if (first == NULL) return v;
            for (auto it = first; it != NULL; it=it->next) {
                v.push_back(it->value);
            }
            return v;
        }

        void append(long j) {
            auto q = std::make_shared<struct Node>();

            q->value = j;
            q->next = NULL;

            if (first == NULL) {
                first = q;
                last = q;
            } else {
                last->next = q;
                last = q;
            }
        }

        void append(json j) {
            auto q = std::make_shared<struct Node>();

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
    };

}

//*************************************************//
//
// Michal Kot-Kawula
// Seneca email: mkot-kawula@myseneca.ca
// Seneca Student ID: 128945193
// Date: 12/4/2021
//
// I have done all the coding by myself and only copied the code that my professor provided to complete my workshops and assignments.
//
//*************************************************//
#ifndef SDDS_WORKSTATION_H
#define SDDS_WORKSTATION_H

#include <iostream>
#include <deque>
#include <string>
#include "Station.h"
#include "CustomerOrder.h"

namespace sdds {

    extern std::deque<CustomerOrder> pending;
    extern std::deque<CustomerOrder> completed;
    extern std::deque<CustomerOrder> incomplete;

    class Workstation : public Station {
        std::deque<CustomerOrder> m_orders{};
        Workstation* m_pNextStation{ nullptr };

    public:


        //Receives a reference to an unmodifiable reference to std::string and passes it to the Station base class.
        Workstation(const std::string& name);

        //this modifier fills the order at the front of the queue, if there are CustomerOrders in the queue; otherwise, does nothing.
        void fill(std::ostream& os);

        //attempts to move the order order at the front of the queue to the next station in the assembly line
        bool attemptToMoveOrder();

        //this modifier stores the address of the referenced Workstation object in the pointer to the m_pNextStation. Parameter defaults to nullptr.
        void setNextStation(Workstation* station = nullptr) ;

        //this query returns the address of next Workstation
        Workstation* getNextStation() const;

        //this query inserts the name of the Item for which the current object is responsible into stream os
        void display(std::ostream& os) const;

        //moves the CustomerOrder referenced in parameter newOrder to the back of the queue.
        Workstation& operator +=(CustomerOrder&& newOrder);

    };
}
#endif 
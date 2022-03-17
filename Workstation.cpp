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
#include <iostream>
#include "Workstation.h"

using namespace std;
namespace sdds {

    // holds the orders to be placed onto the assembly line at the first station.
    std::deque<CustomerOrder> pending{};

    //holds the orders that have been removed from the last station and have been completely filled.
    std::deque<CustomerOrder> completed{};
    
    //holds the orders that have been removed from the last station and could not be filled completely.
    std::deque<CustomerOrder> incomplete{};

    //Receives a reference to an unmodifiable reference to std::string and passes it to the Station base class.
    Workstation::Workstation(const std::string& name) :Station(name) {};

    //this modifier fills the order at the front of the queue, if there are CustomerOrders in the queue; otherwise, does nothing.
    void Workstation::fill(std::ostream& os){
        if (!m_orders.empty()) {
            m_orders.begin()->fillItem(*this, os);
        }
    }


    //attempts to move the order order at the front of the queue to the next station in the assembly line
    bool Workstation::attemptToMoveOrder(){
      
        if (!m_orders.empty()) {
            //if the order requires no more service at this station, move it to the next station
            //if the order cannot be filled (not enough inventory), move the order to the next station.
            if (m_orders.front().isItemFilled(getItemName()) || getQuantity() <= 0) {
                if (m_pNextStation != nullptr) {
                    *m_pNextStation += std::move(m_orders.front());
                    m_orders.erase(m_orders.begin());
                    return true;
                }
                else {
                    //if there is no next station in the assembly line, then the order is moved into completed queue
                    if (m_orders.front().isFilled()) {
                        completed.push_back(std::move(m_orders.front()));
                    }
                    //if there is no next station in the assembly line, then the order is moved into incomplete queue.
                    else {
                        incomplete.push_back(std::move(m_orders.front()));
                    }
                    //if an order has been moved, return true; 
                    m_orders.erase(m_orders.begin());
                    return true;
                }
            }
        }
        //false otherwise
        return false;
    }

    //this modifier stores the address of the referenced Workstation object in the pointer to the m_pNextStation. Parameter defaults to nullptr.
    void Workstation::setNextStation(Workstation* station){m_pNextStation = station;}

    //this query returns the address of next Workstation
    Workstation* Workstation::getNextStation() const{return m_pNextStation;}

    //this query inserts the name of the Item for which the current object is responsible into stream os
    void Workstation::display(std::ostream& os) const{
        os << getItemName() << " --> ";
        os << (m_pNextStation != nullptr ? m_pNextStation->getItemName() : "End of Line");
        os << endl;
    }


    //moves the CustomerOrder referenced in parameter newOrder to the back of the queue.
    Workstation& Workstation::operator+=(CustomerOrder&& newOrder){
        m_orders.push_back(std::move(newOrder));
        return *this;
    }
}
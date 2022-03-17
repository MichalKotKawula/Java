//*************************************************//
//
// Michal Kot-Kawula
// Seneca email: mkot-kawula@myseneca.ca
// Seneca Student ID: 128945193
// Date: 11/27/2021
//
// I have done all the coding by myself and only copied the code that my professor provided to complete my workshops and assignments.
//
//*************************************************//
#ifndef SDDS_CUSTORMERORDER_H
#define SDDS_CUSTORMERORDER_H

#include <iostream>
#include <string>
#include <algorithm>
#include <iostream>
#include <iomanip>
#include "Station.h"
#include "Utilities.h"

namespace sdds {

class CustomerOrder {


    struct Item
    {
        std::string m_itemName;
        unsigned int m_serialNumber = 0;
        bool m_isFilled = false;

        Item(const std::string& src) : m_itemName(src) {};
    };

    std::string m_name;
    std::string m_product;
    unsigned int m_cntItem;
    Item** m_lstItem;
    static size_t m_widthField;

public:
    //default constructor
    CustomerOrder();
    //A custom 1 - arg constructor that takes a reference to an unmodifiable string.
    //This constructor uses a local Utilities object to extract the tokens from the string and populate the current instance.
    CustomerOrder(const std::string&);

    //a CustomerOrder object should not allow copy operations.
    CustomerOrder(const CustomerOrder&);

    //copy operator= should be deleted
    CustomerOrder& operator=(const CustomerOrder& cpy) = delete;

    //A move constructor. This constructor should "promise" that it doesn't throw exceptions. 
    CustomerOrder(CustomerOrder&& mov) noexcept;

    //A move assignment operator. This operator should "promise" that it doesn't throw exceptions. 
    CustomerOrder& operator=(CustomerOrder&& mov) noexcept;

    //destructor
    ~CustomerOrder();

    // returns true if all the items in the order have been filled; false otherwise
    bool isFilled() const;

    //returns true if all items specified by itemName have been filled. If the item doesn't exist in the order, this query returns true.
    bool isItemFilled(const std::string& itemName) const;

    //this modifier fills all items in the current order that the Station specified in the first parameter handles.
    void fillItem(Station& station, std::ostream& os);

    //this query displays the state of the current object in specified format 
    void display(std::ostream& os) const;

};

}
#endif

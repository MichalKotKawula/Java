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

#include "CustomerOrder.h"

using namespace std;
namespace sdds {

    //setting field width
   size_t CustomerOrder::m_widthField = 0;

    //default constructor
    CustomerOrder::CustomerOrder() {m_name = "";    m_product = "";    m_cntItem = 0;  m_lstItem = nullptr;}

    //A custom 1 - arg constructor that takes a reference to an unmodifiable string.
   //This constructor uses a local Utilities object to extract the tokens from the string and populate the current instance.
    CustomerOrder::CustomerOrder(const std::string& my_str) {
        Utilities util;
        bool even = true;
        size_t foundPos = 0;

        m_name = util.extractToken(my_str, foundPos, even);
        m_product = util.extractToken(my_str, foundPos, even);       
        m_cntItem = std::count(my_str.begin(), my_str.end(), util.getDelimiter()) - 1;

        m_lstItem = new Item * [m_cntItem];

        for (auto i = 0u; i < m_cntItem; i++) {
            m_lstItem[i] = new Item(util.extractToken(my_str, foundPos, even));
        }

        if (m_widthField < util.getFieldWidth()) { m_widthField = util.getFieldWidth(); }
         
    }

    //a CustomerOrder object should not allow copy operations.
    CustomerOrder::CustomerOrder(const CustomerOrder& cpy) {
        //The copy constructor should throw an exception if called and the copy operator=
        throw "Exception! No Copying!";
    }

    //A move constructor. This constructor should "promise" that it doesn't throw exceptions. 
    CustomerOrder::CustomerOrder(CustomerOrder&& mov) noexcept {
        m_lstItem = nullptr;
        *this = std::move(mov);
    }

    //A move assignment operator. This operator should "promise" that it doesn't throw exceptions. 
    CustomerOrder& CustomerOrder::operator=(CustomerOrder&& mov) noexcept {
        if (this != &mov) {

            if (m_lstItem != nullptr) {
                for (size_t i = 0; i < m_cntItem; ++i) {
                    delete m_lstItem[i];
                }
                delete[] m_lstItem;
            }

            this->m_name = mov.m_name;
            mov.m_name.clear();

            this->m_product = mov.m_product;
            mov.m_product.clear();

            this->m_cntItem = mov.m_cntItem;
            mov.m_cntItem = 0;

            this->m_lstItem = mov.m_lstItem;
            mov.m_lstItem = nullptr;
        }
        return *this;
    }

 
    // returns true if all the items in the order have been filled, false otherwise.
    bool CustomerOrder::isFilled() const {
        bool myCond = true;
        for (auto i = 0u; i < m_cntItem; ++i) {
            if (!m_lstItem[i]->m_isFilled) {
                myCond = false;
            }
        }
        return myCond;
    }

   //returns true if all items specified by itemName have been filled. If the item doesn't exist in the order, this query returns true.
    bool CustomerOrder::isItemFilled(const std::string& itemName) const {
        bool filled = true;

        for (size_t i = 0; i < m_cntItem; i++) {
            if (m_lstItem[i]->m_itemName == itemName) {
                filled = m_lstItem[i]->m_isFilled;
            }
        }
        return filled;
    }

   // //this modifier fills all items in the current order that the Station specified in the first parameter handles.
    void CustomerOrder::fillItem(Station& station, std::ostream& os) {

        for (size_t i = 0; i < m_cntItem; i++) {
            if (m_lstItem[i]->m_itemName == station.getItemName()) {
                if (station.getQuantity() > 0) {
                    station.updateQuantity();
                    m_lstItem[i]->m_serialNumber = station.getNextSerialNumber();
                    m_lstItem[i]->m_isFilled = true;

                    os << std::right << "    Filled ";
                    os << m_name << ", " << m_product << " [" << m_lstItem[i]->m_itemName << "]" << std::endl;
                }
                else {
                    os << std::right << "    Unable to fill ";
                    os << m_name << ", " << m_product << " [" << m_lstItem[i]->m_itemName << "]" << std::endl;
                }
            }
        }
    }


     //this query displays the state of the current object in specified format 
    void CustomerOrder::display(std::ostream& os) const {
        const char zeroFill = '0';	const int localInt6 = 6;	const char emptyFill = ' ';
   
        os << m_name << " - " << m_product << std::endl;

        for (size_t i = 0; i < m_cntItem; ++i) {
            os << "[" << std::setw(localInt6) << std::right << std::setfill(zeroFill) << m_lstItem[i]->m_serialNumber << "] ";
            os << std::setfill(emptyFill) << std::left << std::setw(CustomerOrder::m_widthField);
            os << m_lstItem[i]->m_itemName << " - ";
            os << ((m_lstItem[i]->m_isFilled) ? "FILLED" : "TO BE FILLED") << std::endl;
        }
    }

    //destructor
    CustomerOrder::~CustomerOrder() {

        for (auto i = 0u; i < m_cntItem; ++i) {
            delete m_lstItem[i];
        }
        delete[] m_lstItem;
        m_lstItem = nullptr;
    }
    
}



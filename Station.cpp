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
#include "Station.h"
#include "Utilities.h"
#include <iostream>
#include <iomanip>

namespace sdds {

	// the maximum number of characters required to print to the screen the item name, serial numberand quantity for any object of type Station.Initial value is 0.
	size_t Station::m_widthField = 0;
	//a variable used to generate IDs for new instances of type Station. Every time a new instance is created, the current value of the id_generator is stored in that instance, and id_generator is incremented. Initial value is 0.
	size_t Station::id_generator = 0;

	//custom 1 argument constructor
	//upon instantiation, an Station object receives a reference to an unmodifiable std::string. This string contains a single record (one line) that has been retrieved from the input file specified by the user.
	Station::Station(const std::string& record) {


		//this constructor uses a Utilities object (defined locally) to extract each token from the record and populates the Station object accordingly
		Utilities util{};

		size_t next_pos = 0;
		bool more = false;

		//increment ID
		++Station::id_generator;
		s_stationID = Station::id_generator;

		//this constructor assumes that the string contains 4 fields separated by the delimiter
		this->s_itmName = util.extractToken(record, next_pos, more);
		this->s_serNum = std::stoi(util.extractToken(record, next_pos, more));
		this->s_qtyStock = std::stoi(util.extractToken(record, next_pos, more));

		//before extracting description, it updates Station::m_widthField to the maximum value of Station::m_widthField and Utilities::m_widthField.
		size_t length = util.getFieldWidth();
		if (Station::m_widthField < length) {
			Station::m_widthField = length;
		}
		
		s_stationDesc = util.extractToken(record, next_pos, more);
		
	}

	//returns the name of the current Station object
	const std::string& Station::getItemName() const { return s_itmName; }

	//returns the next serial number to be used on the assembly line and increments serialNumber
	size_t Station::getNextSerialNumber() { return s_serNum++; }

	//returns the remaining quantity of items in the Station object
	size_t Station::getQuantity() const { return s_qtyStock; }

	//subtracts 1 from the available quantity; should not drop below 0.
	void Station::updateQuantity() {
		--s_qtyStock;
		if (s_qtyStock < 0) {
			s_qtyStock = 0;
		}
	}

	// inserts information about the current object into stream os.
	void Station:: display(std::ostream& os, bool full) const {

		const char zeroFill = '0';	const int localInt3 = 3;	const int localInt6 = 6;	const char emptyFill = ' ';

		//if the second parameter is false, this function inserts only the ID, name, and serial number in the format: [ID] Item: NAME [SERIAL]
		os << "[" << std::setw(localInt3) << std::setfill(zeroFill) << std::right << s_stationID << "]" << std::setfill(emptyFill);
		os << " Item: " << std::setw(m_widthField) << std::left << s_itmName << std::setfill(emptyFill);
		os << " [" << std::setw(localInt6) << std::setfill(zeroFill) << std::right << s_serNum << "]" << std::setfill(emptyFill);

		
		if (full == false) {
			
			os	<< std::endl;
		}
		//if the second parameter if true, this function inserts the information in the following format: [ID] Item NAME [SERIAL] Quantity: QTY Description: DESCRIPTION
		else {
				os << " Quantity: " << std::setw(m_widthField) << std::left << s_qtyStock << std::setfill(' ');
				os << " Description: " << s_stationDesc;
				os<< std::endl;
		}
	}




}
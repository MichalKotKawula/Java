
#ifndef SDDS_STATION_H
#define SDDS_STATION_H
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
#include <string>


namespace sdds {
	class Station {


		int s_stationID;
		std::string s_itmName;
		std::string s_stationDesc;
		size_t s_serNum;
		size_t s_qtyStock;
		static size_t m_widthField;
		static size_t id_generator;


	public:
		//custom 1 argument constructor
		Station(const std::string& record);

		//returns the name of the current Station object
		const std::string& getItemName() const;

		//returns the next serial number to be used on the assembly line and increments serialNumber
		size_t getNextSerialNumber();

		//returns the remaining quantity of items in the Station object
		size_t getQuantity() const;

		//subtracts 1 from the available quantity; should not drop below 0.
		void updateQuantity();

		// inserts information about the current object into stream os.
		void display(std::ostream& os, bool full) const;

	};


}


#endif
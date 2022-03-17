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
#ifndef SDDS_UTILITIES_H
#define SDDS_UTILITIES_H

#include <string>

namespace sdds {
	class Utilities {

		size_t m_widthField = 1;
		static char m_delimeter;

	public:
		//sets the field width of the current object to the value of parameter newWidth
		void setFieldWidth(size_t newWidth);

		//returns the field width of the current object
		size_t getFieldWidth() const;

		// extracts a token from string str referred to by the first parameter
		std::string extractToken(const std::string& str, size_t& next_pos, bool& more);

		//sets the delimiter for this class to the character received
		static void setDelimiter(char newDelimiter);

		//returns the delimiter for this class.
		static char getDelimiter();

	};

}
#endif

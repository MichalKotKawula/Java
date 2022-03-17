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
#include "Utilities.h"


namespace sdds {

	char Utilities::m_delimeter = '\0';


	// extracts a token from string str referred to by the first parameter
	std::string Utilities::extractToken(const std::string& str, size_t& next_pos, bool& more) {

		size_t foundPos = 0;
		std::string saveCpy;

		more = false;

		if (next_pos < str.length()){


			// uses the delimiter to extract the next token from str starting at position next_pos
			foundPos = str.find(m_delimeter, next_pos);


			//If successful, this function returns a copy of the extracted token found, updates next_pos with the position of the next token, and sets more to true
			if (foundPos != std::string::npos){
			
				saveCpy = str.substr(next_pos, foundPos - next_pos);
				more = true;
			}
			//felse return false
			else{
				more = false;
				saveCpy = str.substr(next_pos);
			}
			//This function reports an exception if a delimiter is found at next_pos.
			if (str.at(next_pos) == this->m_delimeter){
				more = false;
				throw "Nothing Found";
			}
			//This function updates the current object's m_widthField data member if its current value is less than the size of the token extracted.
			if (m_widthField < saveCpy.length()){
				m_widthField = saveCpy.length();
			}
		}

		//increment next position
		next_pos = foundPos + 1;
		return saveCpy;
	}


	//sets the delimiter for this class to the character received
	void Utilities::setDelimiter(char newDelimiter) {Utilities::m_delimeter = newDelimiter;}
	
	//returns the delimiter for this class.
	char Utilities::getDelimiter() {return Utilities::m_delimeter;}

	//sets the field width of the current object to the value of parameter newWidth
	void Utilities::setFieldWidth(size_t newWidth) { this->m_widthField = newWidth; }

	//returns the field width of the current object
	size_t Utilities::getFieldWidth() const { return m_widthField; }


}
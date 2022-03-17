
#include <fstream>
#include <algorithm>
#include "LineManager.h"

using namespace std;

namespace sdds {
  
    LineManager::LineManager(const std::string& file, const std::vector<Workstation*>& stations){
      
        try {
            std::fstream fin(file);

            if (!fin)
                throw "Could not open file";

            std::string collection{};  std::string currentWorkStation{}, nextWorkStation{}; size_t foundPos = 0;
            bool moreComing = true;
            Utilities util{};
            Utilities::setDelimiter('|');

            m_cntCustomerOrder = pending.size();

     
            while (std::getline(fin, collection)) {

                if (fin) {

    
                    currentWorkStation = util.extractToken(collection, foundPos, moreComing);
                    auto toBeSend = std::find_if(stations.cbegin(), stations.cend(), [currentWorkStation](const Workstation* work) { return work->getItemName() == currentWorkStation; });
                    activeLine.push_back(*toBeSend);

                    if (moreComing) {
                        nextWorkStation = util.extractToken(collection, foundPos, moreComing);
                        toBeSend = std::find_if(stations.cbegin(), stations.cend(), [nextWorkStation](const Workstation* work) { return work->getItemName() == nextWorkStation; });
                        activeLine.back()->setNextStation(*toBeSend);
                    }

                }
            }

            std::for_each(activeLine.cbegin(), activeLine.cend(), [this](Workstation* work) {
                const std::string& stationN = work->getItemName();
                auto stationSearch = [stationN](const Workstation* otherWs) {
                    if (otherWs->getNextStation()) {
                        return stationN == otherWs->getNextStation()->getItemName();
                    }
                    return false;
                };

                bool found = std::any_of(activeLine.cbegin(), activeLine.cend(), stationSearch);
                if (!found) { m_firstStation = work; }
                });


            fin.close();
        }catch (...) {
            throw "Issue with Line Manager";
        }
        
    }

    void LineManager::linkStations(){
        vector<Workstation*> workstationVec;
        Workstation* thisWS = m_firstStation;

        while (thisWS != nullptr) {
            workstationVec.push_back(thisWS);
            thisWS = thisWS->getNextStation();
        }
        activeLine = workstationVec;
    }

    bool LineManager::run(std::ostream& os){

        static size_t CurrentNum = 0;
        ++CurrentNum;

        os << "Line Manager Iteration: " << CurrentNum << std::endl;

      
        if (!pending.empty()){
            *m_firstStation += std::move(pending.front());
            pending.pop_front();
        }
       
        for (const auto& w_station : activeLine){
            w_station->fill(os);
        }
      
        for (const auto& w_station : activeLine){
            w_station->attemptToMoveOrder();
        }

     
        if ((incomplete.size() + completed.size()) == m_cntCustomerOrder) {return true;}
        return false;
     
    }

   
    void LineManager::display(std::ostream& os) const{
        for (size_t i = 0; i < activeLine.size(); i++) {
            activeLine.at(i)->display(os);
        }
        //std::for_each(activeLine.begin(), activeLine.end(), [&os](const Workstation* ws) { ws->display(os); });
    }

}

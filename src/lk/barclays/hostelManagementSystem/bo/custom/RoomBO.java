package lk.barclays.hostelManagementSystem.bo.custom;

import javafx.collections.ObservableList;
import lk.barclays.hostelManagementSystem.bo.SuperBO;
import lk.barclays.hostelManagementSystem.dto.RoomDTO;

public interface RoomBO extends SuperBO {
    boolean saveRoom(RoomDTO roomDTO);

    boolean deleteRoom(String s);

    String generateNextRoomId();

    ObservableList<RoomDTO> getAllRooms();

    boolean updateRoom(RoomDTO roomDTO);

    RoomDTO getSpecificRoom(String newValue);

    int getAvailableRoomsCount();
}

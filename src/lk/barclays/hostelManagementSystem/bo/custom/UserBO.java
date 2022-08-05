package lk.barclays.hostelManagementSystem.bo.custom;

import lk.barclays.hostelManagementSystem.bo.SuperBO;
import lk.barclays.hostelManagementSystem.dto.UserDTO;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO userDTO);

    UserDTO checkUserExists(String id);

    String generateNextUserId();
}

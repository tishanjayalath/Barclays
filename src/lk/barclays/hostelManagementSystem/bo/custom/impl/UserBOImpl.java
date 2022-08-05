package lk.barclays.hostelManagementSystem.bo.custom.impl;

import lk.barclays.hostelManagementSystem.bo.custom.UserBO;
import lk.barclays.hostelManagementSystem.dao.DAOFactory;
import lk.barclays.hostelManagementSystem.dao.custom.UserDAO;
import lk.barclays.hostelManagementSystem.dto.UserDTO;
import lk.barclays.hostelManagementSystem.entity.User;

import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.save(new User(userDTO.getUserid(),userDTO.getUserName(), userDTO.getPassword(), userDTO.getDateCreated()));
    }

    @Override
    public UserDTO checkUserExists(String name) {
        ArrayList<User> all = userDAO.getAll();
        for (User user : all) {
            if (user.getUserName().equals(name))
                return new UserDTO(user.getUserid(), user.getUserName(), user.getPassword(), user.getDateCreated());
        }
        return null;
    }

    @Override
    public String generateNextUserId() {
        ArrayList<User> all = userDAO.getAll();
        if (all.size() > 0) {
            int newId = Integer.parseInt(all.get(all.size()-1).getUserid().replace("U","")) +1;
            return String.format("U%03d",newId);
        }else
            return "U001";
    }
}

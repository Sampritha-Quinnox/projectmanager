package quinnox.sam.projectmanager.service;

import quinnox.sam.projectmanager.entity.User;

public interface UserServices {

	void save(User user);

	User getUserByUsername(String username);
	public User get(Long id);

}

package com.axelor.auth.db.repo;

import com.axelor.auth.db.User;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class UserRepository extends JpaRepository<User> {

	public UserRepository() {
		super(User.class);
	}

	public User findByName(String name) {
		return Query.of(User.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public User findByGroup(Long groupId) {
		return Query.of(User.class)
				.filter("self.group[].id = :groupId")
				.bind("groupId", groupId)
				.cacheable()
				.fetchOne();
	}

	public User findByGroup(String groupCode) {
		return Query.of(User.class)
				.filter("self.group[].code = :groupCode")
				.bind("groupCode", groupCode)
				.cacheable()
				.fetchOne();
	}

	public User findByEmail(String email) {
		return Query.of(User.class)
				.filter("self.email = :email")
				.bind("email", email)
				.cacheable()
				.fetchOne();
	}

	public User findByCodeOrEmail(String codeOrEmail) {
		return Query.of(User.class)
				.filter("self.code = :codeOrEmail OR self.email = :codeOrEmail")
				.bind("codeOrEmail", codeOrEmail)
				.cacheable()
				.fetchOne();
	}

	public User findByCode(String code) {
		return Query.of(User.class)
				.filter("LOWER(self.code) = LOWER(:code)")
				.bind("code", code)
				.fetchOne();
	}

}


package controller;

import domain.Role;

public enum Action {
	plserror(null),
	home(null),
	login(null),
	logout(null),
	register(null),
	switchcolour(null),

	products(Role.CUSTOMER),

	overview(Role.ADMINISTRATOR),
	addproduct(Role.ADMINISTRATOR),
	updateperson(Role.ADMINISTRATOR),
	deleteperson(Role.ADMINISTRATOR),
	deleteproduct(Role.ADMINISTRATOR);

	public final Role role;

	Action(Role role) {
		this.role = role;
	}

	public boolean roleCanDo(Role role) {
		if (this.role == role) return true;
		if (role == Role.ADMINISTRATOR) return true;
		return this.role == null;
	}

}

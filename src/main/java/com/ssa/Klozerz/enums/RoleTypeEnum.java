package com.ssa.Klozerz.enums;

/**
 * 
 * @author Zubair
 *
 */
public enum RoleTypeEnum {
	CEO("CEO"),MANAGER("MANAGER"),DIRECTOR("DIRECTOR"),Administrator("Administrator");
	/**
	*
	*/
	private String value;

	RoleTypeEnum(final String value) {
		this.value = value;
	}

	/**
	 *
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 *
	 * @param value
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}

package com.terminus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity Class representing PhoneNumbers table
 */
@Entity
@Table(name = "phone_number")
public class PhoneNumberEntity {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int phoneId;
	private int customerID;
	private int phoneNumber;
	private String status;

	public int getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PhoneNumberEntity [phoneId=" + phoneId + ", customerID=" + customerID + ", phoneNumber=" + phoneNumber
				+ ", status=" + status + "]";
	}

	public PhoneNumberEntity(int phoneId, int customerID, int phoneNumber, String status) {
		super();
		this.phoneId = phoneId;
		this.customerID = customerID;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public PhoneNumberEntity() {
		super();
	}

}

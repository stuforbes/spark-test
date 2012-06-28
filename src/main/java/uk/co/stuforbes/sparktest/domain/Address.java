package uk.co.stuforbes.sparktest.domain;

public class Address {

	public enum AddressType {
		WORK, HOME
	};

	private final int addressId;
	private final String address1;
	private final String address2;
	private final String city;
	private final String county;
	private final String postcode;
	private final AddressType type;

	public Address(final int addressId, final String address1,
			final String address2, final String city, final String county,
			final String postcode, final AddressType type) {
		this.addressId = addressId;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.county = county;
		this.postcode = postcode;
		this.type = type;
	}

	public int getAddressId() {
		return addressId;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCity() {
		return city;
	}

	public String getCounty() {
		return county;
	}

	public String getPostcode() {
		return postcode;
	}

	public AddressType getType() {
		return type;
	}

}

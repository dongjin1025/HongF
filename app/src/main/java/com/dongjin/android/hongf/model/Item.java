package com.dongjin.android.hongf.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable
{
	private String id;

	private String category;

	private String distance;

	private String title;

	private String phone;

	private String newAddress;

	private String address;

	private String imageUrl;

	private String direction;

	private String zipcode;

	private String placeUrl;

	private String longitude;

	private String latitude;

	private String addressBCode;

	public Item(Parcel in) {
		readFromParcel(in);
	}

	public String getId ()
	{
		return id;
	}

	public void setId (String id)
	{
		this.id = id;
	}

	public String getCategory ()
	{
		return category;
	}

	public void setCategory (String category)
	{
		this.category = category;
	}

	public String getDistance ()
	{
		return distance;
	}

	public void setDistance (String distance)
	{
		this.distance = distance;
	}

	public String getTitle ()
	{
		return title;
	}

	public void setTitle (String title)
	{
		this.title = title;
	}

	public String getPhone ()
	{
		return phone;
	}

	public void setPhone (String phone)
	{
		this.phone = phone;
	}

	public String getNewAddress ()
	{
		return newAddress;
	}

	public void setNewAddress (String newAddress)
	{
		this.newAddress = newAddress;
	}

	public String getAddress ()
	{
		return address;
	}

	public void setAddress (String address)
	{
		this.address = address;
	}

	public String getImageUrl ()
	{
		return imageUrl;
	}

	public void setImageUrl (String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getDirection ()
	{
		return direction;
	}

	public void setDirection (String direction)
	{
		this.direction = direction;
	}

	public String getZipcode ()
	{
		return zipcode;
	}

	public void setZipcode (String zipcode)
	{
		this.zipcode = zipcode;
	}

	public String getPlaceUrl ()
	{
		return placeUrl;
	}

	public void setPlaceUrl (String placeUrl)
	{
		this.placeUrl = placeUrl;
	}

	public String getLongitude ()
	{
		return longitude;
	}

	public void setLongitude (String longitude)
	{
		this.longitude = longitude;
	}

	public String getLatitude ()
	{
		return latitude;
	}

	public void setLatitude (String latitude)
	{
		this.latitude = latitude;
	}

	public String getAddressBCode ()
	{
		return addressBCode;
	}

	public void setAddressBCode (String addressBCode)
	{
		this.addressBCode = addressBCode;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [id = "+id+", category = "+category+", distance = "+distance+", title = "+title+", phone = "+phone+", newAddress = "+newAddress+", address = "+address+", imageUrl = "+imageUrl+", direction = "+direction+", zipcode = "+zipcode+", placeUrl = "+placeUrl+", longitude = "+longitude+", latitude = "+latitude+", addressBCode = "+addressBCode+"]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(category);
		dest.writeString(distance);
		dest.writeString(title);
		dest.writeString(phone);
		dest.writeString(newAddress);
		dest.writeString(address);
		dest.writeString(imageUrl);
		dest.writeString(direction);
		dest.writeString(zipcode);
		dest.writeString(placeUrl);
		dest.writeString(longitude);
		dest.writeString(latitude);
		dest.writeString(addressBCode);

	}
	private void readFromParcel(Parcel in){

		id=in.readString();
		category=in.readString();
		distance=in.readString();
		title=in.readString();
		phone=in.readString();
		newAddress=in.readString();
		address=in.readString();
		imageUrl=in.readString();
		direction=in.readString();
		zipcode=in.readString();
		placeUrl=in.readString();
		longitude=in.readString();
		latitude=in.readString();
		addressBCode=in.readString();

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

}
package vukan.com.apursp.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Objects;

public class Message implements Comparable, Parcelable {
    private String content;
    private Timestamp dateTime;
    private String productID;
    private String senderID;
    private String receiverID;
    private long dateTimelong;

    public Message() {
    }

    public Message(String content, Timestamp dateTime, String productID, String senderID, String receiverID) {
        this.content = content;
        this.dateTime = dateTime;
        this.productID = productID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.dateTimelong = new Date().getTime();

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateTimelong() {
        return dateTimelong;
    }

    public void setDateTimelong(long dateTime) {
        this.dateTimelong = dateTime;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    @NonNull
    @Override
    public String toString() {
        return senderID + content;
    }


    @Override
    public int compareTo(Object o) {
        Timestamp compareDate = ((Message) o).getDateTime();
        /* For Ascending order*/

        return this.dateTime.compareTo(compareDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return dateTimelong == message.dateTimelong &&
                content.equals(message.content) &&
                dateTime.equals(message.dateTime) &&
                productID.equals(message.productID) &&
                senderID.equals(message.senderID) &&
                receiverID.equals(message.receiverID);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(content, dateTime, productID, senderID, receiverID, dateTimelong);
    }

    protected Message(Parcel in) {
        content = in.readString();
        dateTime = (Timestamp) in.readValue(Timestamp.class.getClassLoader());
        productID = in.readString();
        senderID = in.readString();
        receiverID = in.readString();
        dateTimelong = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeValue(dateTime);
        dest.writeString(productID);
        dest.writeString(senderID);
        dest.writeString(receiverID);
        dest.writeLong(dateTimelong);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
package com.developersanjeev.uhbvn;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Keep
public class FormResponse implements Serializable {
    private String studentName;
    private String studentEmailID;
    private String circle;
    private String subdivision;

    private double latitude;
    private double longitude;

    private String signUrl;

    private String name;
    private String meterNumber;
    private String mobileNumber;
    private String dateTimeStamp;


    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String q8;
    private String q9;
    private String q10;
    private String q11;
    private String q12;
    private String q13;
    private String q14;
    private String q15;
    private String q16;
    private String q17;
    private String q18;
    private String q19;
    private String q20;
    private String q21;
    private String q22;
    private String q23;
    private String q24;
    private String q25;
    private String q26;
    private String q27;
    private String q28;
    private String q29;
    private String q30;

    // for firebase
    public FormResponse() {

    }

    public FormResponse(String studentName, String studentEmailID, String circle, String subdivision, double latitude, double longitude, String name, String meterNumber, String mobileNumber, String dateTimeStamp, String q1, String q2, String q3, String q4, String q5, String q6, String q7, String q8, String q9, String q10, String q11, String q12, String q13, String q14, String q15, String q16, String q17, String q18, String q19, String q20, String q21, String q22, String q23, String q24, String q25, String q26, String q27, String q28, String q29, String q30) {
        this.studentName = studentName;
        this.studentEmailID = studentEmailID;
        this.circle = circle;
        this.subdivision = subdivision;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.meterNumber = meterNumber;
        this.mobileNumber = mobileNumber;
        this.dateTimeStamp = dateTimeStamp;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.q10 = q10;
        this.q11 = q11;
        this.q12 = q12;
        this.q13 = q13;
        this.q14 = q14;
        this.q15 = q15;
        this.q16 = q16;
        this.q17 = q17;
        this.q18 = q18;
        this.q19 = q19;
        this.q20 = q20;
        this.q21 = q21;
        this.q22 = q22;
        this.q23 = q23;
        this.q24 = q24;
        this.q25 = q25;
        this.q26 = q26;
        this.q27 = q27;
        this.q28 = q28;
        this.q29 = q29;
        this.q30 = q30;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public String getStudentEmailID() {
        return studentEmailID;
    }

    public void setStudentEmailID(String studentEmailID) {
        this.studentEmailID = studentEmailID;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    public String getQ6() {
        return q6;
    }

    public void setQ6(String q6) {
        this.q6 = q6;
    }

    public String getQ7() {
        return q7;
    }

    public void setQ7(String q7) {
        this.q7 = q7;
    }

    public String getQ8() {
        return q8;
    }

    public void setQ8(String q8) {
        this.q8 = q8;
    }

    public String getQ9() {
        return q9;
    }

    public void setQ9(String q9) {
        this.q9 = q9;
    }

    public String getQ10() {
        return q10;
    }

    public void setQ10(String q10) {
        this.q10 = q10;
    }

    public String getQ11() {
        return q11;
    }

    public void setQ11(String q11) {
        this.q11 = q11;
    }

    public String getQ12() {
        return q12;
    }

    public void setQ12(String q12) {
        this.q12 = q12;
    }

    public String getQ13() {
        return q13;
    }

    public void setQ13(String q13) {
        this.q13 = q13;
    }

    public String getQ14() {
        return q14;
    }

    public void setQ14(String q14) {
        this.q14 = q14;
    }

    public String getQ15() {
        return q15;
    }

    public void setQ15(String q15) {
        this.q15 = q15;
    }

    public String getQ16() {
        return q16;
    }

    public void setQ16(String q16) {
        this.q16 = q16;
    }

    public String getQ17() {
        return q17;
    }

    public void setQ17(String q17) {
        this.q17 = q17;
    }

    public String getQ18() {
        return q18;
    }

    public void setQ18(String q18) {
        this.q18 = q18;
    }

    public String getQ19() {
        return q19;
    }

    public void setQ19(String q19) {
        this.q19 = q19;
    }

    public String getQ20() {
        return q20;
    }

    public void setQ20(String q20) {
        this.q20 = q20;
    }

    public String getQ21() {
        return q21;
    }

    public void setQ21(String q21) {
        this.q21 = q21;
    }

    public String getQ22() {
        return q22;
    }

    public void setQ22(String q22) {
        this.q22 = q22;
    }

    public String getQ23() {
        return q23;
    }

    public void setQ23(String q23) {
        this.q23 = q23;
    }

    public String getQ24() {
        return q24;
    }

    public void setQ24(String q24) {
        this.q24 = q24;
    }

    public String getQ25() {
        return q25;
    }

    public void setQ25(String q25) {
        this.q25 = q25;
    }

    public String getQ26() {
        return q26;
    }

    public void setQ26(String q26) {
        this.q26 = q26;
    }

    public String getQ27() {
        return q27;
    }

    public void setQ27(String q27) {
        this.q27 = q27;
    }

    public String getQ28() {
        return q28;
    }

    public void setQ28(String q28) {
        this.q28 = q28;
    }

    public String getQ29() {
        return q29;
    }

    public void setQ29(String q29) {
        this.q29 = q29;
    }

    public String getQ30() {
        return q30;
    }

    public void setQ30(String q30) {
        this.q30 = q30;
    }

    @NonNull
    @Override
    public String toString() {
        return "Student Name : " + this.getStudentName() + "\n" +
                "Student Email ID : " + this.getStudentEmailID() + "\n" +
                "Name : " + this.getName() + "\n" +
                "Circle : " + this.getCircle() + "\n" +
                "Time Stamp : " + this.getDateTimeStamp();
    }
}

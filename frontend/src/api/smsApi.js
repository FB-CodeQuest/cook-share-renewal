import axiosInstance from "@/lib/axiosInstance.js";

export const smsSend = async (phoneNumber) => {
    return axiosInstance.post("/api/sms/phone",null,{
        params:{ phoneNumber }
    });
}

export const smsVerify = async (phoneNumber,authCode) => {
    return axiosInstance.post("/api/sms/verify",{
        phoneNumber,
        authCode
    });
};
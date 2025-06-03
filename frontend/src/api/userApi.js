import axiosInstance from "@/lib/axiosInstance.js";

export const checkNickname = async (nickname) => {
    return axiosInstance.get("/api/users/check-nickname",{
        params: { nickname },
    });
};
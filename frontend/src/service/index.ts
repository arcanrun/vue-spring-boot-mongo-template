import {axiosAuto, axiosManual} from "@/service/config.ts";
import type {ILoginRequestDto, IRegisterRequestDto} from "@/service/dto/auth.ts";
import type {IUserDto} from "@/service/dto/user.ts";

export const API = {
  authController: {
    PATH: "/auth",
    MANUAL_MODE: {
      login: (loginDto: ILoginRequestDto) => axiosManual.post(`${API.authController.PATH}/login`, loginDto),
      signup: (signupDto: any) => axiosManual.post(`${API.authController.PATH}/signup`, signupDto),
      refreshToken: () =>
        axiosManual.put(`${API.authController.PATH}/refresh`, null, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
          }
        }),
    },

    login: async (loginDto: ILoginRequestDto) =>
      (await axiosAuto.post(`${API.authController.PATH}/login`, loginDto)).data,
    signup: async (signupDto: IRegisterRequestDto) =>
      (await axiosAuto.post(`${API.authController.PATH}/signup`, signupDto)).data,
    refreshToken: async () =>
      (await axiosAuto.put(`${API.authController.PATH}/refresh`)).data,
  },

  userController: {
    PATH: "/users",
    fetchCurrentUser: async () =>
      (await axiosAuto.get<IUserDto>(`${API.userController.PATH}/me`)).data,
  }
}

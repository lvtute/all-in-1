import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "/auth/";

const register = (signUpRequestObject) => {
  return http.post(API_URL + "sign-up", signUpRequestObject);
};

const login = (username, password) => {
  return http
    .post(API_URL + "sign-in", {
      username,
      password,
    })
    .then((response) => {
      console.log(response.data);
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const changePassword = (data) => {
  return http.put(`${API_URL}change-password`, data, {
    headers: authHeader(),
  });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const authenService = {
  register,
  login,
  logout,
  getCurrentUser,
  changePassword,
};

export default authenService;

import http from "./http-common";

const API_URL = "/auth/";

const register = (signUpRequestObject) => {
  return http.post(API_URL + "sign-up", signUpRequestObject);
};

const createFaculty = (createFacultyRequestObject) => {
  return http.post(API_URL + "insert", createFacultyRequestObject);
};

const login = (username, password) => {
  return http
    .post(API_URL + "sign-in", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const authenService = {
  register,
  createFaculty,
  login,
  logout,
  getCurrentUser
};

export default authenService;

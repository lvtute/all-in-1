import http from "./http-common";


const register = (signUpRequestObject) => {
  return http.post("/auth/sign-up", signUpRequestObject);
};

// const login = (username, password) => {
//   return http
//     .post(API_URL + "signin", {
//       username,
//       password,
//     })
//     .then((response) => {
//       if (response.data.accessToken) {
//         localStorage.setItem("user", JSON.stringify(response.data));
//       }

//       return response.data;
//     });
// };

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const authenService = {
  register,
  // login,
  logout,
  getCurrentUser,
}

export default authenService;
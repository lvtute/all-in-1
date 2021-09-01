// import authHeader from "./auth-header";
import http from "./http-common";

// const getAdminBoard = () => {
//   return axios.get(API_URL + "admin", { headers: authHeader() });
// };

const getAllWithPaging = (pageNum = 0, pageSize = 5) => {
  if (pageNum > 0) pageNum--;
  return http.get("/user?size=" + pageSize + "&page=" + pageNum);
};

const deleteUserById = (id = 0) => {
  return http.delete("/user/" + id);
};

const userService = {
  getAllWithPaging,
  deleteUserById,
};

export default userService;

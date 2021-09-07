// import authHeader from "./auth-header";
import http from "./http-common";

// const getAdminBoard = () => {
//   return axios.get(API_URL + "admin", { headers: authHeader() });
// };

const getAll = (pageNum = 0, pageSize = 5) => {
  if (pageNum > 0) pageNum--;
  return http.get("/user?size=" + pageSize + "&page=" + pageNum);
};

const deleteById = (id = 0) => {
  return http.delete("/user/" + id);
};

const userService = {
  getAll,
  deleteById,
};

export default userService;

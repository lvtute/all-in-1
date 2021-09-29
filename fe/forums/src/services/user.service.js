// import authHeader from "./auth-header";
import http from "./http-common";

// const getAdminBoard = () => {
//   return axios.get(API_URL + "admin", { headers: authHeader() });
// };

const getAll = (pageNum = 1, pageSize = 5) => {
  pageNum--;
  return http.get("/user?size=" + pageSize + "&page=" + pageNum);
};

const getById = (id = 0) => {
  return http.get(`/user/${id}`);
};

const deleteById = (id = 0) => {
  return http.delete(`/user/${id}`);
};

const update = (updateRequestObject) => {
  return http.put("/user", updateRequestObject);
};

const userService = {
  getAll,
  getById,
  deleteById,
  update,
};

export default userService;

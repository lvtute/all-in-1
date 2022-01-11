import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "/user";

const getAll = (pageNum = 1, pageSize = 5) => {
  pageNum--;
  return http.get("/user?size=" + pageSize + "&page=" + pageNum, {
    headers: authHeader(),
  });
};

const getById = (id = 0) => {
  return http.get(`/user/${id}`,{
    headers: authHeader(),
  });
};

const deleteById = (id = 0) => {
  return http.delete(`/user/${id}`, { headers: authHeader() });
};

const update = (updateRequestObject) => {
  return http.put("/user", updateRequestObject, {
    headers: authHeader(),
  });
};

const getByDean = (params) => {
  let config = {
    headers: authHeader(),
    params,
  };
  return http.get(`${API_URL}/find-by-dean`, config);
};

const updateByDean = (requestBody) => {
  return http.put(`${API_URL}/update-by-dean`, requestBody, {
    headers: authHeader(),
  });
};

const createByDean = (requestBody) => {
  return http.post(`${API_URL}/create-by-dean`, requestBody, {
    headers: authHeader(),
  });
};

const userService = {
  getAll,
  getById,
  deleteById,
  update,
  getByDean,
  updateByDean,
  createByDean,
};

export default userService;

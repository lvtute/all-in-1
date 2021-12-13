import authHeader from "./auth-header";
import http from "./http-common";

const getAll = () => {
  return http.get("/faculty");
};

const getById = (id = 0) => {
  return http.get(`/faculty/${id}`);
};

const deleteById = (id = 0) => {
  return http.delete(`/faculty/${id}`, { headers: authHeader() });
};

const update = (updateRequestObject) => {
  return http.put("/faculty", updateRequestObject, { headers: authHeader() });
};
const create = (requestBody) => {
  return http.post("/faculty", requestBody, { headers: authHeader() });
};

const facultyService = {
  getAll,
  getById,
  deleteById,
  update,
  create,
};

export default facultyService;

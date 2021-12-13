import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "/topic";

const getByFacultyId = (id) => {
  return http.get(`${API_URL}/find-by-faculty-id/${id}`);
};

const deleteById = (id) => {
  return http.delete(`${API_URL}/${id}`, { headers: authHeader() });
};

const update = (requestBody) => {
  return http.put(`${API_URL}`, requestBody, {
    headers: authHeader(),
  });
};

const create = (requestBody) => {
  return http.post(`${API_URL}`, requestBody, {
    headers: authHeader(),
  });
};

const topicService = {
  getByFacultyId,
  deleteById,
  update,
  create,
};

export default topicService;

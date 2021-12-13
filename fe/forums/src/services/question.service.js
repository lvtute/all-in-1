import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "/question";

const getAll = (params) => {
  return http.get(API_URL, { params });
};

const getById = (questionId) => {
  return http.get(`${API_URL}/${questionId}`);
};

const createQuestion = (requestBody) => {
  return http.post(API_URL, requestBody);
};

const getByAdviserId = (params) => {
  let config = {
    headers: authHeader(),
    params,
  };
  return http.get(`${API_URL}/find-by-adviser-id`, config);
};

const saveAnswer = (requestData) => {
  return http.put(`${API_URL}/save-answer`, requestData, {
    headers: authHeader(),
  });
};

const deleteQuestion = (id) => {
  return http.delete(`${API_URL}/${id}`, { headers: authHeader() });
};

const getByDean = (params) => {
  let config = {
    headers: authHeader(),
    params,
  };
  return http.get(`${API_URL}/find-by-dean`, config);
};
const questionService = {
  getAll,
  getById,
  createQuestion,
  getByAdviserId,
  saveAnswer,
  deleteQuestion,
  getByDean,
};

export default questionService;

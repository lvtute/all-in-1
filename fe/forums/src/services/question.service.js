import http from "./http-common";

const API_URL = "/question";

const getAll = (params) => {
  return http.get(API_URL, { params });
};

const getById = (questionId) => {
  return http.get(`${API_URL}/${questionId}`);
}

const createQuestion = (requestBody) => {
  return http.post(API_URL, requestBody);
};

const questionService = {
  getAll,
  getById,
  createQuestion,
};

export default questionService;

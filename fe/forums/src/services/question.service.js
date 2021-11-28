import http from "./http-common";

const getAll = (params) => {
  return http.get("/question", { params });
};

const questionService = {
  getAll,
};

export default questionService;

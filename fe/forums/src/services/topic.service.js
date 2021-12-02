import http from "./http-common";

const getById = (id) => {
  return http.get(`/topic/${id}`);
};

const topicService = {
  getById,
};

export default topicService;

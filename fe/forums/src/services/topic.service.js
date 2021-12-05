import http from "./http-common";

const getById = (id) => {
  return http.get(`/topic/find-by-faculty-id/${id}`);
};

const topicService = {
  getById,
};

export default topicService;

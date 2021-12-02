import http from "./http-common";

const API_URL = "/question";

const getAll = (params) => {
  return http.get(API_URL, { params });
};

const createQuestion = (requestBody) => {
  requestBody = {
    ...requestBody,
    agreeToReceiveEmailNotification: (!!requestBody.agreeToReceiveEmailNotification ? "true" : "false"),
    facultyId: (isNaN(requestBody.facultyId) ? "" : requestBody.facultyId),
    topicId: (isNaN(requestBody.topicId) ? "" : requestBody.topicId),
  };
  
  return http.post(API_URL, requestBody);

}

const questionService = {
  getAll,
  createQuestion,
};

export default questionService;

import http from "./http-common";

const getAll = () => {
  return http.get("/faculty");
};

const facultyService = {
  getAll,
};

export default facultyService;

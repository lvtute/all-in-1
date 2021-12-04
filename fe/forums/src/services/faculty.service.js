import http from "./http-common";

const getAll = () => {
  return http.get("/faculty");
};


const getById = (id = 0) => {
  return http.get(`/faculty/${id}`);
};

const deleteById = (id = 0) => {
  return http.delete(`/faculty/${id}`);
};

const update = (updateRequestObject) => {
  return http.put("/faculty", updateRequestObject);
};

const facultyService = {
  getAll,
  getById,
  deleteById,
  update,
};

export default facultyService;

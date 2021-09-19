import http from "./http-common";

const getAll = () => {
  return http.get("/role");
};

const roleService = {
  getAll,
};

export default roleService;

import authHeader from "./auth-header";
import http from "./http-common";

const API_URL = "/chart";

const getFacultyUserPieChartData = () => {
  return http.get(`${API_URL}/faculty-user-pie-chart`, {
    headers: authHeader(),
  });
};

const chartService = {
  getFacultyUserPieChartData,
};

export default chartService;

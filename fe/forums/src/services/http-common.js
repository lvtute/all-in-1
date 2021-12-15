import axios from "axios";
import history from "../history";

let http = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});
http.interceptors.response.use(
  (response) => {
    return Promise.resolve(response);
  },
  (error) => {
    let status = error.response.status;
    if (status === 404) {
      history.push(`/404`);
      return Promise.reject(error);
    } else if (status === 401) {
      console.log("intercepted");
      if (error.response?.data?.includes("Bad credentials")) {
        // don't redirect to 401 if this is from login form
        console.log("intercepted1");
        return Promise.reject(error);
      } else {
        console.log("intercepted3");
        history.push(`/401`);
        return Promise.reject(error);
      }
    } else {
      console.log("intercepted4");
      return Promise.reject(error);
    }
  }
);

export default http;

import axios from "axios";
import history from "../history";
// import {Redirect} from "react-router-dom";
import { LOGIN_PATH } from "./constants";

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
    if (status === 401 || status === 404) {
      if (error.response?.data?.includes("Bad credentials")) {
        // don't redirect to 401 if this is from login form
        return Promise.reject(error);
      }
      if (
        error.response?.data?.includes(
          "Full authentication is required to access this resource"
        )
      ) {
        console.log("I was here");
        history.push(LOGIN_PATH);
      }
      history.push(`/${status}`);
    } else {
      return Promise.reject(error);
    }
  }
);

export default http;

import { Slide } from "react-toastify";


export const TOASTIFY_CONFIGS = {
  position: "top-right",
  autoClose: 4000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
  progress: undefined,
  transition: Slide,
};
export const HOME_PATH = "/home";
export const ADMIN_PATH = "/admin";
export const LOGIN_PATH = "/login";
export const QUESTION_CREATOR_PATH = "/create-question";

export const USER_SUB_PATH = "/user";
export const FACULTY_SUB_PATH = "/faculty";
// ADMIN PATHS
export const USER_MANAGEMENT_PATH = ADMIN_PATH + USER_SUB_PATH ;
export const FACULTY_MANAGEMENT_PATH = ADMIN_PATH + FACULTY_SUB_PATH ;

/**
 * (register/login/logout actions)
 */

import { LOGIN_SUCCESS, LOGOUT } from "./types";

import AuthService from "../services/auth.service";

import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../services/constants";

export const login = (username, password) => (dispatch) => {
  return AuthService.login(username, password).then(
    (data) => {
      dispatch({
        type: LOGIN_SUCCESS,
        payload: { user: data },
      });
      toast.success(`Welcome back, ${data.username}!`);

      return Promise.resolve();
    },
    (error) => {
      toast.error("Wrong username or password!", TOASTIFY_CONFIGS);

      return Promise.reject();
    }
  );
};

export const logout = () => (dispatch) => {
  AuthService.logout();
  dispatch({
    type: LOGOUT,
  });
};

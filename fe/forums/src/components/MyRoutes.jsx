import { Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import AdminDashBoard from "../pages/AdminDashBoard";
import Home from "../pages/Home";
import Login from "../pages/Login";
import QuestionCreator from "../pages/QuestionCreator";
import {
  ADMIN_PATH,
  HOME_PATH,
  LOGIN_PATH,
  QUESTION_CREATOR_PATH,
} from "../services/constants";

const MyRoutes = () => {
  return (
    <>
      <Switch>
        <Route path={LOGIN_PATH}>
          <Login />
        </Route>
        <Route path={ADMIN_PATH}>
          <AdminDashBoard />
        </Route>
        <Route path={HOME_PATH}>
          <Home />
        </Route>
        <Route path={QUESTION_CREATOR_PATH}>
          <QuestionCreator />
        </Route>
      </Switch>
      <ToastContainer />
    </>
  );
};
export default MyRoutes;

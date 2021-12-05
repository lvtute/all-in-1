import { Route, Switch, Redirect } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import AdminDashBoard from "../pages/AdminDashBoard";
import Home from "../pages/Home";
import Login from "../pages/Login";
import QuestionCreator from "../pages/QuestionCreator";
import {
  ADMIN_PATH,
  ADVISER_PATH,
  HOME_PATH,
  LOGIN_PATH,
  QUESTION_CREATOR_PATH,
} from "../services/constants";
import AdviserDashboard from "../pages/AdviserDashboard";
const MyRoutes = () => {
  return (
    <>
      <Switch>
        <Route
          exact
          path="/"
          render={() => {
            return <Redirect to="/home" />;
          }}
        />
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
        <Route path={ADVISER_PATH}>
          <AdviserDashboard />
        </Route>
      </Switch>
      <ToastContainer />
    </>
  );
};
export default MyRoutes;

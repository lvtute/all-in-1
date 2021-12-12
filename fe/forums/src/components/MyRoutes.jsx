import { Route, Switch, Redirect } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import AdminDashBoard from "../pages/AdminDashBoard";
import Home from "../pages/Home";
import Login from "../pages/Login";
import QuestionCreator from "../pages/QuestionCreator";
import QuestionReplier from "../pages/QuestionReplier";
import Page404 from "../pages/Page404";
import Page401 from "../pages/Page401";
import Page403 from "../pages/Page403";
import {
  ADMIN_PATH,
  ADVISER_PATH,
  DEAN_PATH,
  HOME_PATH,
  LOGIN_PATH,
  QUESTION_CREATOR_PATH,
  QUESTION_REPLIER,
  ROLE_ADVISER,
  ROLE_DEAN,
} from "../services/constants";
import AdviserDashboard from "../pages/AdviserDashboard";
import { useSelector } from "react-redux";
import DeanDashBoard from "../pages/DeanDashboard";

const MyRoutes = () => {
  const { user } = useSelector((state) => state.auth);
  let role = !!user ? user.role : "";

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
        <Route path={`${HOME_PATH}/:id?`}>
          <Home />
        </Route>
        <Route path={QUESTION_CREATOR_PATH}>
          <QuestionCreator />
        </Route>
        <Route path={ADVISER_PATH}>
          {role === ROLE_ADVISER ? (
            <AdviserDashboard />
          ) : (
            <Redirect to="/403" />
          )}
        </Route>
        <Route path={DEAN_PATH}>
          {role === ROLE_DEAN ? <DeanDashBoard /> : <Redirect to="/403" />}
        </Route>
        <Route
          path={`${QUESTION_REPLIER}/:id`}
          children={
            role === ROLE_ADVISER || role === ROLE_DEAN ? (
              <QuestionReplier />
            ) : (
              <Redirect to="/403" />
            )
          }
        />
        <Route path="/401">
          <Page401 />
        </Route>
        <Route path="/403">
          <Page403 />
        </Route>
        <Route path="*">
          <Page404 />
        </Route>
      </Switch>
      <ToastContainer />
    </>
  );
};
export default MyRoutes;

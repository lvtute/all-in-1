import Login from "../pages/Login";
import AdminDashBoard from "../pages/AdminDashBoard";
import { Switch, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import Home from "../pages/Home";
import { ADMIN_PATH, HOME_PATH, LOGIN_PATH } from "../services/constants";

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
    </Switch>
    <ToastContainer/>
    </>
  );
};
export default MyRoutes;

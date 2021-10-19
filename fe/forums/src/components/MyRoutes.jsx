import Login from "../pages/Login";
import AdminDashBoard from "../pages/AdminDashBoard";
import { Switch, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";

const MyRoutes = () => {
  return (
    <>
    <Switch>
      <Route path="/login">
        <Login />
      </Route>
      <Route path="/admin">
        <AdminDashBoard />
      </Route>
    </Switch>
    <ToastContainer/>
    </>
  );
};
export default MyRoutes;

import Login from "../pages/Login";
import AdminDashBoard from "../pages/AdminDashBoard";
import UserManagerTable from "../components/UserManagerTable";
import { Switch, Route } from "react-router-dom";

const MyRoutes = () => {
  return (
    <Switch>
      <Route path="/login">
        <Login />
      </Route>
      <Route path="/admin">
        <AdminDashBoard />
      </Route>
      <Route path="/table">
        <UserManagerTable />
      </Route>
    </Switch>
  );
};
export default MyRoutes;

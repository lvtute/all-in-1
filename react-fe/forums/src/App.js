import "./App.css";

// pages
import Login from "./pages/Login";
import AdminDashBoard from "./pages/AdminDashBoard";

import { Route, Switch } from "react-router-dom";
import Layout from "./layout/Layout";

function App() {
  return (
    <div>
      <div>
        <Layout>
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/admin" component={AdminDashBoard} />
            {/* <Route component={NotFound} /> */}
          </Switch>
        </Layout>
      </div>
    </div>
  );
}

export default App;

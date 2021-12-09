import "./App.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import "react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css";
import "react-toastify/dist/ReactToastify.css";

import { Router } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import MyRoutes from "./components/MyRoutes";
import history from "./history";

function App() {
  return (
    <Router history={history}>
      <div>
        <NavigationBar />
        <MyRoutes />
      </div>
    </Router>
  );
}
export default App;

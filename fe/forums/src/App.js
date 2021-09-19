import "./App.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import "react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css";
import "react-toastify/dist/ReactToastify.css";

import { BrowserRouter as Router } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import MyRoutes from "./components/MyRoutes";

function App() {
  return (
    <>
    <Router>
      <div>
        <NavigationBar />
        <MyRoutes />
      </div>
    </Router>
    </>
  );
}
export default App;

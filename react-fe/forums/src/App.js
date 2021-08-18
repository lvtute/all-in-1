import "./App.css";

import { BrowserRouter as Router } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import MyRoutes from "./components/MyRoutes";

function App() {
  return (
    <Router>
      <div>
        <NavigationBar />
        <MyRoutes />
      </div>
    </Router>
  );
}
export default App;

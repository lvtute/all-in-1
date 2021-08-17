// router
import { BrowserRouter as Router } from "react-router-dom";
import NavigationBar from "../components/NavigationBar.jsx";

const Layout = ({ children }) => {
  return (
    <Router>
      <NavigationBar/>
      <main fluid="xl">{children}</main>

      {/* <Footer /> */}
    </Router>
  );
};

export default Layout;

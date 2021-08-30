import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import "bootstrap/dist/css/bootstrap.min.css";
// import 'bootstrap/dist/js/bootstrap';

ReactDOM.render(
  // <React.StrictMode> // https://stackoverflow.com/questions/67415620/fixing-index-js1-warning-using-unsafe-componentwillreceiveprops-in-strict-mod
    <App />,
  // </React.StrictMode>,
  document.getElementById("root")
);

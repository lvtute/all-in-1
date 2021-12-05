import { Form, Button } from "react-bootstrap";
import CenteredTitle from "../components/CenteredTitle";
import { useDispatch, useSelector } from "react-redux";
import { Redirect, useHistory } from "react-router-dom";
import { useState } from "react";

import { login } from "../actions/auth";
import { HOME_PATH } from "../services/constants";

const Login = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const { isLoggedIn } = useSelector((state) => state.auth);

  const [loading, setLoading] = useState(false);

  const handleLogin = (event) => {
    event.preventDefault();

    setLoading(true);
    const formData = Object.fromEntries(new FormData(event.target).entries());
    const { username, password } = formData;

    dispatch(login(username, password))
      .then(() => {
        history.push({ HOME_PATH });
        // window.location.reload();
      })
      .catch(() => {
        setLoading(false);
      });
  };

  if (isLoggedIn) {
    return <Redirect to={HOME_PATH} />;
  }

  return (
    <>
      <CenteredTitle title="ĐĂNG NHẬP" />
      <Form className="centered-form" onSubmit={handleLogin}>
        <Form.Group controlId="formBasicEmail">
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter username"
            name="username"
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Password"
            name="password"
          />
        </Form.Group>
        {/* <Form.Group controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Remember me" />
        </Form.Group> */}
        <Button variant="primary" type="submit" disabled={loading}>
          {loading && (
            <span className="spinner-border spinner-border-sm"></span>
          )}
          Submit
        </Button>
      </Form>
    </>
  );
};
export default Login;

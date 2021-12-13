import { ListGroup, Container, Row, Col } from "react-bootstrap";
import UserManager from "../components/admin-dashboard/user-manager/UserManager";
import QuestionManager from "../components/admin-dashboard/question-manager/QuestionManager";
import FacultyManager from "../components/admin-dashboard/faculty-manager/FacultyManager";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useRouteMatch,
} from "react-router-dom";
import { ToastContainer } from "react-toastify";
import PasswordChanger from "../components/PasswordChanger";

const AdminDashBoard = () => {
  let match = useRouteMatch();
  // path enum
  const adminPath = Object.freeze({
    user: `${match.url}/user`,
    question: `${match.url}/question`,
    faculty: `${match.url}/faculty`,
    passwordChanger: `${match.url}/password-changer`,
  });

  return (
    <Router>
      <div>
        <Container fluid>
          <h2 className="left-title">ADMIN DASHBOARD</h2>
          <Row>
            <Col md="3">
              <ListGroup>
                <Link to={`${adminPath.user}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý Người dùng
                  </ListGroup.Item>
                </Link>

                <Link to={`${adminPath.faculty}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý Khoa
                  </ListGroup.Item>
                </Link>
                <Link to={`${adminPath.passwordChanger}`}>
                  <ListGroup.Item action variant="info">
                    Đổi mật khẩu
                  </ListGroup.Item>
                </Link>
              </ListGroup>
            </Col>

            <Col md="9">
              <Switch>
                <Route path={adminPath.user}>
                  <UserManager />
                </Route>
                <Route path={adminPath.question}>
                  <QuestionManager />
                </Route>
                <Route path={adminPath.faculty}>
                  <FacultyManager />
                </Route>
                <Route path={adminPath.passwordChanger}>
                  <PasswordChanger />
                </Route>
              </Switch>
            </Col>
          </Row>
        </Container>
        <ToastContainer />
      </div>
    </Router>
  );
};
export default AdminDashBoard;

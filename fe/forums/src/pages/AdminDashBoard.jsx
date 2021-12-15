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
  Redirect,
} from "react-router-dom";
import { ToastContainer } from "react-toastify";
import PasswordChanger from "../components/PasswordChanger";
import AdminChart from "../components/admin-dashboard/AdminChart";
import { ADMIN_PATH } from "../services/constants";

const AdminDashBoard = () => {
  let match = useRouteMatch();
  // path enum
  const adminPath = Object.freeze({
    user: `${match.url}/user`,
    question: `${match.url}/question`,
    faculty: `${match.url}/faculty`,
    passwordChanger: `${match.url}/password-changer`,
    chart: `${match.url}/chart`,
  });

  return (
    <Router>
      <div>
        <Container fluid>
          <h2 className="left-title">ADMIN DASHBOARD</h2>
          <Row>
            <Col md="3">
              <ListGroup>
                <Link to={`${adminPath.chart}`}>
                  <ListGroup.Item action variant="info">
                    Thống kê và biểu đồ
                  </ListGroup.Item>
                </Link>
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
                <Route
                  exact
                  path={ADMIN_PATH}
                  render={() => {
                    return <Redirect to={adminPath.chart} />;
                  }}
                />
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
                <Route path={adminPath.chart}>
                  <AdminChart />
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

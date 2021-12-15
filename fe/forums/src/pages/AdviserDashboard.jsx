import { ListGroup, Container, Row, Col } from "react-bootstrap";
import QuestionManager from "../components/adviser-dashboard/QuestionManager";
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
import { ADVISER_PATH } from "../services/constants";
import AdviserChart from "../components/adviser-dashboard/AdviserChart";

const AdminDashBoard = () => {
  let match = useRouteMatch();
  // path enum
  const adviserPath = Object.freeze({
    question: `${match.url}/question`,
    passwordChanger: `${match.url}/password-changer`,
    chart: `${match.url}/chart`,
  });

  return (
    <Router>
      <div>
        <Container fluid>
          <h2 className="left-title">Trang tư vấn viên</h2>
          <Row>
            <Col md="3">
              <ListGroup>
                {/* <Link to={`${adviserPath.chart}`}>
                  <ListGroup.Item action variant="info">
                    Thống kê và biểu đồ
                  </ListGroup.Item>
                </Link> */}

                <Link to={`${adviserPath.question}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý câu hỏi
                  </ListGroup.Item>
                </Link>

                <Link to={`${adviserPath.passwordChanger}`}>
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
                  path={ADVISER_PATH}
                  render={() => {
                    return <Redirect to={adviserPath.chart} />;
                  }}
                />
                <Route path={adviserPath.question}>
                  <QuestionManager />
                </Route>
                <Route path={adviserPath.passwordChanger}>
                  <PasswordChanger />
                </Route>
                <Route path={adviserPath.chart}>
                  <AdviserChart />
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

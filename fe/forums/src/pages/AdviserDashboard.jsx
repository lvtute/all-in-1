import { ListGroup, Container, Row, Col } from "react-bootstrap";
import QuestionManager from "../components/adviser-dashboard/QuestionManager";
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
  const adviserPath = Object.freeze({
    question: `${match.url}/question`,
    passwordChanger: `${match.url}/password-changer`,
  });

  return (
    <Router>
      <div>
        <Container fluid>
          <h2 className="left-title">Trang tư vấn viên</h2>
          <Row>
            <Col md="3">
              <ListGroup>
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
                <Route path={adviserPath.question}>
                  <QuestionManager />
                </Route>
                <Route path={adviserPath.passwordChanger}>
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

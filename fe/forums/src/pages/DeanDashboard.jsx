import { ListGroup, Container, Row, Col } from "react-bootstrap";
import QuestionManager from "../components/dean-dashboard/QuestionManager";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useRouteMatch,
} from "react-router-dom";
import { ToastContainer } from "react-toastify";
import PasswordChanger from "../components/PasswordChanger";
import TopicManager from "../components/dean-dashboard/TopicManager";
import UserManager from "../components/dean-dashboard/UserManager";

const DeanDashBoard = () => {
  let match = useRouteMatch();
  // path enum
  const deanPath = Object.freeze({
    question: `${match.url}/question`,
    passwordChanger: `${match.url}/password-changer`,
    topic :`${match.url}/topic`,
    user: `${match.url}/user`
  });

  return (
    <Router>
      <div>
        <Container fluid>
          <h2 className="left-title">Trang Trưởng khoa</h2>
          <Row>
            <Col md="3">
              <ListGroup>
                <Link to={`${deanPath.question}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý câu hỏi
                  </ListGroup.Item>
                </Link>

                <Link to={`${deanPath.topic}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý chủ đề
                  </ListGroup.Item>
                </Link>

                <Link to={`${deanPath.user}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý Tư vấn viên
                  </ListGroup.Item>
                </Link>

                <Link to={`${deanPath.passwordChanger}`}>
                  <ListGroup.Item action variant="info">
                    Đổi mật khẩu
                  </ListGroup.Item>
                </Link>
              </ListGroup>
            </Col>

            <Col md="9">
              <Switch>
                <Route path={deanPath.question}>
                  <QuestionManager />
                </Route>
                <Route path={deanPath.topic}>
                  <TopicManager />
                </Route>
                <Route path={deanPath.user}>
                  <UserManager />
                </Route>
                <Route path={deanPath.passwordChanger}>
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
export default DeanDashBoard;

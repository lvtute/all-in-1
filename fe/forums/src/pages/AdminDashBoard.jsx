import { ListGroup, Container, Row, Col } from "react-bootstrap";
import UserManager from "../components/admin-dashboard/user-manager/UserManager";
import QuestionManager from "../components/admin-dashboard/question-manager/QuestionManager";
import FacultyManager from "../components/admin-dashboard/faculty-manager/FacultyManager";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

const AdminDashBoard = () => {
  // path enum
  const adminPath = Object.freeze({
    user: "/admin/users",
    question: "/admin/questions",
    faculty: "/admin/faculty",
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
                    Quản lý User
                  </ListGroup.Item>
                </Link>

                <Link to={`${adminPath.question}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý câu hỏi
                  </ListGroup.Item>
                </Link>

                <Link to={`${adminPath.faculty}`}>
                  <ListGroup.Item action variant="info">
                    Quản lý phòng ban
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
              </Switch>
            </Col>
          </Row>
        </Container>
      </div>
    </Router>
  );
};
export default AdminDashBoard;

import { ListGroup, Container, Row, Col } from "react-bootstrap";
import CenteredTitle from "../components/CenteredTitle";
import UserManager from "../components/UserManager";

const AdminDashBoard = () => {

  // states

  // functions
  const showUserManager = () => {
    console.log("I'm at User manager");
  };
  const showQuestionManager = () => {
    console.log("I'm at Question manager");
  };
  const showFacultyManager = () => {
    console.log("I'm at Faculty manager");
  };

  // render
  return (
    <Container fluid>
      <CenteredTitle title="ADMIN DASHBOARD"/>
      <Row>
        <Col md="3">
          <ListGroup>
            <ListGroup.Item onClick={showUserManager} action variant="info">
              Quản lý User
            </ListGroup.Item>
            <ListGroup.Item onClick={showQuestionManager} action variant="info">
              Quản lý câu hỏi
            </ListGroup.Item>
            <ListGroup.Item onClick={showFacultyManager} action variant="info">
              Quản lý phòng ban
            </ListGroup.Item>
          </ListGroup>
        </Col>
        <Col md="9">
          <UserManager/>
        </Col>
      </Row>
    </Container>
  );
};
export default AdminDashBoard;

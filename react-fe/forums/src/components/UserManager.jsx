import { Table, Container, Row, Button, ButtonGroup } from "react-bootstrap";
import ButtonCreateNew from "./ButtonCreateNew";

const UserManager = () => {
  return (
    <Container fluid>
      <Row style={{marginBottom:"2em"}}>
        <h4>Quản lý người dùng</h4>
        <ButtonCreateNew label="Đăng ký người dùng mới" />
      </Row>
      <Row>
        <Table striped bordered hover size="sm">
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Full name</th>
              <th>Email</th>
              <th>Faculty</th>
              <th>Role</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>1</td>
              <td>lvtute</td>
              <td>Luong Van Thuan</td>
              <td>lvtute@gmail.com</td>
              <td>Information Technology</td>
              <td>Adviser</td>
              <td>
                <ButtonGroup aria-label="Basic example">
                  <Button variant="warning">Sửa</Button>
                  <Button variant="danger">Xóa</Button>
                </ButtonGroup>
              </td>
            </tr>
            <tr>
              <td>1</td>
              <td>lvtute</td>
              <td>Luong Van Thuan</td>
              <td>lvtute@gmail.com</td>
              <td>Information Technology</td>
              <td>Adviser</td>
              <td>
                <ButtonGroup aria-label="Basic example">
                  <Button variant="warning">Sửa</Button>
                  <Button variant="danger">Xóa</Button>
                </ButtonGroup>
              </td>
            </tr>
            <tr>
              <td>1</td>
              <td>lvtute</td>
              <td>Luong Van Thuan</td>
              <td>lvtute@gmail.com</td>
              <td>Information Technology</td>
              <td>Adviser</td>
              <td>
                <ButtonGroup aria-label="Basic example">
                  <Button variant="warning">Sửa</Button>
                  <Button variant="danger">Xóa</Button>
                </ButtonGroup>
              </td>
            </tr>
          </tbody>
        </Table>
      </Row>
    </Container>
  );
};
export default UserManager;

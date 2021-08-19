import { useState } from "react";
import {
  Table,
  Container,
  Row,
  Button,
  ButtonGroup,
} from "react-bootstrap";
import PagingBar from "../../PagingBar";
import ButtonCreateNew from "../ButtonCreateNew";
import UserRegisterModal from "./UserRegisterModal";

const UserManager = () => {
  // states
  const [registerModalShow, setRegisterModalShow] = useState(false);

  return (
    <Container fluid>
      <Row style={{ marginBottom: "2em" }}>
        <h4>Quản lý người dùng</h4>
        <ButtonCreateNew
          label="Đăng ký người dùng mới"
          onClickFunction={() => setRegisterModalShow(true)}
        />
        <UserRegisterModal
          isOpened= {registerModalShow}
          onCloseEvent={() => setRegisterModalShow(false)}
        />
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
      <PagingBar />
    </Container>
  );
};
export default UserManager;

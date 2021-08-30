import { useState } from "react";
import { Container, Row } from "react-bootstrap";
import ButtonCreateNew from "../ButtonCreateNew";
import UserTable from "../user-manager/UserTable";
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
          isOpened={registerModalShow}
          onCloseEvent={() => setRegisterModalShow(false)}
        />
      </Row>
      <Row>
        <UserTable />
      </Row>
    </Container>
  );
};
export default UserManager;

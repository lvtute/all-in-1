import { useState } from "react";
import { Container, Row } from "react-bootstrap";
import ButtonCreateNew from "../ButtonCreateNew";
import UserTable from "../user-manager/UserTable";
import UserRegisterModal from "./UserRegisterModal";

const UserManager = () => {
  // state to show or hide modal (for register and update user)
  const [registerModalShow, setRegisterModalShow] = useState(false);

  // state for updating user- modal acknowledgement
  const [isUpdating, setUpdatingStatus] = useState(false);
  const [userIdToUpdate, setUserIdToUpdate] = useState(0);

  const openRegisteringModal = () => {
    setUpdatingStatus(false);
    setRegisterModalShow(true);
  };

  const openUpdatingModal = (id) => {
    setUpdatingStatus(true);
    setRegisterModalShow(true);
    setUserIdToUpdate(id);

  };

  return (
    <Container fluid>
      <Row style={{ marginBottom: "2em" }}>
        <h4>Quản lý người dùng</h4>
        <ButtonCreateNew
          label="Đăng ký người dùng mới"
          onClickFunction={() => openRegisteringModal()}
        />
        <UserRegisterModal
          isOpened={registerModalShow}
          isUpdating={isUpdating}
          onCloseFunction={() => setRegisterModalShow(false)}
          id={userIdToUpdate}
        />
      </Row>
      <Row>
        <UserTable openUpdatingModal={openUpdatingModal} />
      </Row>
    </Container>
  );
};
export default UserManager;

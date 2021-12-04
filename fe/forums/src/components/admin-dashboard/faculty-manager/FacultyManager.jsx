// const FacultyManager = () => {
//   return <h4>Faculty Manager</h4>;
// };
// export default FacultyManager;
import { useState } from "react";
import { Container, Row } from "react-bootstrap";
import ButtonCreateNew from "../ButtonCreateNew";
import FacultyTable from "../faculty-manager/FacultyTable";
import FacultyrRegisterModal from "./FacultyRegisterModal";

const FacultyManager = () => {
  // state to show or hide modal (for register and update user)
  const [registerModalShow, setRegisterModalShow] = useState(false);

  // state for updating user- modal acknowledgement
  const [isUpdating, setUpdatingStatus] = useState(false);
  const [facultyIdToUpdate, setFacultyIdToUpdate] = useState(0);

  const openRegisteringModal = () => {
    setUpdatingStatus(false);
    setRegisterModalShow(true);
  };

  const openUpdatingModal = (id) => {
    setUpdatingStatus(true);
    setRegisterModalShow(true);
    setFacultyIdToUpdate(id);

  };

  return (
    <Container fluid>
      <Row style={{ marginBottom: "2em" }}>
        <h4>Quản lý khoa</h4>
        <ButtonCreateNew
          label="Thêm khoa mới"
          onClickFunction={() => openRegisteringModal()}
        />
        <FacultyrRegisterModal
          isOpened={registerModalShow}
          isUpdating={isUpdating}
          onCloseFunction={() => setRegisterModalShow(false)}
          id={facultyIdToUpdate}
        />
      </Row>
      <Row>
        <FacultyTable openUpdatingModal={openUpdatingModal} />
      </Row>
    </Container>
  );
};
export default FacultyManager;

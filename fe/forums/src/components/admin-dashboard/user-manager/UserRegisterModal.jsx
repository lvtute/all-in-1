import { Modal, Form, Col, Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import facultyService from "../../../services/faculty.service";
import roleService from "../../../services/role.service";
import authenService from "../../../services/auth.service";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../../../services/constants";

const UserRegisterModal = ({ isOpened, onCloseFunction }) => {
  const [isSubmitButtonDisabled, setSubmitButtonDisabled] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const formDataObj = Object.fromEntries(formData.entries());
    setSubmitButtonDisabled(true);

    authenService
      .register(formDataObj)
      .then((res) => {
        console.log(res.data);
        toast.success(res.data?.message, TOASTIFY_CONFIGS);
        onCloseFunction();
        setSubmitButtonDisabled(false);
      })
      .catch((err) => {
        console.log(err);
        setSubmitButtonDisabled(false);
      });
  };

  const [facultyList, setFacultyList] = useState([]);
  const [roleList, setRoleList] = useState([]);

  useEffect(() => {
    facultyService.getAll().then((res) => setFacultyList(res.data));
    roleService.getAll().then((res) => setRoleList(res.data));
  }, []);

  return (
    <Modal animation={false} show={isOpened} onHide={onCloseFunction}>
      <Modal.Header closeButton>
        <Modal.Title id="example-custom-modal-styling-title">
          Đăng ký
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="formGridFullName">
            <Form.Label>Họ tên</Form.Label>
            <Form.Control placeholder="Full name" name="fullName" />
          </Form.Group>
          <Form.Row>
            <Form.Group as={Col} controlId="formGridEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                name="email"
              />
            </Form.Group>

            <Form.Group as={Col} controlId="formGridUsername">
              <Form.Label>Username</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter username"
                name="username"
              />
            </Form.Group>
          </Form.Row>

          <Form.Row>
            <Form.Group as={Col} controlId="formGridRole">
              <Form.Label>Role</Form.Label>
              <Form.Control as="select" name="roleId">
                {roleList?.map((role) => (
                  <option key={role.id} value={role.id}>
                    {role.name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
            <Form.Group as={Col} controlId="formGridFaculty">
              <Form.Label>Khoa</Form.Label>
              <Form.Control as="select" name="facultyId">
                {facultyList?.map((faculty) => (
                  <option key={faculty.id} value={faculty.id}>
                    {faculty.name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Form.Row>

          <Button
            variant="primary"
            type="submit"
            disabled={isSubmitButtonDisabled}
          >
            Submit
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};
export default UserRegisterModal;

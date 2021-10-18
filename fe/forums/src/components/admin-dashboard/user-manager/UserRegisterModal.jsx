import { Modal, Form, Col, Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import facultyService from "../../../services/faculty.service";
import roleService from "../../../services/role.service";
import authenService from "../../../services/auth.service";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../../../services/constants";
import userService from "../../../services/user.service";
import { useHistory } from "react-router";

const UserRegisterModal = ({ isOpened, isUpdating, onCloseFunction, id }) => {
  const [isSubmitButtonDisabled, setSubmitButtonDisabled] = useState(false);

  const [userInfoObject, setUserInfoObject] = useState(Object);

  const [selectedRoleId, setSelectedRoleId] = useState(0);
  const [selectedFacultyId, setSelectedFacultyId] = useState(0);

  const history = useHistory();


  useEffect(() => {
    if (isUpdating) {
      userService.getById(id).then((res) => {
        setUserInfoObject(res.data);
        setSelectedRoleId(res.data?.role?.id);
        setSelectedFacultyId(res.data?.faculty?.id);
      });
    }
  }, [id, isUpdating, setSelectedRoleId, setSelectedFacultyId]);

  useEffect(() => {
    setUserInfoObject(null);
    setSelectedRoleId(0);
    setSelectedFacultyId(0);
  }, [isOpened, setSelectedRoleId, setSelectedFacultyId]);

  useEffect(() => {
    facultyService.getAll().then((res) => setFacultyList(res.data));
    roleService.getAll().then((res) => setRoleList(res.data));
  }, []);

  const getUserServiceRegisterOrUpdateFunction = () => {
    return isUpdating ? userService.update : authenService.register;
  };

  const handleSubmit = (e) => {
    e.preventDefault();


    const formData = new FormData(e.target);
    const formDataObj = Object.fromEntries(formData.entries());
    setSubmitButtonDisabled(true);

    getUserServiceRegisterOrUpdateFunction()(formDataObj)
      .then((res) => {
        toast.success(res.data.message, TOASTIFY_CONFIGS);
        onCloseFunction();
        setSubmitButtonDisabled(false);

        history.go(0);        
      })
      .catch((err) => {
        setSubmitButtonDisabled(false);
      });
  };

  const [facultyList, setFacultyList] = useState([]);
  const [roleList, setRoleList] = useState([]);

  // use this for onChange to prevent warning
  // const doNothing = (event) => {};

  return (
    <Modal
      animation={false}
      show={isOpened}
      onHide={() => {
        onCloseFunction();
        // setUseInfoObject(null);
      }}
    >
      <Modal.Header closeButton>
        <Modal.Title id="example-custom-modal-styling-title">
          {isUpdating ? "Chỉnh sửa thông tin" : "Đăng ký"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          {isUpdating ? (
            <Form.Group className="mb-3">
              <Form.Label>ID:</Form.Label>
              <Form.Control type="text" value={`${id}`} readOnly name="id" />
            </Form.Group>
          ) : (
            ""
          )}

          <Form.Group controlId="formGridFullName">
            <Form.Label>Họ tên</Form.Label>
            <Form.Control
              placeholder="Full name"
              name="fullName"
              defaultValue={userInfoObject ? userInfoObject.fullName : ""}
              
            />
          </Form.Group>
          <Form.Row>
            <Form.Group as={Col} controlId="formGridEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="Enter email"
                name="email"
                defaultValue={userInfoObject ? userInfoObject.email : ""}
          
              />
            </Form.Group>
            {
              // field username is not allowed to be updated
              !isUpdating ? (
                <Form.Group as={Col} controlId="formGridUsername">
                  <Form.Label>Username</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter username"
                    name="username"
                  />
                </Form.Group>
              ) : (
                ""
              )
            }
          </Form.Row>

          <Form.Row>
            <Form.Group as={Col} controlId="formGridRole">
              <Form.Label>Role</Form.Label>
              <Form.Control
                as="select"
                name="roleId"
                value={selectedRoleId}
                onChange={(event) => {
                  setSelectedRoleId(event.target.value);
                }}
              >
                <option value="">Choose role...</option>
                {roleList?.map((role) => (
                  <option key={role.id} value={role.id}>
                    {role.name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
            <Form.Group as={Col} controlId="formGridFaculty">
              <Form.Label>Khoa</Form.Label>
              <Form.Control
                as="select"
                name="facultyId"
                value={selectedFacultyId}
                onChange={(event) => {
                  setSelectedFacultyId(event.target.value);
                }}
              >
                <option value="">Choose faculty...</option>
                {facultyList?.map((faculty) => (
                  <option key={faculty.id} value={faculty.id}>
                    {faculty.name}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Form.Row>

          <Button
            variant={isUpdating ? "warning" : "primary"}
            type="submit"
            disabled={isSubmitButtonDisabled}
            className="float-right"
          >
            Submit
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};
export default UserRegisterModal;

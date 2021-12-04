import { Modal, Form, Col, Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import facultyService from "../../../services/faculty.service";
import authenService from "../../../services/auth.service";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../../../services/constants";
import { useHistory } from "react-router";

const FacultyRegisterModal = ({ isOpened, isUpdating, onCloseFunction, id }) => {
  const [isSubmitButtonDisabled, setSubmitButtonDisabled] = useState(false);

  const [facultyInfoObject, setFacultyInfoObject] = useState(Object);

  //const [selectedRoleId, setSelectedRoleId] = useState(0);
  //const [selectedFacultyId, setSelectedFacultyId] = useState(0);

  const history = useHistory();


  useEffect(() => {
    if (isUpdating) {
      facultyService.getById(id).then((res) => {
        setFacultyInfoObject(res.data);
       // setSelectedRoleId(res.data?.role?.id);
      //  setSelectedFacultyId(res.data?.faculty?.id);
      });
    }
  }, [id, isUpdating]);

  useEffect(() => {
    setFacultyInfoObject(null);
    // setSelectedRoleId(0);
    // setSelectedFacultyId(0);
  }, [isOpened]);

  useEffect(() => {
    facultyService.getAll().then((res) => setFacultyList(res.data));
    //roleService.getAll().then((res) => setRoleList(res.data));
  }, []);

  const getFacultyServiceRegisterOrUpdateFunction = () => {
    return isUpdating ? facultyService.update : authenService.register;
  };

  const handleSubmit = (e) => {
    e.preventDefault();


    const formData = new FormData(e.target);
    const formDataObj = Object.fromEntries(formData.entries());
    setSubmitButtonDisabled(true);

    getFacultyServiceRegisterOrUpdateFunction()(formDataObj)
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
  //const [roleList, setRoleList] = useState([]);

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
          {/* <Form.Group controlId="formGridName">
            <Form.Label>Tên khoa</Form.Label>
            <Form.Control
              placeholder="Faculty name"
              name="name"
              defaultValue={facultyInfoObject ? facultyInfoObject.name : ""}
              
            />
          </Form.Group> */}
          <Form.Row>
            <Form.Group as={Col} controlId="formGridName">
              <Form.Label>Email</Form.Label>
              <Form.Control
               // type="email"
                placeholder="Enter name"
                name="name"
                defaultValue={facultyInfoObject ? facultyInfoObject.name : ""}
          
              />
            </Form.Group>
            {/* {
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
            } */}
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
export default FacultyRegisterModal;

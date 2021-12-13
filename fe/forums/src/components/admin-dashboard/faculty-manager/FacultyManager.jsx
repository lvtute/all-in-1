import { useEffect, useState } from "react";
import {
  Button,
  Container,
  Form,
  Modal,
  Row,
  Spinner,
  Table,
} from "react-bootstrap";
import { toast } from "react-toastify";
import facultyService from "../../../services/faculty.service";
import { TOASTIFY_CONFIGS } from "../../../services/constants";
import ValidationMessage from "../../ValidationMessage";

const FacultyManager = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [modalConfirmDeleteShow, setModalConfirmDeleteShow] = useState(false);
  const [selectedFaculty, setSelectedFaculty] = useState(Object);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [trigger, triggerUseEffect] = useState(false); // this state is only used for refreshing (excecute useEffect)
  const [modalUpdateShow, setModalUpdateShow] = useState(false);
  const [modalCreateShow, setModalCreateShow] = useState(false);
  const [errorResponse, setErrorResponse] = useState(Object);

  const handleClose = () => setModalConfirmDeleteShow(false);
  const handleModalUpdateClose = () => setModalUpdateShow(false);
  const handleModalCreateClose = () => setModalCreateShow(false);

  useEffect(() => {
    setIsLoading(true);
    facultyService
      .getAll()
      .then((res) => {
        setIsLoading(false);
        setTableData(res.data);
        console.log(res.data);
      })
      .catch((err) => {
        setIsLoading(false);
        console.log(err.response?.data);
      });
  }, [trigger]);

  const onButtonDeleteClick = (faculty) => {
    setSelectedFaculty(faculty);
    setModalConfirmDeleteShow(true);
  };

  const handleDelete = (id) => {
    setIsSubmitting(true);
    facultyService
      .deleteById(id)
      .then((res) => {
        setIsSubmitting(false);
        setModalConfirmDeleteShow(false);
        toast.success("Xóa Khoa thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Xóa thất bại", TOASTIFY_CONFIGS);
        console.log(err.response.data);
      });
    console.log(id);
  };

  const onButtonUpdateClick = (faculty) => {
    setSelectedFaculty(faculty);
    setModalUpdateShow(true);
    setErrorResponse({});
  };

  const handleUpdate = () => {
    setIsSubmitting(true);
    facultyService
      .update(selectedFaculty)
      .then((res) => {
        setIsSubmitting(false);
        setModalUpdateShow(false);
        toast.success("Đổi tên khoa thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Đổi tên thất bại", TOASTIFY_CONFIGS);
        console.log(err.response.data);
        setErrorResponse(err.response.data);
      });
  };

  const handleNewNameChange = (e) => {
    setSelectedFaculty({ ...selectedFaculty, name: e.target.value });
  };

  const onButtonCreateClick = () => {
    setSelectedFaculty({});
    setModalCreateShow(true);
    setErrorResponse({});
  };

  const handleCreate = () => {
    setIsSubmitting(true);
    facultyService
      .create({ name: selectedFaculty.name })
      .then((res) => {
        setIsSubmitting(false);
        setModalCreateShow(false);
        toast.success("Tạo thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Tạo thất bại", TOASTIFY_CONFIGS);
        console.log(err.response.data);
        setErrorResponse(err.response.data);
      });
  };

  return (
    <>
      <h4>Quản lý khoa</h4>
      <Container>
        {isLoading && (
          <Row style={{ display: "flex", height: "80%" }}>
            <Spinner
              style={{ margin: "auto" }}
              animation="border"
              variant="primary"
            />
          </Row>
        )}
        <Button
          style={{ marginBottom: "1%" }}
          className="float-right"
          variant="outline-primary"
          onClick={onButtonCreateClick}
        >
          Tạo khoa mới
        </Button>
        <Row style={{ display: "block" }}>
          {!!tableData && (
            <>
              <Table bordered hover>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Tên khoa</th>
                    <th>Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  {tableData.map((faculty) => (
                    <tr key={faculty.id}>
                      <td>{faculty.id}</td>
                      <td>{faculty.name}</td>
                      <td>
                        <Button
                          onClick={() =>
                            onButtonUpdateClick({
                              id: faculty.id,
                              name: faculty.name,
                            })
                          }
                          variant="outline-warning"
                        >
                          Đổi tên
                        </Button>
                        <Button
                          onClick={() =>
                            onButtonDeleteClick({
                              id: faculty.id,
                              name: faculty.name,
                            })
                          }
                          variant="outline-danger"
                        >
                          Xóa
                        </Button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </>
          )}
        </Row>
        <Modal size="md" show={modalConfirmDeleteShow} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{`Xóa khoa "${selectedFaculty.name}"?`}</Modal.Title>
          </Modal.Header>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="danger"
              onClick={() => handleDelete(selectedFaculty.id)}
            >
              Xóa
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal size="md" show={modalUpdateShow} onHide={handleModalUpdateClose}>
          <Modal.Header closeButton>
            <Modal.Title>Đổi tên khoa</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Label>Nhập tên mới</Form.Label>
            <Form.Control
              type="text"
              defaultValue={selectedFaculty.name}
              onChange={handleNewNameChange}
            />
            <ValidationMessage
              errorResponse={errorResponse}
              field="name"
            ></ValidationMessage>
            <ValidationMessage
              errorResponse={errorResponse}
              field="error"
            ></ValidationMessage>
          </Modal.Body>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="warning"
              onClick={handleUpdate}
            >
              Đổi tên
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal size="md" show={modalCreateShow} onHide={handleModalCreateClose}>
          <Modal.Header closeButton>
            <Modal.Title>Tạo khoa mới</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Label>Nhập tên</Form.Label>
            <Form.Control
              type="text"
              defaultValue={selectedFaculty.name}
              onChange={handleNewNameChange}
            />
            <ValidationMessage
              errorResponse={errorResponse}
              field="name"
            ></ValidationMessage>
            <ValidationMessage
              errorResponse={errorResponse}
              field="error"
            ></ValidationMessage>
          </Modal.Body>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="primary"
              onClick={handleCreate}
            >
              Tạo
            </Button>
          </Modal.Footer>
        </Modal>
      </Container>
    </>
  );
};
export default FacultyManager;

import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import topicService from "../../services/topic.service";
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
import { TOASTIFY_CONFIGS } from "../../services/constants";

const TopicManager = () => {
  const { user } = useSelector((state) => state.auth);
  let faculty = !!user ? user.faculty : "";

  const [isLoading, setIsLoading] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [modalConfirmDeleteShow, setModalConfirmDeleteShow] = useState(false);
  const [selectedTopic, setSelectedTopic] = useState(Object);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [trigger, triggerUseEffect] = useState(false); // this state is only used for refreshing (excecute useEffect)
  const [modalUpdateShow, setModalUpdateShow] = useState(false);
  const [modalCreateShow, setModalCreateShow] = useState(false);

  const handleClose = () => setModalConfirmDeleteShow(false);
  const handleModalUpdateClose = () => setModalUpdateShow(false);
  const handleModalCreateClose = () => setModalCreateShow(false);

  useEffect(() => {
    setIsLoading(true);
    topicService
      .getByFacultyId(faculty.id)
      .then((res) => {
        setIsLoading(false);
        setTableData(res.data);
        console.log(res.data.map((u) => u.users));
      })
      .catch((err) => {
        setIsLoading(false);
        console.log(err.response?.data);
      });
  }, [faculty, trigger]);

  const onButtonDeleteClick = (topic) => {
    setSelectedTopic(topic);
    setModalConfirmDeleteShow(true);
  };

  const handleDelete = (id) => {
    setIsSubmitting(true);
    topicService
      .deleteById(id)
      .then((res) => {
        setIsSubmitting(false);
        setModalConfirmDeleteShow(false);
        toast.success("Xóa chủ đề thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Xóa thất bại", TOASTIFY_CONFIGS);
        console.log(err.response.data);
      });
    console.log(id);
  };

  const onButtonUpdateClick = (topic) => {
    setSelectedTopic(topic);
    setModalUpdateShow(true);
  };

  const handleUpdate = () => {
    setIsSubmitting(true);
    topicService
      .update(selectedTopic)
      .then((res) => {
        setIsSubmitting(false);
        setModalUpdateShow(false);
        toast.success("Đổi tên chủ đề thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Đổi tên thất bại", TOASTIFY_CONFIGS);
        console.log(err.response.data);
      });
  };

  const handleNewNameChange = (e) => {
    setSelectedTopic({ ...selectedTopic, name: e.target.value });
  };

  const onButtonCreateClick = () => {
    setSelectedTopic({});
    setModalCreateShow(true);
  };

  const handleCreate = () => {
    setIsSubmitting(true);
    topicService
      .create({ name: selectedTopic.name })
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
      });
  };

  return (
    <>
      <h4>{`Quản lý chủ đề trong khoa ${faculty.name}`}</h4>
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
          Tạo chủ đề mới
        </Button>
        <Row style={{ display: "block" }}>
          {!!tableData && (
            <>
              <Table bordered hover>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Tên chủ đề</th>
                    <th>Tư vấn viên</th>
                    <th>Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  {tableData.map((topic) => (
                    <tr key={topic.id}>
                      <td>{topic.id}</td>
                      <td>{topic.name}</td>
                      <td>{topic.users.map((u) => u.username).join(", ")}</td>
                      <td>
                        <Button
                          onClick={() =>
                            onButtonUpdateClick({
                              id: topic.id,
                              name: topic.name,
                            })
                          }
                          variant="outline-warning"
                        >
                          Đổi tên
                        </Button>
                        <Button
                          onClick={() =>
                            onButtonDeleteClick({
                              id: topic.id,
                              name: topic.name,
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
            <Modal.Title>{`Xóa chủ đề "${selectedTopic.name}"?`}</Modal.Title>
          </Modal.Header>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="danger"
              onClick={() => handleDelete(selectedTopic.id)}
            >
              Xóa
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal size="md" show={modalUpdateShow} onHide={handleModalUpdateClose}>
          <Modal.Header closeButton>
            <Modal.Title>Đổi tên chủ đề</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Label>Nhập tên mới</Form.Label>
            <Form.Control
              type="text"
              defaultValue={selectedTopic.name}
              onChange={handleNewNameChange}
            />
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
            <Modal.Title>Tạo chủ đề mới</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Label>Nhập tên</Form.Label>
            <Form.Control
              type="text"
              defaultValue={selectedTopic.name}
              onChange={handleNewNameChange}
            />
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
export default TopicManager;

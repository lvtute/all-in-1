import { useEffect, useState } from "react";
import {
  Button,
  Col,
  Container,
  Form,
  Modal,
  Row,
  Spinner,
  Table,
} from "react-bootstrap";
import { useSelector } from "react-redux";
import userService from "../../services/user.service";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../../services/constants";
import { Pagination } from "@material-ui/lab";
import topicService from "../../services/topic.service";
import ValidationMessage from "../ValidationMessage";

const UserManager = () => {
  const { user } = useSelector((state) => state.auth);
  let faculty = !!user ? user.faculty : "";

  const [isLoading, setIsLoading] = useState(false);
  const [tableData, setTableData] = useState([]);
  const [selectedUser, setSelectedUser] = useState(Object);
  const [modalConfirmDeleteShow, setModalConfirmDeleteShow] = useState(false);
  const [modalUpdateShow, setModalUpdateShow] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState(5);
  const sizes = [5, 10];

  const [trigger, triggerUseEffect] = useState(false); // this state is only used for refreshing (excecute useEffect)
  const [topicList, setTopicList] = useState([]);
  const [topicIdList, setTopicIdList] = useState([]);
  const [modalCreateShow, setModalCreateShow] = useState(false);
  const [errorResponse, setErrorResponse] = useState(Object);

  const handleClose = () => setModalConfirmDeleteShow(false);
  const handleModalUpdateClose = () => setModalUpdateShow(false);
  const handleModalCreateClose = () => setModalCreateShow(false);

  const handlePageSizeChange = (event) => {
    setSize(event.target.value);
  };
  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const onButtonDeleteClick = (user) => {
    setSelectedUser(user);
    setModalConfirmDeleteShow(true);
  };
  const handleDelete = (id) => {
    setIsSubmitting(true);
    userService
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

  const handleUpdate = () => {
    setIsSubmitting(true);
    let requestBody = {
      id: selectedUser.id,
      username: selectedUser.username,
      fullName: selectedUser.fullName,
      email: selectedUser.email,
      topicIdList,
    };
    console.log(requestBody);
    userService
      .updateByDean(requestBody)
      .then((res) => {
        setIsSubmitting(false);
        setModalUpdateShow(false);
        toast.success("Cập nhật thành công", TOASTIFY_CONFIGS);
        triggerUseEffect(!trigger);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Cập nhật không thành công", TOASTIFY_CONFIGS);
        console.log(err.response.data);
        setErrorResponse(err.response?.data);
      });
  };

  const loadTopicList = () => {
    topicService
      .getByFacultyId(faculty.id)
      .then((res) => {
        setTopicList(res.data);
      })
      .catch((err) => {
        console.log(err.res);
        toast.error(err.res, TOASTIFY_CONFIGS);
      });
  };

  const onButtonUpdateClick = (user) => {
    setErrorResponse({});
    setSelectedUser(user);
    setTopicIdList(!!user.topics ? user.topics.map((t) => t.id) : []);

    loadTopicList();
    setModalUpdateShow(true);
  };

  useEffect(() => {
    setIsLoading(true);
    userService
      .getByDean({ page: page - 1, size })
      .then((res) => {
        setTotalPages(res.data?.totalPages);
        setIsLoading(false);
        setTableData(res.data.content);
        console.log(res.data);
      })
      .catch((err) => {
        setIsLoading(false);
        console.log(err.response?.data);
      });
  }, [page, size, trigger]);

  const handleUsernameChange = (e) => {
    setSelectedUser({ ...selectedUser, username: e.target.value });
  };
  const handleEmailChange = (e) => {
    setSelectedUser({ ...selectedUser, email: e.target.value });
  };
  const handleFullNameChange = (e) => {
    setSelectedUser({ ...selectedUser, fullName: e.target.value });
  };

  const handleCheckboxChange = (e) => {
    let list = topicIdList;
    let value = parseInt(e.target.value);
    let checked = e.target.checked;
    if (checked) {
      list.push(value);
    } else {
      const index = list.indexOf(value);
      if (index > -1) {
        list.splice(index, 1);
      }
    }
    setTopicIdList(list);
  };

  const onButtonCreateClick = () => {
    setErrorResponse({});
    setSelectedUser({});
    setModalCreateShow(true);
    loadTopicList();
    setTopicIdList([]);
    
  };

  const handleCreate = () => {
    let requestBody = {
      username: selectedUser.username,
      fullName: selectedUser.fullName,
      email: selectedUser.email,
      topicIdList,
    };
    setIsSubmitting(true);
    userService
      .createByDean(requestBody)
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
      <h4>{`Quản lý Tư vấn viên- khoa ${faculty.name}`}</h4>
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
          Tạo Tư vấn viên mới
        </Button>

        <Row style={{ display: "block" }}>
          {!!tableData && (
            <>
              <Table bordered hover>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Họ tên</th>
                    <th>Tài khoản</th>
                    <th>Email</th>
                    <th>Chủ đề được giao</th>
                    <th>Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  {tableData.map((user) => (
                    <tr key={user.id}>
                      <td>{user.id}</td>
                      <td>{user.fullName}</td>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td>{user.topics.map((t) => t.name).join(", ")}</td>
                      <td>
                        <Button
                          onClick={() => onButtonUpdateClick(user)}
                          variant="outline-warning"
                        >
                          Sửa
                        </Button>
                        <Button
                          onClick={() =>
                            onButtonDeleteClick({
                              id: user.id,
                              username: user.username,
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
        <Row style={{ float: "right" }} className="mt-3">
          {"size: "}
          <select onChange={handlePageSizeChange} value={size}>
            {sizes.map((size) => (
              <option key={size} value={size}>
                {size}
              </option>
            ))}
          </select>
          <Pagination
            className="my-3"
            count={totalPages}
            page={page}
            variant="outlined"
            shape="rounded"
            onChange={handlePageChange}
          />
        </Row>

        <Modal size="md" show={modalConfirmDeleteShow} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>{`Xóa tư vấn viên "${selectedUser.username}"?`}</Modal.Title>
          </Modal.Header>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="danger"
              onClick={() => handleDelete(selectedUser.id)}
            >
              Xóa
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal size="md" show={modalUpdateShow} onHide={handleModalUpdateClose}>
          <Modal.Header closeButton>
            <Modal.Title>Cập nhật thông tin tư vấn viên</Modal.Title>
          </Modal.Header>

          <Modal.Body>
            <Form>
              <Form.Row>
                <Form.Group as={Col}>
                  <Form.Label>Username</Form.Label>
                  <Form.Control
                    type="text"
                    name="username"
                    defaultValue={selectedUser.username}
                    onChange={handleUsernameChange}
                  />
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="username"
                  ></ValidationMessage>
                </Form.Group>

                <Form.Group as={Col}>
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    defaultValue={selectedUser.email}
                    onChange={handleEmailChange}
                  />
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="email"
                  ></ValidationMessage>
                </Form.Group>
              </Form.Row>

              <Form.Group>
                <Form.Label>Họ và tên</Form.Label>
                <Form.Control
                  type="text"
                  name="fullName"
                  defaultValue={selectedUser.fullName}
                  onChange={handleFullNameChange}
                />
                <ValidationMessage
                  errorResponse={errorResponse}
                  field="fullName"
                ></ValidationMessage>
              </Form.Group>

              <Form.Group>
                <Form.Label>Chọn chủ đề</Form.Label>
                {topicList.map((topic) => (
                  <Form.Check
                    type="checkbox"
                    label={`${topic.name}`}
                    key={`${topic.id}`}
                    value={`${topic.id}`}
                    onChange={handleCheckboxChange}
                    defaultChecked={
                      !!selectedUser.topics &&
                      selectedUser.topics.map((t) => t.id).includes(topic.id)
                    }
                  />
                ))}
              </Form.Group>
              <ValidationMessage
                errorResponse={errorResponse}
                field="error"
              ></ValidationMessage>
            </Form>
          </Modal.Body>

          <Modal.Footer>
            <Button
              disabled={isSubmitting}
              variant="warning"
              onClick={handleUpdate}
            >
              Cập nhật
            </Button>
          </Modal.Footer>
        </Modal>

        <Modal size="md" show={modalCreateShow} onHide={handleModalCreateClose}>
          <Modal.Header closeButton>
            <Modal.Title>Tạo tư vấn viên</Modal.Title>
          </Modal.Header>

          <Modal.Body>
            <Form>
              <Form.Row>
                <Form.Group as={Col}>
                  <Form.Label>Username</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Username"
                    onChange={handleUsernameChange}
                  />
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="username"
                  ></ValidationMessage>
                </Form.Group>

                <Form.Group as={Col}>
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    placeholder="Email"
                    onChange={handleEmailChange}
                  />
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="email"
                  ></ValidationMessage>
                </Form.Group>
              </Form.Row>

              <Form.Group>
                <Form.Label>Họ và tên</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Họ và tên"
                  onChange={handleFullNameChange}
                />
                <ValidationMessage
                  errorResponse={errorResponse}
                  field="fullName"
                ></ValidationMessage>
              </Form.Group>

              <Form.Group>
                <Form.Label>Chọn chủ đề</Form.Label>
                {topicList.map((topic) => (
                  <Form.Check
                    type="checkbox"
                    label={`${topic.name}`}
                    key={`${topic.id}`}
                    value={`${topic.id}`}
                    onChange={handleCheckboxChange}
                  />
                ))}
              </Form.Group>
              <ValidationMessage
                errorResponse={errorResponse}
                field="error"
              ></ValidationMessage>
            </Form>
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

export default UserManager;

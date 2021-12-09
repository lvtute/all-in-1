import { Button, ListGroup, Modal } from "react-bootstrap";
import { useEffect } from "react";
import facultyService from "../services/faculty.service";
import { useState } from "react";
import { Container, Row, Col, Spinner, Card } from "react-bootstrap";
import questionService from "../services/question.service";
import Pagination from "@material-ui/lab/Pagination";
import createMarkup from "../common/createMarkup";
const Home = () => {
  const [facultyList, setFacultyList] = useState([]);
  const [isFacultyLoading, setFacultyLoadingStatus] = useState(true);

  const [questionList, setQuestionList] = useState([]);
  const [isQuestionLoading, setQuestionLoadingStatus] = useState(true);

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState(5);
  const [facultyId, setFacultyId] = useState(0);
  const sizes = [5, 10];

  // modal's
  const [show, setShow] = useState(false);
  const [questionDetail, setQuestionDetail] = useState(Object);

  const handleClose = () => {
    setShow(false);
    setQuestionDetail(Object);
  };
  const handleShow = () => setShow(true);

  const handleOnQuestionClick = (id) => {
    handleShow();
    questionService.getById(id).then((res) => {
      setQuestionDetail(res.data);
      console.log(res.data);
    });
  };

  // const createMarkup = (richText) => {
  //   return { __html: DOMPurify.sanitize(richText) };
  // };

  useEffect(() => {
    facultyService
      .getAll()
      .then((res) => {
        setFacultyList(res.data);
        setFacultyLoadingStatus(false);
      })
      .catch((err) => {
        console.log(err);
        setFacultyLoadingStatus(false);
      });
  }, []);

  useEffect(() => {
    questionService
      .getAll({ page: page - 1, size, facultyId })
      .then((res) => {
        console.log(res.data);
        setTotalPages(res.data?.totalPages);
        setQuestionList(res.data?.content);
        setQuestionLoadingStatus(false);
      })
      .catch((err) => {
        console.log(err);
        setQuestionLoadingStatus(false);
      });
  }, [page, size, facultyId]);

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const handlePageSizeChange = (event) => {
    setSize(event.target.value);
  };

  const reloadQuestions = (event) => {
    setFacultyId(event.target.value);
    setPage(1);
    setSize(5);
  };
  return (
    <Container fluid>
      <Row>
        <Col md={2}>
          <ListGroup>
            <ListGroup.Item
              onClick={reloadQuestions}
              value="0"
              action
              variant="light"
            >
              Tất cả phòng ban
            </ListGroup.Item>
            {isFacultyLoading && (
              <Spinner
                style={{ margin: "0 auto" }}
                animation="border"
                variant="primary"
              />
            )}
            {facultyList.map((faculty) => (
              <ListGroup.Item
                value={faculty.id}
                onClick={reloadQuestions}
                key={faculty.id}
                action
                variant="light"
              >
                {faculty.name}
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>

        <Col md={8}>
          {isQuestionLoading && (
            <Row style={{ display: "flex", height: "80%" }}>
              <Spinner
                style={{ margin: "auto" }}
                animation="border"
                variant="primary"
              />
            </Row>
          )}

          {questionList.map((question) => (
            <Card
              onClick={() => handleOnQuestionClick(question.id)}
              key={question.id}
              style={{ padding: "1% 2%" }}
            >
              <Card.Body style={{ padding: "0.1%" }}>
                <Card.Title>{question.title}</Card.Title>

                <Card.Subtitle
                  className="question-subtitle"
                  style={{ float: "right" }}
                >
                  {`${question.views} lượt xem`}
                </Card.Subtitle>

                <Card.Subtitle className="question-subtitle">
                  đăng bởi: <span>{question.name}</span>- vào lúc{" "}
                  <span>{question.createdDate}</span>
                </Card.Subtitle>

                <Card.Subtitle
                  className="question-subtitle"
                  style={{ float: "right", display: "inline-block" }}
                >
                  {!!question.answer ? (
                    <p style={{ color: "green", fontWeight: "bold" }}>
                      Đã trả lời
                    </p>
                  ) : (
                    <p style={{ color: "orange" }}>Chờ trả lời</p>
                  )}
                </Card.Subtitle>

                <Card.Subtitle className="question-subtitle">
                  khoa <span>{question.facultyName}</span>- mục{" "}
                  <span>{question.topicName}</span>
                </Card.Subtitle>
              </Card.Body>
            </Card>
          ))}
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
        </Col>
      </Row>

      <Modal
        show={show}
        onHide={handleClose}
        size="lg"
      >
        <Modal.Header>
          <Container>
            <Row>
              <p
                style={{
                  textAlign: "right",
                  width: "100%",
                  color: "#6c757d",
                  fontStyle: "italic",
                }}
              >{`bởi: ${questionDetail.name} | vào lúc: ${questionDetail.createdDate}`}</p>
              <h2 style={{ color: "#005cb2", width: "100%" }}>
                {questionDetail.title}
              </h2>
              <p
                style={{ marginBottom: "0", color: "#6c757d" }}
              >{`Khoa ${questionDetail.facultyName} | ${questionDetail.topicName}`}</p>
            </Row>
          </Container>
        </Modal.Header>
        <Modal.Body>
          <Container>
            <Row>
              <div
                dangerouslySetInnerHTML={createMarkup(questionDetail.content)}
              ></div>
            </Row>
            <hr></hr>
            <Row>
              {!!questionDetail.answer ? (
                <div
                  style={{
                    width: "100%",
                    textAlign: "right",
                  }}
                  dangerouslySetInnerHTML={createMarkup(questionDetail.answer)}
                ></div>
              ) : (
                <p
                  style={{
                    width: "100%",
                    textAlign: "right",
                    color: "#f57c00",
                  }}
                >
                  <i className="bi bi-check-circle"></i>
                  Đang chờ tư vấn viên trả lời...
                </p>
              )}
              {questionDetail.adviserFullName &&
                questionDetail.lastModifiedDate &&
                questionDetail.answer && (
                  <p
                    style={{
                      textAlign: "right",
                      width: "100%",
                      color: "#6c757d",
                      fontStyle: "italic",
                    }}
                  >{`Tư vấn viên: ${questionDetail.adviserFullName}- ${questionDetail.lastModifiedDate}`}</p>
                )}
              {questionDetail.approvedByDean && (
                <p
                  style={{
                    textAlign: "right",
                    width: "100%",
                    color: "#00b0ff",
                    fontStyle: "italic",
                  }}
                >
                  Câu trả lời này đã được xác nhận bởi trưởng khoa
                </p>
              )}
            </Row>
          </Container>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Đóng
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default Home;

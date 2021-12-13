import { Card, Container, Form, Row, Spinner } from "react-bootstrap";
import { useState, useEffect } from "react";
import questionService from "../../services/question.service";
import Pagination from "@material-ui/lab/Pagination";
import ValidationMessage from "../ValidationMessage";
import { QUESTION_REPLIER } from "../../services/constants";
import history from "../../history";
import { useSelector } from "react-redux";

const QuestionManager = () => {
  const [questionList, setQuestionList] = useState([]);
  const [isQuestionLoading, setQuestionLoadingStatus] = useState(true);
  const [errorMessage, setErrorMessage] = useState(Object);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState(5);
  const [noAnswerOnly, setNoAnswerOnly] = useState(false);

  const sizes = [5, 10];

  const { user } = useSelector((state) => state.auth);
  let faculty = !!user ? user.faculty : "";

  useEffect(() => {
    let isMounted = true;
    questionService
      .getByDean({ page: page - 1, size, noAnswerOnly })
      .then((res) => {
        if (isMounted) {
          setTotalPages(res.data?.totalPages);
          setQuestionList(res.data?.content);
          setQuestionLoadingStatus(false);
          setErrorMessage(Object);
        }
      })
      .catch((err) => {
        if (isMounted) {
          setQuestionLoadingStatus(false);
          setErrorMessage(err.response?.data);
        }
      });
    return () => {
      isMounted = false;
    };
  }, [page, size, noAnswerOnly]);

  const handlePageChange = (event, value) => {
    // if (isMounted) {
    setPage(value);
    // }
  };

  const handlePageSizeChange = (event) => {
    // if (isMounted) {
    setSize(event.target.value);
    // }
  };

  const handleOnQuestionClick = (id) => {
    history.push(`${QUESTION_REPLIER}/${id}`);
  };

  const handleAnswerOnlyCheckboxChange = (e) => {
    setNoAnswerOnly(e.target.checked);
  };

  return (
    <>
      <h4>{`Câu hỏi trong khoa ${faculty.name}`}</h4>

      <Container>
        {isQuestionLoading && (
          <Row style={{ display: "flex", height: "80%" }}>
            <Spinner
              style={{ margin: "auto" }}
              animation="border"
              variant="primary"
            />
          </Row>
        )}
        {!!errorMessage && (
          <ValidationMessage errorResponse={errorMessage} field="error" />
        )}

        <Row>
          <Form.Check
            disabled={isQuestionLoading}
            type="checkbox"
            label="Chỉ hiện câu chưa trả lời"
            onChange={handleAnswerOnlyCheckboxChange}
          />
          {questionList.map((question) => (
            <Card
              key={question.id}
              style={{
                width: "100%",
                padding: "0",
              }}
            >
              <Card.Body
                style={{ padding: "0" }}
                onClick={() => handleOnQuestionClick(question.id)}
              >
                <Card.Title
                  style={{
                    cursor: "pointer",
                    color: !!question.answer ? "#4caf50" : "#c56000",
                  }}
                >
                  {question.title}
                </Card.Title>
              </Card.Body>
            </Card>
          ))}
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
      </Container>
    </>
  );
};
export default QuestionManager;

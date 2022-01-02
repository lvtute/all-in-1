import {
  Button,
  Card,
  Col,
  Container,
  Form,
  FormControl,
  InputGroup,
  Row,
  Spinner,
} from "react-bootstrap";
import { useState, useEffect } from "react";
import questionService from "../../services/question.service";
import Pagination from "@material-ui/lab/Pagination";
import ValidationMessage from "../ValidationMessage";
import { QUESTION_REPLIER } from "../../services/constants";
import history from "../../history";
import { useSelector } from "react-redux";

import { makeStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Tabs from "@material-ui/core/Tabs";
import Tab from "@material-ui/core/Tab";

import ContactSupportOutlinedIcon from "@material-ui/icons/ContactSupportOutlined";
import CheckCircleOutlineRoundedIcon from "@material-ui/icons/CheckCircleOutlineRounded";
import HourglassEmptyOutlinedIcon from "@material-ui/icons/HourglassEmptyOutlined";

const QuestionManager = () => {
  const [questionList, setQuestionList] = useState([]);
  const [isQuestionLoading, setQuestionLoadingStatus] = useState(true);
  const [errorMessage, setErrorMessage] = useState(Object);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState(5);
  const [noAnswerOnly, setNoAnswerOnly] = useState(false);
  const [passedToDean, setPassedToDean] = useState(true);

  const [searchString, setSearchString] = useState("");
  const [searchStringState, setSearchStringState] = useState("");

  const sizes = [5, 10];

  const { user } = useSelector((state) => state.auth);
  let faculty = !!user ? user.faculty : "";

  useEffect(() => {
    let isMounted = true;
    questionService
      .getByDean({
        page: page - 1,
        size,
        noAnswerOnly,
        passedToDean,
        searchString: searchStringState,
      })
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
  }, [page, size, noAnswerOnly, passedToDean, searchStringState]);

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

  // tabs
  const [tabValue, setTabValue] = useState(0);

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
    setSearchString("");
    setPassedToDean(newValue === 0);
    setNoAnswerOnly(newValue === 1);
  };

  const handleSearchStringChange = (e) => {
    setSearchString(e.target.value);
    if (e.target.value === "") setSearchStringState("");
  };
  const handleSeachButtonClick = () => {
    setSearchStringState(searchString);
    setPage(1);
    setSize(5);
  };

  return (
    <>
      <h4>{`Câu hỏi trong khoa ${faculty.name}`}</h4>

      <Container>
        <Row>
          <Form.Row style={{ marginLeft: "auto", float: "right" }}>
            <Col xs="auto">
              <InputGroup className="mb-2">
                <FormControl
                  defaultValue={searchString}
                  onChange={handleSearchStringChange}
                  placeholder="Nhập từ khóa"
                />
              </InputGroup>
            </Col>
            <Col xs="auto">
              <Button onClick={handleSeachButtonClick} variant="secondary">
                Tìm
              </Button>
            </Col>
          </Form.Row>
        </Row>
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
          <CenteredTabs
            handleTabChange={handleTabChange}
            tabValue={tabValue}
          ></CenteredTabs>
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

const useStyles = makeStyles({
  root: {
    flexGrow: 1,
  },
});

const CenteredTabs = ({ handleTabChange, tabValue }) => {
  const classes = useStyles();

  const myTabStyle = { fontSize: "1.1em" };
  return (
    <Paper className={classes.root}>
      <Tabs
        value={tabValue}
        onChange={handleTabChange}
        indicatorColor="primary"
        textColor="primary"
        centered
        variant="fullWidth"
      >
        <Tab
          style={{ ...myTabStyle, color: "#0288d1" }}
          label="Chờ xác nhận"
          icon={<HourglassEmptyOutlinedIcon />}
        />
        <Tab
          style={{ ...myTabStyle, color: "#ffa000" }}
          label="Chưa trả lời"
          icon={<ContactSupportOutlinedIcon />}
        />
        <Tab
          style={{ ...myTabStyle, color: "#80cbc4" }}
          label="Đã trả lời"
          icon={<CheckCircleOutlineRoundedIcon />}
        />
        CheckCircleOutlineRoundedIcon
      </Tabs>
    </Paper>
  );
};

export default QuestionManager;

import { Container, Modal, Row, Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Page404 from "./Page404";
import { useState, useEffect } from "react";
import questionService from "../services/question.service";
import createMarkup from "../common/createMarkup";
import CenteredTitle from "../components/CenteredTitle";
import { Form, Button } from "react-bootstrap";
import { EditorState, ContentState } from "draft-js";
import { Editor } from "react-draft-wysiwyg";
import { stateToHTML } from "draft-js-export-html";
import { toast } from "react-toastify";
import {
  ADVISER_PATH,
  DEAN_PATH,
  ROLE_ADVISER,
  TOASTIFY_CONFIGS,
} from "../services/constants";
import history from "../history";
import DraftPasteProcessor from "draft-js/lib/DraftPasteProcessor";
import { useSelector } from "react-redux";

import DeleteIcon from "@material-ui/icons/Delete";
import SaveIcon from "@material-ui/icons/Save";
import FastForwardIcon from "@material-ui/icons/FastForward";
import facultyService from "../services/faculty.service";
import topicService from "../services/topic.service";
import ValidationMessage from "../components/ValidationMessage";

const QuestionReplier = () => {
  const { user } = useSelector((state) => state.auth);
  let role = "";
  if (!!user) {
    role = user.role;
  }
  let dashboard = role === ROLE_ADVISER ? ADVISER_PATH : DEAN_PATH;

  let { id } = useParams();

  const [questionDetail, setQuestionDetail] = useState(Object);

  const [isSubmitting, setIsSubmitting] = useState(false);

  const [modalShow, setModalShow] = useState(false); // deleting modal

  const [transferModalShow, setTransferModalShow] = useState(false);

  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );

  const onEditorStateChange = (editorState) => {
    setEditorState(editorState);
  };

  const [errorResponse, setErrorResponse] = useState(Object);
  const [facultyList, setFacultyList] = useState([]);
  const [topicList, setTopicList] = useState([]);

  const [isPrivate, setIsPrivate] = useState(false);

  useEffect(() => {
    if (isNaN(id)) {
      return <Page404 />;
    }
    questionService
      .getByIdIncludingPrivate(id)
      .then((res) => {
        console.log(res.data.private);
        setIsPrivate(res.data.private);
        setQuestionDetail(res.data);
        const processedHTML = DraftPasteProcessor.processHTML(
          !!res.data.answer ? res.data.answer : "<p></p>"
        );
        const contentState = ContentState.createFromBlockArray(processedHTML);
        let myState = EditorState.createWithContent(contentState);
        let myState2 = EditorState.moveFocusToEnd(myState);
        // setEditorState(myState);
        setEditorState(myState2);
      })
      .catch((err) => {
        console.log(err.response?.data);
      });

    facultyService
      .getAll()
      .then((res) => {
        setFacultyList(res.data);
      })
      .catch((err) => {
        console.log(err.res?.data);
      });
  }, [id]);

  const handleSubmit = (event) => {
    event.preventDefault();
    setIsSubmitting(true);
    const formData = Object.fromEntries(new FormData(event.target).entries());
    let data = {
      questionId: id,
      consultDean: formData.consultDean === "on",
      answer: stateToHTML(editorState.getCurrentContent()),
      isPrivate: formData.isPrivate === "on",
    };
    console.log(data);
    questionService
      .saveAnswer(data)
      .then((res) => {
        console.log(res);
        toast.success("Lưu câu trả lời thành công!", TOASTIFY_CONFIGS);
        history.push(`${dashboard}/question`);
        // setIsSubmitting(false);
      })
      .catch((err) => {
        setIsSubmitting(false);
        toast.error("Lưu câu trả lời thất bại!", TOASTIFY_CONFIGS);
      });
  };
  const handleModalShow = () => {
    setModalShow(true);
  };

  const handleClose = () => setModalShow(false);

  const handleDelete = () => {
    setModalShow(false);
    questionService
      .deleteQuestion(id)
      .then((res) => {
        toast.success("Xóa thành công", TOASTIFY_CONFIGS);

        history.push(`${dashboard}/question`);
      })
      .catch((err) => {
        toast.error("Xóa thất bại", TOASTIFY_CONFIGS);
        console.log(err.response);
      });
  };

  const handleTransferModalShow = () => {
    setTransferModalShow(true);
  };

  const handleTransferModalClose = () => {
    setTransferModalShow(false);
  };

  const handleTransfer = (event) => {
    event.preventDefault();
    const formData = Object.fromEntries(new FormData(event.target).entries());
    let requestBody = {
      questionId: id,
      facultyId: isNaN(formData.facultyId) ? "" : formData.facultyId,
      topicId: isNaN(formData.topicId) ? "" : formData.topicId,
    };

    questionService
      .transferQuestion(requestBody)
      .then((res) => {
        toast.success("Chuyển câu hỏi thành công", TOASTIFY_CONFIGS);
        setTransferModalShow(false);
        history.push(`${dashboard}/question`);
      })
      .catch((err) => {
        toast.error("Xảy ra lỗi", TOASTIFY_CONFIGS);
        console.log(err.response?.data);
        setErrorResponse(err.response?.data);
      });
  };

  const onFacultySelectorChange = (event) => {
    if (isNaN(event.target.value)) {
      setTopicList([]);
    } else {
      topicService
        .getByFacultyId(event.target.value)
        .then((res) => {
          setTopicList(res.data);
        })
        .catch((err) => {
          setTopicList([]);
          console.log(err);
        });
    }
  };

  return (
    <>
      <CenteredTitle title="TRẢ LỜI CÂU HỎI" />
      <div className="centered-form">
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

          <Button
            style={{ marginRight: "3%" }}
            variant="outline-danger"
            onClick={handleModalShow}
          >
            <DeleteIcon></DeleteIcon>Xóa câu hỏi này
          </Button>
          <Button variant="outline-success" onClick={handleTransferModalShow}>
            <FastForwardIcon></FastForwardIcon>Chuyển câu hỏi cho mục khác
          </Button>

          <Modal size="sm" show={modalShow} centered onHide={handleClose}>
            <Modal.Header closeButton>
              <Modal.Title>Xóa câu hỏi?</Modal.Title>
            </Modal.Header>

            <Modal.Footer>
              <Button variant="danger" onClick={handleDelete}>
                Xóa
              </Button>
            </Modal.Footer>
          </Modal>

          <Modal
            size="md"
            show={transferModalShow}
            centered
            onHide={handleTransferModalClose}
          >
            <Modal.Header closeButton>
              <Modal.Title>Chuyển câu hỏi</Modal.Title>
            </Modal.Header>

            <Modal.Body>
              <Form id="question-transfer-form" onSubmit={handleTransfer}>
                <Form.Group controlId="exampleForm.SelectCustom">
                  <Form.Label>Chọn khoa</Form.Label>
                  <Form.Control
                    as="select"
                    custom
                    name="facultyId"
                    onChange={(e) => {
                      onFacultySelectorChange(e);
                    }}
                  >
                    <option>Chọn khoa</option>
                    {facultyList.map((f) => {
                      return (
                        <option key={f.id} value={f.id}>
                          {f.name}
                        </option>
                      );
                    })}
                  </Form.Control>
                </Form.Group>
                <Form.Group controlId="exampleForm.SelectCustom">
                  <Form.Label>Chọn chủ đề</Form.Label>
                  <Form.Control as="select" custom name="topicId">
                    <option>Chọn chủ đề</option>
                    {topicList.map((t) => {
                      return (
                        <option key={t.id} value={t.id}>
                          {t.name}
                        </option>
                      );
                    })}
                  </Form.Control>
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="topicId"
                  />
                  <ValidationMessage
                    errorResponse={errorResponse}
                    field="error"
                  />
                </Form.Group>
              </Form>
            </Modal.Body>

            <Modal.Footer>
              <Button
                variant="success"
                type="submit"
                form="question-transfer-form"
              >
                <span>
                  Chuyển <FastForwardIcon></FastForwardIcon>
                </span>
              </Button>
            </Modal.Footer>
          </Modal>
        </Container>
      </div>

      <Form className="centered-form" onSubmit={handleSubmit}>
        <Form.Group controlId="formCreateQuestion">
          <Form.Row>
            <Form.Label>Trả lời</Form.Label>
            <Editor
              editorState={editorState}
              onEditorStateChange={onEditorStateChange}
              wrapperClassName="wrapper-class"
              editorClassName="editor-class"
              toolbarClassName="toolbar-class"
            />
          </Form.Row>
          {role === ROLE_ADVISER && (
            <>
              <Form.Check
                name="consultDean"
                type="checkbox"
                label="Xin xác nhận từ Trưởng ban Tư vấn"
              />
              <Form.Check
                defaultChecked= {isPrivate}
                name="isPrivate"
                type="checkbox"
                label="Đánh dấu câu hỏi là RIÊNG TƯ"
              />
            </>
          )}
        </Form.Group>
        <Button disabled={isSubmitting} variant="primary" type="submit">
          {isSubmitting && (
            <Spinner
              as="span"
              animation="border"
              size="sm"
              role="status"
              aria-hidden="true"
            />
          )}
          <SaveIcon></SaveIcon>Lưu
        </Button>
      </Form>
    </>
  );
};
export default QuestionReplier;

import { Container, Modal, Row } from "react-bootstrap";
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

  const [modalShow, setModalShow] = useState(false);

  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );

  const onEditorStateChange = (editorState) => {
    setEditorState(editorState);
  };

  useEffect(() => {
    if (isNaN(id)) {
      return <Page404 />;
    }
    questionService
      .getById(id)
      .then((res) => {
        setQuestionDetail(res.data);
        const processedHTML = DraftPasteProcessor.processHTML(
          !!res.data.answer ? res.data.answer : "<p></p>"
        );
        const contentState = ContentState.createFromBlockArray(processedHTML);
        let myState = EditorState.createWithContent(contentState);
        let myState2 = EditorState.moveFocusToEnd(myState);
        setEditorState(myState);
        setEditorState(myState2);
      })
      .catch((err) => {
        console.log(err.response?.data);
      });
  }, [id]);

  const handleSubmit = (event) => {
    event.preventDefault();
    setIsSubmitting(true);
    const formData = Object.fromEntries(new FormData(event.target).entries());
    let data = {
      questionId: id,
      consultDean: formData.consultDean === "on" ? "true" : false,
      answer: stateToHTML(editorState.getCurrentContent()),
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
          <Button variant="outline-danger" onClick={handleModalShow}>
            Xóa câu hỏi này
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
            <Form.Check
              name="consultDean"
              type="checkbox"
              label="Gửi mail cho Trưởng khoa để xác nhận câu trả lời"
            />
          )}
        </Form.Group>
        <Button disabled={isSubmitting} variant="primary" type="submit">
          Lưu
        </Button>
      </Form>
    </>
  );
};
export default QuestionReplier;

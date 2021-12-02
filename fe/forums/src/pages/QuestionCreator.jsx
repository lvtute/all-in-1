import { Button, Col, Form } from "react-bootstrap";
import CenteredTitle from "../components/CenteredTitle";
import facultyService from "../services/faculty.service";
import topicService from "../services/topic.service";
import questionService from "../services/question.service";
import { useEffect, useState } from "react";

import { EditorState } from "draft-js";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { convertToHTML } from "draft-convert";
import ValidationMessage from "../components/ValidationMessage";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../services/constants";

const QuestionCreator = () => {
  const [faculties, setFaculties] = useState([]);
  const [topics, setTopics] = useState([]);
  const [errorResponse, setErrorResponse] = useState(Object);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const [editorState, setEditorState] = useState(() =>
    EditorState.createEmpty()
  );

  const onEditorStateChange = (editorState) => {
    setEditorState(editorState);
  };

  useEffect(() => {
    facultyService
      .getAll()
      .then((res) => {
        setFaculties(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const onFacultySelectorChange = (event) => {
    if (isNaN(event.target.value)) {
      setTopics([]);
    } else {
      topicService
        .getById(event.target.value)
        .then((res) => {
          setTopics(res.data);
        })
        .catch((err) => {
          setTopics([]);
          console.log(err);
        });
    }
  };
  const handleSubmit = (event) => {
    setIsSubmitting(true);
    event.preventDefault();

    const formData = Object.fromEntries(new FormData(event.target).entries());
    let requestBody = {
      ...formData,
      content: convertToHTML(editorState.getCurrentContent()),
      agreeToReceiveEmailNotification:
        !!formData.agreeToReceiveEmailNotification ? "true" : "false",
      facultyId: isNaN(formData.facultyId) ? "" : formData.facultyId,
      topicId: isNaN(formData.topicId) ? "" : formData.topicId,
    };
    questionService
      .createQuestion(requestBody)
      .then((res) => {
        console.log(res.data);
        setErrorResponse({});
        toast.success("Đặt câu hỏi thành công!", TOASTIFY_CONFIGS);
      })
      .catch((err) => {
        console.log(err.response?.data);
        setErrorResponse(err.response?.data);
        setIsSubmitting(false);
      });
  };

  return (
    <>
      <CenteredTitle title="ĐẶT CÂU HỎI" />
      <Form className="centered-form" onSubmit={handleSubmit}>
        <Form.Group controlId="formCreateQuestion">
          <Form.Row>
            <Col>
              <Form.Label>Khoa</Form.Label>
              <Form.Control
                as="select"
                onChange={(e) => {
                  onFacultySelectorChange(e);
                }}
                name="facultyId"
                custom
              >
                <option>Vui lòng chọn khoa</option>
                {faculties.map((faculty) => {
                  return (
                    <option key={faculty.id} value={faculty.id}>
                      {faculty.name}
                    </option>
                  );
                })}
              </Form.Control>
              <ValidationMessage
                errorResponse={errorResponse}
                field="facultyId"
              />
            </Col>
            <Col>
              <Form.Label>Chủ đề (Chọn khoa trước khi chọn chủ đề)</Form.Label>
              <Form.Control as="select" name="topicId" custom>
                <option>Vui lòng chọn chủ đề</option>
                {topics.map((topic) => {
                  return (
                    <option key={topic.id} value={topic.id}>
                      {topic.name}
                    </option>
                  );
                })}
              </Form.Control>
              <ValidationMessage
                errorResponse={errorResponse}
                field="topicId"
              />
            </Col>
          </Form.Row>
          <br />
          <Form.Row>
            <Col>
              <Form.Label>Tên của bạn</Form.Label>
              <Form.Control name="name" placeholder="Tên của bạn" />
              <ValidationMessage errorResponse={errorResponse} field="name" />
            </Col>
            <Col>
              <Form.Label>Email</Form.Label>
              <Form.Control name="email" placeholder="Email" />
              <ValidationMessage errorResponse={errorResponse} field="email" />
            </Col>
          </Form.Row>
          <br />
          <Form.Row>
            <Form.Label>Tiêu đề câu hỏi</Form.Label>
            <Form.Control name="title" placeholder="Tiêu đề câu hỏi" />
            <ValidationMessage errorResponse={errorResponse} field="title" />
          </Form.Row>
          <br />
          <Form.Row>
            <Form.Label>Nội dung chi tiết câu hỏi</Form.Label>
            <Editor
              editorState={editorState}
              onEditorStateChange={onEditorStateChange}
              wrapperClassName="wrapper-class"
              editorClassName="editor-class"
              toolbarClassName="toolbar-class"
            />
            {/* <ValidationMessage errorResponse={errorResponse} field="content" /> */}
          </Form.Row>

          <Form.Check
            name="agreeToReceiveEmailNotification"
            type="checkbox"
            label="Đăng ký nhận email thông báo"
          />
        </Form.Group>
        <ValidationMessage errorResponse={errorResponse} field="error" />
        <Button disabled={isSubmitting} variant="primary" type="submit">
          Submit
        </Button>
      </Form>
    </>
  );
};
export default QuestionCreator;

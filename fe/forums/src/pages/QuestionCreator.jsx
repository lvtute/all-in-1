import { Button, Col, Form, Spinner } from "react-bootstrap";
import CenteredTitle from "../components/CenteredTitle";
import facultyService from "../services/faculty.service";
import topicService from "../services/topic.service";
import questionService from "../services/question.service";
import { useEffect, useState } from "react";

import { EditorState } from "draft-js";
import { Editor } from "react-draft-wysiwyg";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import ValidationMessage from "../components/ValidationMessage";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../services/constants";
import { useHistory } from "react-router-dom";

import { stateToHTML } from "draft-js-export-html";
// import { HOME_PATH } from "../services/constants";

const QuestionCreator = () => {
  const [faculties, setFaculties] = useState([]);
  const [topics, setTopics] = useState([]);
  const [errorResponse, setErrorResponse] = useState(Object);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const history = useHistory();

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
        .getByFacultyId(event.target.value)
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
      content: stateToHTML(editorState.getCurrentContent()),
      // agreeToReceiveEmailNotification:
      //   !!formData.agreeToReceiveEmailNotification ? "true" : "false",
      facultyId: isNaN(formData.facultyId) ? "" : formData.facultyId,
      topicId: isNaN(formData.topicId) ? "" : formData.topicId,
    };
    questionService
      .createQuestion(requestBody)
      .then((res) => {
        history.push("/home"); // HOME_PATH
        toast.success("?????t c??u h???i th??nh c??ng!", TOASTIFY_CONFIGS);
      })
      .catch((err) => {
        console.log(err.response?.data);
        setErrorResponse(err.response?.data);
        setIsSubmitting(false);
      });
  };

  return (
    <>
      <CenteredTitle title="?????T C??U H???I" />
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
                <option>Vui l??ng ch???n khoa</option>
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
              <Form.Label>Ch??? ????? (Ch???n khoa tr?????c khi ch???n ch??? ?????)</Form.Label>
              <Form.Control as="select" name="topicId" custom>
                <option>Vui l??ng ch???n ch??? ?????</option>
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
              <Form.Label>T??n c???a b???n</Form.Label>
              <Form.Control name="name" placeholder="T??n c???a b???n" />
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
            <Form.Label>Ti??u ????? c??u h???i</Form.Label>
            <Form.Control name="title" placeholder="Ti??u ????? c??u h???i" />
            <ValidationMessage errorResponse={errorResponse} field="title" />
          </Form.Row>
          <br />
          <Form.Row>
            <Form.Label>N???i dung chi ti???t c??u h???i</Form.Label>
            <Editor
              editorState={editorState}
              onEditorStateChange={onEditorStateChange}
              wrapperClassName="wrapper-class"
              editorClassName="editor-class"
              toolbarClassName="toolbar-class"
            />
          </Form.Row>

          {/* <Form.Check
            name="agreeToReceiveEmailNotification"
            type="checkbox"
            label="????ng k?? nh???n email th??ng b??o"
          /> */}
        </Form.Group>
        <ValidationMessage errorResponse={errorResponse} field="error" />
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
          Submit
        </Button>
      </Form>
    </>
  );
};
export default QuestionCreator;

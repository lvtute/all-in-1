import { Form, Button } from "react-bootstrap";
import CenteredTitle from "./CenteredTitle";
import { useState } from "react";
import ValidationMessage from "./ValidationMessage";
import authenService from "../services/auth.service";
import { toast } from "react-toastify";
import { TOASTIFY_CONFIGS } from "../services/constants";

const PasswordChanger = () => {
  const [loading, setLoading] = useState(false);
  const [errorResponse, setErrorResponse] = useState(Object);

  const handleSubmit = (e) => {
    e.preventDefault();

    const formData = Object.fromEntries(new FormData(e.target).entries());
    if (formData.newPassword !== formData.confirmNewPassword) {
      setErrorResponse({ confirmNewPassword: "Mật khẩu không khớp" });
    } else {
      authenService
        .changePassword(formData)
        .then((res) => {
          setLoading(false);
          toast.success("Đổi mật khẩu thành công!", TOASTIFY_CONFIGS);
          setErrorResponse({});
        })
        .catch((err) => {
          console.log(err);
          setLoading(false);
          setErrorResponse(err.response?.data);
        });
    }
  };

  return (
    <>
      <CenteredTitle title="Đổi mật khẩu" />
      <Form className="centered-form" onSubmit={handleSubmit}>
        <Form.Group>
          <Form.Label>Mật khẩu cũ</Form.Label>
          <Form.Control
            type="password"
            placeholder="Mật khẩu hiện tại"
            name="oldPassword"
          />
          <ValidationMessage
            errorResponse={errorResponse}
            field="oldPassword"
          ></ValidationMessage>

          <Form.Label>Mật khẩu mới</Form.Label>
          <Form.Control
            type="password"
            placeholder="Mật khẩu mới"
            name="newPassword"
          />
          <ValidationMessage
            errorResponse={errorResponse}
            field="newPassword"
          ></ValidationMessage>

          <Form.Label>Xác nhận mật khẩu mới</Form.Label>
          <Form.Control
            type="password"
            placeholder="Xác nhận mật khẩu mới"
            name="confirmNewPassword"
          />
          <ValidationMessage
            errorResponse={errorResponse}
            field="confirmNewPassword"
          ></ValidationMessage>
        </Form.Group>

        <Button variant="primary" type="submit" disabled={loading}>
          {loading && (
            <span className="spinner-border spinner-border-sm"></span>
          )}
          Submit
        </Button>
        <ValidationMessage
          errorResponse={errorResponse}
          field="error"
        ></ValidationMessage>
      </Form>
    </>
  );
};
export default PasswordChanger;

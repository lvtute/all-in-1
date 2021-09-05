import { Button, ButtonGroup } from "react-bootstrap";

const genericDelete = (service) => (id) => {
  service
    .deleteById(id)
    .then((response) => {
      console.log(response);
    })
    .catch((error) => {
      console.log("delete error:");
      console.log(error);
    });
};

const ButtonActions = ({ service, id }) => {
  return (
    <ButtonGroup aria-label="Basic example">
      <Button variant="warning">Sửa</Button>
      <Button variant="danger" onClick={() => genericDelete(service)(id)}>
        Xóa
      </Button>
    </ButtonGroup>
  );
};

export default ButtonActions;

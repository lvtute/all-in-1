import { Button, ButtonGroup } from "react-bootstrap";

const genericDelete = (service, reloader) => (id) => {
  service
    .deleteById(id)
    .then((response) => {
      console.log(response);
      reloader();
    })
    .catch((error) => {
      console.log("delete error:");
      console.log(error);
    });
};

const ButtonActions = ({ service, id, reloader }) => {
  return (
    <ButtonGroup aria-label="Basic example">
      <Button variant="warning">Sửa</Button>
      <Button variant="danger" onClick={() => genericDelete(service, reloader)(id)}>
        Xóa
      </Button>
    </ButtonGroup>
  );
};

export default ButtonActions;

import { Button, ButtonGroup } from "react-bootstrap";

const genericDelete = (service, dataRefresher) => (id) => {
  service
    .deleteById(id)
    .then((response) => {
      console.log(response);
      dataRefresher();
    })
    .catch((error) => {
      console.log("delete error:");
      console.log(error);
    });
};

const ButtonActions = ({ service, id, dataRefresher }) => {
  return (
    <ButtonGroup aria-label="Basic example">
      <Button variant="warning">Sửa</Button>
      <Button variant="danger" onClick={() => genericDelete(service, dataRefresher)(id)}>
        Xóa
      </Button>
    </ButtonGroup>
  );
};

export default ButtonActions;

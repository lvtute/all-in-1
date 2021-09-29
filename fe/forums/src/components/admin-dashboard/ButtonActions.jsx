import { Button, ButtonGroup } from "react-bootstrap";

const genericDelete = (service, funcReload) => (id) => {
  service
    .deleteById(id)
    .then((response) => {
      funcReload();
    })
    .catch((error) => {
    }); 
};

const ButtonActions = ({ service, id, funcReload, openUpdatingModal }) => {
  return (
    <ButtonGroup aria-label="Basic example">
      <Button variant="warning" onClick={() => openUpdatingModal(id)}>
        Sửa
      </Button>
      <Button
        variant="danger"
        onClick={() => genericDelete(service, funcReload)(id)}
      >
        Xóa
      </Button>
    </ButtonGroup>
  );
};

export default ButtonActions;

import { Navbar, Nav } from "react-bootstrap";

const NavigationBar = () => {
  return (
    <Navbar bg="primary" variant="dark" style={{ marginBottom: "20px" }}>
      <Navbar.Brand href="#home">Trang chủ</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="#datcauhoi">Đặt câu hỏi</Nav.Link>
      </Nav>
    </Navbar>
  );
};
export default NavigationBar;

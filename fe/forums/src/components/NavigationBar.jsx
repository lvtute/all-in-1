import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../actions/auth";

const NavigationBar = () => {
  const { isLoggedIn, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };
  return (
    <Navbar
      bg="primary"
      variant="dark"
      style={{ marginBottom: "20px", paddingLeft: "10%", paddingRight: "10%" }}
    >
      <Navbar.Brand href="#home">Trang chủ</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href="#datcauhoi">Đặt câu hỏi</Nav.Link>
      </Nav>

      {isLoggedIn ? (
        <Nav>
          <NavDropdown title={`${user.username}`} id="basic-nav-dropdown">
            <NavDropdown.Item onClick={handleLogout}>Log out</NavDropdown.Item>
          </NavDropdown>
        </Nav>
      ) : (
        <Nav>
          <Nav.Link href="/login">Đăng nhập</Nav.Link>
        </Nav>
      )}
    </Navbar>
  );
};
export default NavigationBar;

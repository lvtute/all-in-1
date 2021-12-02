import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../actions/auth";
import {
  HOME_PATH,
  QUESTION_CREATOR_PATH,
  LOGIN_PATH,
} from "../services/constants";

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
      <Navbar.Brand href={HOME_PATH}>Trang chủ</Navbar.Brand>
      <Nav className="mr-auto">
        <Nav.Link href={QUESTION_CREATOR_PATH}>Đặt câu hỏi</Nav.Link>
      </Nav>

      {isLoggedIn ? (
        <Nav>
          <NavDropdown title={`${user.username}`} id="basic-nav-dropdown">
            <NavDropdown.Item onClick={handleLogout}>Log out</NavDropdown.Item>
          </NavDropdown>
        </Nav>
      ) : (
        <Nav>
          <Nav.Link href={LOGIN_PATH}>Đăng nhập</Nav.Link>
        </Nav>
      )}
    </Navbar>
  );
};
export default NavigationBar;

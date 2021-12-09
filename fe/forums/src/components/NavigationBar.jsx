import { Navbar, Nav, NavDropdown } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "../actions/auth";
import {
  HOME_PATH,
  QUESTION_CREATOR_PATH,
  LOGIN_PATH,
  ROLE_ADVISER,
  ROLE_ADMIN,
  ADVISER_PATH,
  ADMIN_PATH,
} from "../services/constants";
import { useState, useEffect } from "react";
import { useHistory } from "react-router";

const NavigationBar = () => {
  const { isLoggedIn, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const history = useHistory();

  const [showAdviserDashboardButton, setShowAdviserDashboardButton] =
    useState(false);
  const [showAdminDashboardButton, setShowAdminDashboardButton] =
    useState(true);

  const handleLogout = () => {
    dispatch(logout());
    history.push(LOGIN_PATH);
  };

  useEffect(() => {
    if (user) {
      setShowAdviserDashboardButton(user.role === ROLE_ADVISER);
      setShowAdminDashboardButton(user.role === ROLE_ADMIN);
    }
  }, [user]);

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
            {showAdviserDashboardButton && (
              <>
                <NavDropdown.Item href={ADVISER_PATH}>
                  Trang TƯ VẤN VIÊN
                </NavDropdown.Item>

                <NavDropdown.Divider />
              </>
            )}
            {showAdminDashboardButton && (
              <>
                <NavDropdown.Item href={ADMIN_PATH}>
                  Trang QUẢN TRỊ VIÊN
                </NavDropdown.Item>
                <NavDropdown.Divider />
              </>
            )}
            <NavDropdown.Item onClick={handleLogout}>
              Đăng xuất
            </NavDropdown.Item>
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

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
  ROLE_DEAN,
  DEAN_PATH,
} from "../services/constants";
import { useState, useEffect } from "react";
import { useHistory } from "react-router";
import logo from "./logo.png";

const NavigationBar = () => {
  const { isLoggedIn, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();
  const history = useHistory();

  const [showAdviserDashboardButton, setShowAdviserDashboardButton] =
    useState(false);
  const [showAdminDashboardButton, setShowAdminDashboardButton] =
    useState(true);
  const [showDeanDashboardButton, setShowDeanDashboardButton] = useState(true);

  const handleLogout = () => {
    dispatch(logout());
    history.push(LOGIN_PATH);
  };

  useEffect(() => {
    if (user) {
      setShowAdviserDashboardButton(user.role === ROLE_ADVISER);
      setShowAdminDashboardButton(user.role === ROLE_ADMIN);
      setShowDeanDashboardButton(user.role === ROLE_DEAN);
    }
  }, [user]);

  return (
    <>
      <Navbar
        bg="light"
        variant="light"
        style={{
          marginBottom: "20px",
          paddingLeft: "10%",
          paddingRight: "10%",
        }}
      >
        <Navbar.Brand href={HOME_PATH}>
          <img
            alt=""
            src={logo}
            
            width="295"
            height="67"
            className="d-inline-block align-top"
          />
        </Navbar.Brand>
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
              {showDeanDashboardButton && (
                <>
                  <NavDropdown.Item href={DEAN_PATH}>
                    Trang TRƯỞNG KHOA
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
    </>
  );
};
export default NavigationBar;

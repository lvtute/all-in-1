import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";
import ButtonActions from "../ButtonActions";
import userService from "../../../services/user.service";
import queryParamUtils from "../../../utils/queryParamUtils";
import { useHistory, useLocation } from "react-router-dom";
import ConfirmationAlert from "../../ConfirmationAlert";

const UserTable = ({ openUpdatingModal }) => {
  // state to show or hide confirmation alert modal (for deleting user)
  const [confirmationAlertShow, setConfirmationAlertShow] = useState(false);
  const [pageNumState, setPageNumState] = useState(1);

  <ConfirmationAlert
    isOpened={confirmationAlertShow}
    handleClose={() => setConfirmationAlertShow(false)}
  />;

  const createActionButtons = (cell, row, rowIndex) => {
    return (
      <ButtonActions
        service={userService}
        id={row?.id}
        funcReload={() => reload(pageNumState)}
        openUpdatingModal={openUpdatingModal}
      />
    );
  };

  const columns = [
    {
      dataField: "id",
      text: "Id",
    },
    {
      dataField: "username",
      text: "Username",
    },
    {
      dataField: "email",
      text: "Email",
    },
    {
      dataField: "fullName",
      text: "Full name",
    },
    {
      dataField: "facultyName",
      text: "Faculty",
    },
    {
      dataField: "roleName",
      text: "Role",
    },
    {
      dataField: "actions",
      text: "Actions",
      formatter: createActionButtons,
    },
  ];

  let history = useHistory();
  const path = useLocation().pathname;

  let pageNum = queryParamUtils.useQuery().get("page");
  if (pageNum === null) pageNum = 1;

  const [tableData, setTableData] = useState(Object);

  const reload = (num, size) => {
    UserService.getAll(num, size)
      .then((response) => {
        pageNum =
          pageNum > response.data.totalPages
            ? response.data.totalPages
            : response.data.number + 1;
        setPageNumState(pageNum);
        setTableData(response.data);
        // set the url param to match the current page
        history.push({
          pathname: path,
          search: `?page=${pageNum}`,
        });
      })
      .catch((err) => {});
  };

  const handleTableChange = (type, { page, sizePerPage }) => {
    reload(page, sizePerPage);
  };

  useEffect(() => {
    UserService.getAll(pageNum).then((res) => {
      setTableData(res.data);
    });
  }, [pageNum]);

  return (
    <>
      {tableData?.content?.length > 0 && (
        <TableWithPagination
          data={tableData}
          columns={columns}
          onTableChange={handleTableChange}
        />
      )}
    </>
  );
};

export default UserTable;

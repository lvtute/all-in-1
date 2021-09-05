import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";
import ButtonActions from "../ButtonActions";
import userService from "../../../services/user.service";
import queryParamUtils from "../../../utils/queryParamUtils";
import { useHistory, useLocation } from "react-router-dom";

const UserTable = () => {
  const createActionButtons = (cell, row, rowIndex) => {
    return (
      <ButtonActions
        service={userService}
        id={row?.id}
        reloader={() => reload(pageNum)}
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
      dataField: "roleNames",
      text: "Role",
    },
    {
      dataField: "actions",
      text: "Actions",
      formatter: createActionButtons,
    },
  ];

  // get page num from the url query param
  let pageNum = queryParamUtils.useQuery().get("page");
  let history = useHistory();

  const path = useLocation().pathname;

  const [tableData, setTableData] = useState(Object);

  const reload = (pageNum, pageSize) => {
    UserService.getAll(pageNum, pageSize).then((response) => {
      setTableData(response?.data);
      // do recusion when return empty array
      if (response.data.content.length === 0 && pageNum > 2) {
        reload(pageNum - 1, pageSize);
      }
    });
    console.log(`reloaded with page = ${pageNum}`);
  };
  const handleTableChange = (type, { page, sizePerPage }) => {
    reload(page, sizePerPage);
    // set the url param to match the current page
    history.replace({ pathname: path, search: `?page=${page}` });
  };

  useEffect(() => {
    UserService.getAll(pageNum)
      .then((response) => {
        setTableData(response?.data);
      })
      .catch((error) => {
        console.log(error);
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

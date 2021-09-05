import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";
import ButtonActions from "../ButtonActions";
import userService from "../../../services/user.service";
import queryParamUtils from "../../../utils/queryParamUtils";
import { useHistory, useLocation } from "react-router-dom";

const createActionButtons = (cell, row, rowIndex) => {
  return <ButtonActions service={userService} id={row?.id} />;
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

const UserTable = () => {
  let history = useHistory();
  const query = queryParamUtils.useQuery();

  const path = useLocation().pathname;
  // state to handle pageNum

  const [tableData, setTableData] = useState(Object);

  const handleTableChange = (type, { page, sizePerPage }) => {
    UserService.getAll(page, sizePerPage).then((response) => {
      setTableData(response?.data);
    });

    // set the url param to match the current page
    history.replace({ pathname: path, search: `?page=${page}` });
  };

  useEffect(() => {
    UserService.getAll(query.get("page"))
      .then((response) => {
        setTableData(response?.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [query]);

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

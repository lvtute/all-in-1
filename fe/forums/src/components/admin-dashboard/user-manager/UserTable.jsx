import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";
import ButtonActions from "../ButtonActions";
import userService from "../../../services/user.service";

const UserTable = () => {
  const [tableData, setTableData] = useState(Object);
  useEffect(() => {
    UserService.getAll()
      .then((response) => {
        setTableData(response?.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const handleTableChange = (type, { page, sizePerPage }) => {
    UserService.getAll(page, sizePerPage).then((response) => {
      setTableData(response?.data);
    });
  };

  const createActionButtons = (cell, row, rowIndex) => {
    console.log(`id=${row.id}`);
    return (
      <ButtonActions
        service={userService}
        id={row?.id}
        dataRefresher={handleTableChange}
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

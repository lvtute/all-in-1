import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";
import ButtonActions from "../ButtonActions";
import userService from "../../../services/user.service";

const createActionButtons = (cell, row, rowIndex) => {
  console.log(`id=${row.id}`);
  return (
    <ButtonActions
      service={userService}
      id={row?.id}
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


const UserTable = () => {
  const [tableData, setTableData] = useState(Object);

  const handleTableChange = (type, { page, sizePerPage }) => {
    UserService.getAll(page, sizePerPage).then((response) => {
      setTableData(response?.data);
    });
  };
  

  useEffect(() => {
    UserService.getAll()
      .then((response) => {
        setTableData(response?.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

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

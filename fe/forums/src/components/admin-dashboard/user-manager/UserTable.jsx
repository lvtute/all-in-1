import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import UserService from "../../../services/user.service";

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
];

const UserTable = () => {
  const [tableData, setTableData] = useState(Object);
  useEffect(() => {
    UserService.getAllWithPaging(1, 5)
      .then((response) => {
        setTableData(response?.data);
      })
      .catch((error) => {});
  }, []);

  const handleTableChange = (type, { page, sizePerPage }) => {
    UserService.getAllWithPaging(page, sizePerPage).then((response) => {
      setTableData(response?.data);
    });
  };

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

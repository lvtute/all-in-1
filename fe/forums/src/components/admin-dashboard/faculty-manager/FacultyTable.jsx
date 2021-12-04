import TableWithPagination from "../TableWithPagination";
import { useEffect, useState } from "react";
import FacultyService from "../../../services/faculty.service";
import ButtonActions from "../ButtonActions";
import facultyService from "../../../services/faculty.service";

import { useHistory, useLocation } from "react-router-dom";
import ConfirmationAlert from "../../ConfirmationAlert";
import { TableBody } from "@material-ui/core";

const FacultyTable = ({ openUpdatingModal }) => {
  // state to show or hide confirmation alert modal (for deleting user)
  const [confirmationAlertShow, setConfirmationAlertShow] = useState(false);
  //const [pageNumState, setPageNumState] = useState(1);

  <ConfirmationAlert
    isOpened={confirmationAlertShow}
    handleClose={() => setConfirmationAlertShow(false)}
  />;

  const createActionButtons = (cell, row, rowIndex) => {
    return (
      <ButtonActions
        service={facultyService}
        id={row?.id}
        //funcReload={() => reload(pageNumState)}
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
      dataField: "name",
      text: "name",
    },
    // {
    //   dataField: "email",
    //   text: "Email",
    // },
    // {
    //   dataField: "fullName",
    //   text: "Full name",
    // },
    // {
    //   dataField: "facultyName",
    //   text: "Faculty",
    // },
    // {
    //   dataField: "roleName",
    //   text: "Role",
    // },
    {
      dataField: "actions",
      text: "Actions",
      formatter: createActionButtons,
    },
  ];

  let history = useHistory();
  const path = useLocation().pathname;

  //let pageNum = queryParamUtils.useQuery().get("page");
 // if (pageNum === null) pageNum = 1;

  const [tableData, setTableData] = useState(Object);

  const reload = (num, size) => {
    FacultyService.getAll(num, size)
      .then((response) => {
        // pageNum =
        //   pageNum > response.data.totalPages
        //     ? response.data.totalPages
        //     : response.data.number + 1;
        //setPageNumState(pageNum);
        setTableData(response.data);
        // set the url param to match the current page
        history.push({
          pathname: path,
          //search: `?page=${pageNum}`,
        });
      })
      .catch((err) => {});
  };

   const handleTableChange = (type, { page, sizePerPage }) => {
    //reload(page, sizePerPage);
  };

  useEffect(() => {
    FacultyService.getAll().then((res) => {
      setTableData(res.data);
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

export default FacultyTable;
